package xyz.litewars.litewars.utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.RunningData;

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
}
