package xyz.litewars.litewars;

import org.bukkit.configuration.file.YamlConfiguration;
import xyz.litewars.litewars.api.arena.ArenaGroup;
import xyz.litewars.litewars.api.game.GameManager;
import xyz.litewars.litewars.game.SimpleGameManager;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static xyz.litewars.litewars.Litewars.plugin;

public class RunningData {
    public static List<ArenaGroup> arenaGroups = new ArrayList<>();
    public static GameManager gameManager = new SimpleGameManager();
    public static YamlConfiguration languageFile;
    public static YamlConfiguration config;
    public static String languageName;

    public static void init () throws URISyntaxException, IOException {
        /*URL url = RunningData.class.getClassLoader().getResource("languages");
        if (url != null) {
            Path path = Paths.get(url.toURI());
            try (Stream<Path> walk = Files.walk(path)) {
                    walk.forEach(item -> {
                    try {
                        Files.copy(item, Paths.get(plugin.getDataFolder() + "/Languages"));
                    } catch (IOException e) {
                        logger.severe("发生异常");
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        e.printStackTrace(pw);
                        logger.severe(sw.toString());
                    }
                });
            }
        } else {
            logger.warning("没有找到languages文件夹，这可能是个错误！");
        }*/
        config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
        languageName = config.getString("language");
        languageFile = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "Languages/" + languageName + ".yml"));
    }
}
