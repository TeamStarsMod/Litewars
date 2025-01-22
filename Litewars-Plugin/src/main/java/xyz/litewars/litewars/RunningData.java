package xyz.litewars.litewars;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.litewars.litewars.api.arena.Arena;
import xyz.litewars.litewars.api.arena.ArenaGroup;
import xyz.litewars.litewars.api.arena.team.Colors;
import xyz.litewars.litewars.api.arena.team.Team;
import xyz.litewars.litewars.api.data.DataSet;
import xyz.litewars.litewars.api.database.hikaricp.DatabaseManager;
import xyz.litewars.litewars.api.database.hikaricp.HikariCPSupport;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.commands.litewarssubcommands.normal.Arenas;
import xyz.litewars.litewars.game.GameManager;
import xyz.litewars.litewars.lobby.scoreboard.Lobby;
import xyz.litewars.litewars.utils.Utils;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static xyz.litewars.litewars.Litewars.*;

public class RunningData {
    private static final Logger log = LoggerFactory.getLogger(RunningData.class);
    public static boolean hasPlaceholderAPI = false;

    public static Map<String, ArenaGroup> arenaGroupMap;
    public static xyz.litewars.litewars.api.game.GameManager gameManager = new GameManager();
    public static YamlConfiguration languageFile;
    public static YamlConfiguration config;
    public static YamlConfiguration dataConfig;
    public static File configFile;
    public static File dataConfigFile;
    public static String languageName;
    public static Lobby lobby;
    private static final List<String> languages = new ArrayList<>();
    public static HikariCPSupport cpSupport;
    public static DatabaseManager databaseManager;
    public static Map<Player, Arena> onSetupPlayerMap;
    public static Map<Player, Team> playerTeamMap;
    public static DataSet<String, Player, Object> onSetupData = new DataSet<>();
    public static String serverVersion; // Just like 1_12_R1, 1_8_R3...
    public static List<Player> playersInLobby = new ArrayList<>();
    public static List<String> lobbyScoreboardLines;

    public static void init () throws URISyntaxException, IOException {
        languages.add("zh_cn");
        arenaGroupMap = new HashMap<>();
        for (String languageName : languages) {
            if (!(new File(dataFolder + "/Languages/" + languageName + ".yml").exists())) {
                Files.copy(Objects.requireNonNull(
                                RunningData.class.getClassLoader().getResourceAsStream("languages/" + languageName + ".yml")),
                        Paths.get(dataFolder + "/Languages/" + languageName + ".yml")
                );
            }
        }
        lobby = new Lobby();
        configFile = new File(plugin.getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
        File dataFolder1 = new File(dataFolder, "Data");
        if (dataFolder1.mkdirs()) logger.info("已创建数据文件夹");
        dataConfigFile = new File(plugin.getDataFolder(), "Data/Data.yml");
        if (dataConfigFile.createNewFile()) logger.info("已创建数据文件");
        dataConfig = YamlConfiguration.loadConfiguration(dataConfigFile);
        File arenaFolder = new File(dataFolder, "Arenas");
        File[] arenaFiles = arenaFolder.listFiles();
        for (String name : dataConfig.getStringList(Arenas.arenaGroupListName)) {
            arenaGroupMap.put(name, new ArenaGroup(name));
        }
        if (arenaFiles != null) {
            for (File f : arenaFiles) {
                if (f.getName().endsWith(".yml") || f.getName().endsWith(".yaml")) {
                    try {
                        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
                        Map<String, Object> teams = Utils.getYamlKeys(yaml, "Team");
                        if (yaml.get("Name") == null) {
                            logger.warning("竞技场配置文件 " + f.getName() + " 的键不完整！已跳过加载");
                            return;
                        }
                        Arena arena = new Arena(yaml.getString("Name"), yaml);
                        teams.forEach((s, o) -> {
                            Colors color = Colors.valueOf(yaml.getString("Team." + s + ".Color"));
                            Team team = new Team(color, false, s, yaml, arena.getArenaWorld());
                            arena.addTeam(team);
                        });
                        arenaGroupMap.get(yaml.getString("ArenaGroup")).addArena(arena);
                    } catch (NullPointerException e) {
                        logger.warning("竞技场配置文件 " + f.getName() + " 的键不完整！已跳过加载！");
                    } catch (Exception e) {
                        logger.warning("加载竞技场文件 " + f.getName() + " 时发生异常，已跳过！");
                    }
                }
            }
        }
        languageName = config.getString("language");
        languageFile = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "Languages/" + languageName + ".yml"));
        List<String> list = languageFile.getStringList(Messages.LOBBY_SCOREBOARD_LINES);
        lobbyScoreboardLines = new ArrayList<>();
        onSetupData.newDataMap("PlayerMap");
        onSetupData.newDataMap("PlayerTeam");
        onSetupData.newDataMap("PlayerBedSetting");
        onSetupPlayerMap = onSetupData.getArenaMap("PlayerMap");
        playerTeamMap = onSetupData.getTeamMap("PlayerTeam");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isEmpty()) {
                String[] strings = String.valueOf(i).split("");
                StringBuilder line = new StringBuilder();
                for (String s : strings) {
                    line.append("&").append(s);
                }
                lobbyScoreboardLines.add(i, Utils.reColor(line.toString()));
            } else {
                lobbyScoreboardLines.add(i, list.get(i));
            }
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            RunningData.lobby.addPlayer(p);
            RunningData.playersInLobby.add(p);
        }
    }
}
