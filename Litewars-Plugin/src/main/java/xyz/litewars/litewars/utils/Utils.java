package xyz.litewars.litewars.utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.RunningData;

import java.util.*;

public class Utils {
    public static String reColor(String message) {
        if (message == null) return "NULL";
        return message.replace("&", "§").replace("§§", "&");
    }

    public static YamlConfiguration getArenaConfig(Player player) {
        if (!RunningData.onSetupPlayerMap.containsKey(player)) {
            return null;
        }
        return RunningData.onSetupPlayerMap.get(player).getYaml();
    }

    public static Map<String, Object> getYamlKeys (YamlConfiguration yaml, String key) {
        return yaml == null ? new HashMap<>() : (yaml.contains(key) ? yaml.getConfigurationSection(key).getValues(false) : new HashMap<>());
    }
}
