package xyz.litewars.litewars;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.arena.Arena;
import xyz.litewars.litewars.api.arena.ArenaGroup;
import xyz.litewars.litewars.api.arena.team.Colors;
import xyz.litewars.litewars.api.arena.team.Team;
import xyz.litewars.litewars.api.database.hikaricp.DatabaseManager;
import xyz.litewars.litewars.api.database.hikaricp.HikariCPSupport;
import xyz.litewars.litewars.api.game.Game;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.commands.litewarssubcommands.normal.Arenas;
import xyz.litewars.litewars.game.GameManager;
import xyz.litewars.litewars.lobby.scoreboard.Lobby;
import xyz.litewars.litewars.utils.ArgumentUtils;
import xyz.litewars.litewars.utils.Utils;
import xyz.litewars.litewars.utils.WorldUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static xyz.litewars.litewars.Litewars.*;

/**
 * 全局运行时数据管理类，存储插件运行期间的各种全局状态和缓存。
 */
public class LitewarsRunningData {
    /** 当前语言文件名（如 zh_cn） */
    public static String languageCode;
    /** 服务器 NMS 版本号（如 v1_21_R0） */
    public static String nmsVersion;
    /** 是否已检测到 PlaceholderAPI 插件 */
    public static boolean hasPlaceholderAPI = false;

    /** 大厅管理对象 */
    public static Lobby lobbyManager;
    /** 当前在大厅的玩家列表 */
    public static List<Player> lobbyPlayers = new ArrayList<>();

    /** 支持的语言列表 */
    private static final List<String> supportedLanguages = new ArrayList<>();

    /** 当前加载的语言文件 */
    public static YamlConfiguration languageConfig;
    /** 插件主配置文件 */
    public static YamlConfiguration mainConfig;
    /** 数据配置文件（如 Data.yml） */
    public static YamlConfiguration dataConfig;
    /** 主配置文件对象 */
    public static File mainConfigFile;
    /** 数据配置文件对象 */
    public static File dataConfigFile;

    /** 游戏管理器，管理所有正在运行的游戏实例 */
    public static GameManager gameManager = new GameManager();

    /** 数据库连接池支持对象 */
    public static HikariCPSupport hikariCPSupport;
    /** 数据库管理器 */
    public static DatabaseManager databaseManager;

    /** 所有竞技场分组（ArenaGroup）映射，key 为分组名 */
    public static Map<String, ArenaGroup> arenaGroupMap;
    /** 正在 setup 某个竞技场的玩家与竞技场映射（每个玩家当前 setup 的 Arena） */
    public static Map<Player, Arena> playerSetupArenaMap;
    /** 正在 setup 的竞技场名与 Arena 实例映射（多人协作时共用同一个 Arena 实例） */
    public static Map<String, Arena> setupArenaInstanceMap = new ConcurrentHashMap<>();
    /** 玩家正在 setup 的队伍映射（每个玩家当前 setup 的 Team） */
    public static Map<Player, Team> playerSetupTeamMap;
    /** 玩家是否正在设置床的标记 */
    public static Map<Player, Boolean> playerBedSettingMap = new HashMap<>();

    /** 大厅记分板显示内容 */
    public static List<String> lobbyScoreboardLines;

