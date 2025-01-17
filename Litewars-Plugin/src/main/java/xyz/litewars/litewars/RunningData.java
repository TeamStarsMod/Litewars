package xyz.litewars.litewars;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.arena.Arena;
import xyz.litewars.litewars.api.arena.ArenaGroup;
import xyz.litewars.litewars.api.arena.team.Team;
import xyz.litewars.litewars.api.data.DataSet;
import xyz.litewars.litewars.api.database.hikaricp.DatabaseManager;
import xyz.litewars.litewars.api.database.hikaricp.HikariCPSupport;
import xyz.litewars.litewars.api.game.GameManager;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.game.SimpleGameManager;
import xyz.litewars.litewars.lobby.scoreboard.Lobby;
import xyz.litewars.litewars.utils.Utils;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static xyz.litewars.litewars.Litewars.*;

public class RunningData {
    public static boolean hasPlaceholderAPI = false;

    public static List<ArenaGroup> arenaGroups = new ArrayList<>();
    public static GameManager gameManager = new SimpleGameManager();
    public static YamlConfiguration languageFile;
    public static YamlConfiguration config;
    public static String languageName;
    public static Lobby lobby;
    private static final List<String> languages = new ArrayList<>();
    public static HikariCPSupport cpSupport;
    public static DatabaseManager databaseManager;
    public static Map<Player, Arena> onSetupPlayerMap;// <玩家, 地图名> 要不写在Arena类里吧
    public static Map<Player, Team> playerTeamMap;
    public static DataSet<String, Player, Object> onSetupData = new DataSet<>();
    public static String serverVersion; // Just like 1_12_R1, 1_8_R3...
    public static List<Player> playersInLobby = new ArrayList<>();
    public static List<String> lobbyScoreboardLines;

    public static void init () throws URISyntaxException, IOException {
        languages.add("zh_cn");
        for (String languageName : languages){
            if (!(new File(dataFolder + "/Languages/" + languageName + ".yml").exists())) {
                Files.copy(Objects.requireNonNull(
                                RunningData.class.getClassLoader().getResourceAsStream("languages/" + languageName + ".yml")),
                        Paths.get(dataFolder + "/Languages/" + languageName + ".yml")
                );
            }
        }

        lobby = new Lobby();
        config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
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
