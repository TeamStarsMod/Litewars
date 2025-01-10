package xyz.litewars.litewars;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.arena.ArenaGroup;
import xyz.litewars.litewars.api.database.hikaricp.DatabaseManager;
import xyz.litewars.litewars.api.database.hikaricp.HikariCPSupport;
import xyz.litewars.litewars.api.game.GameManager;
import xyz.litewars.litewars.game.SimpleGameManager;
import xyz.litewars.litewars.lobby.scoreboard.Lobby;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static xyz.litewars.litewars.Litewars.dataFolder;
import static xyz.litewars.litewars.Litewars.plugin;

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
    public static Map<Player, String> onSetupPlayerMap = new HashMap<>();
    public static String serverVersion; // Just like 1_12_R1, 1_8_R3...
    public static List<Player> playersInLobby = new ArrayList<>();

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
        for (Player p : Bukkit.getOnlinePlayers()) {
            RunningData.lobby.addPlayer(p);
            RunningData.playersInLobby.add(p);
        }
    }
}