    /**
     * 初始化全局运行时数据。
     */
    public static void init() throws URISyntaxException, IOException {
        logger.info("开始初始化 RunningData...");
        hasPlaceholderAPI = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");

        addSupportedLanguages();
        arenaGroupMap = new HashMap<>();

        for (String lang : supportedLanguages) {
            File langFile = new File(dataFolder + "/Languages/" + lang + ".yml");
            if (!langFile.exists()) {
                logger.info("缺失语言文件: " + lang + "，从jar中复制");
                Files.copy(Objects.requireNonNull(LitewarsRunningData.class.getClassLoader().getResourceAsStream("languages/" + lang + ".yml")),
                        Paths.get(langFile.toURI()));
            }
        }

        lobbyManager = new Lobby();
        mainConfigFile = new File(plugin.getDataFolder(), "config.yml");
        mainConfig = YamlConfiguration.loadConfiguration(mainConfigFile);

        File dataFolder1 = new File(dataFolder, "Data");
        if (dataFolder1.mkdirs()) logger.info("已创建数据文件夹 Data");

        dataConfigFile = new File(plugin.getDataFolder(), "Data/Data.yml");
        if (dataConfigFile.createNewFile()) logger.info("已创建数据文件 Data.yml");
        dataConfig = YamlConfiguration.loadConfiguration(dataConfigFile);

        for (String name : dataConfig.getStringList(Arenas.arenaGroupListName)) {
            arenaGroupMap.put(name, new ArenaGroup(name));
        }

        File arenaFolder = new File(dataFolder, "Arenas");
        File[] arenaFiles = arenaFolder.listFiles();
        if (arenaFiles != null) {
            for (File file : arenaFiles) {
                if (file.getName().endsWith(".yml") || file.getName().endsWith(".yaml")) {
                    try {
                        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
                        Map<String, Object> teams = Utils.getYamlKeys(yaml, "Team");

                        ArgumentUtils.checkArgument(yaml.getString("Name"), ArgumentUtils.CheckMethod.NOT_NULL);
                        String worldName = yaml.getString("World");
                        ArgumentUtils.checkArgument(worldName, ArgumentUtils.CheckMethod.NOT_NULL);

                        if (Bukkit.getWorld(worldName) != null) {
                            logger.info("跳过已加载世界: " + worldName);
                            continue;
                        }

                        World arenaWorld = WorldUtils.loadWorld(worldName);
                        ArgumentUtils.checkArgument(arenaWorld, ArgumentUtils.CheckMethod.NOT_NULL);

                        Arena arena = new Arena(yaml.getString("Name"), yaml);
                        teams.forEach((s, o) -> {
                            Colors color = Colors.valueOf(yaml.getString("Team." + s + ".Color"));
                            Team team = new Team(color, false, s, yaml, arena.getArenaWorld());
                            arena.addTeam(team);
                        });

                        String arenaGroupName = yaml.getString("ArenaGroup");
                        if (arenaGroupName != null && !arenaGroupName.isEmpty()) {
                            ArenaGroup arenaGroup = arenaGroupMap.get(arenaGroupName);
                            if (arenaGroup != null) {
                                arenaGroup.addArena(arena);
                                logger.info("已加载竞技场: " + arena.getName() + " 属于 ArenaGroup: " + arenaGroupName);
                            } else {
                                logger.warning("竞技场 " + arena.getName() + " 的 ArenaGroup " + arenaGroupName + " 未找到，跳过加载！");
                            }
                        } else {
                            logger.warning("竞技场文件 " + file.getName() + " 缺失 ArenaGroup，跳过加载！");
                        }
                    } catch (IllegalArgumentException e) {
                        logger.warning("竞技场文件 " + file.getName() + " 参数错误: " + e.getMessage() + "，跳过！");
                    } catch (Exception e) {
                        logger.warning("加载竞技场文件 " + file.getName() + " 时异常: " + e.getMessage() + "，跳过！");
                    }
                }
            }
        }

        languageCode = mainConfig.getString("language", "zh_cn");
        languageConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "Languages/" + languageCode + ".yml"));

        List<String> list = languageConfig.getStringList(Messages.LOBBY_SCOREBOARD_LINES);
        if (list == null || list.isEmpty()) {
            logger.warning("语言文件 " + languageCode + " 缺失 LOBBY_SCOREBOARD_LINES，使用默认值");
            list = Collections.singletonList("&a欢迎来到服务器！");
        }

        lobbyScoreboardLines = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String line = list.get(i);
            lobbyScoreboardLines.add(line.isEmpty() ? Utils.reColor("&" + i) : line);
        }
        playerSetupArenaMap = new HashMap<>();
        playerSetupTeamMap = new HashMap<>();

        Bukkit.getOnlinePlayers().forEach(p -> {
            lobbyManager.addPlayer(p);
            lobbyPlayers.add(p);
        });
        logger.info("RunningData 初始化完成！");
    }

    /**
     * 添加支持的语言。
     */
    private static void addSupportedLanguages() {
        supportedLanguages.add("zh_cn");
        supportedLanguages.add("en_us");
    }

    /**
     * 根据玩家查找其所在的游戏实例。
     * @param player 玩家
     * @return 包含该玩家的 Game 实例，若无则返回 null
     */
    public static Game getGameWithPlayer(Player player) {
        return gameManager.getRunningGames().stream()
                .filter(game -> game != null && game.getPlayers().contains(player))
                .findFirst().orElse(null);
    }

    /**
     * 根据竞技场名查找正在运行的游戏实例。
     * @param name 竞技场名
     * @return 绑定该竞技场的 Game 实例，若无则返回 null
     */
    public static Game getGameWithArenaName(String name) {
        return gameManager.getRunningGames().stream()
                .filter(game -> game != null && game.getArena().getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }
}