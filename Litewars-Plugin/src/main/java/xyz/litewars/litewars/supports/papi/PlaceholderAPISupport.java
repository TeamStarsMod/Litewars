package xyz.litewars.litewars.supports.papi;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.RunningData;

public class PlaceholderAPISupport {
    public static String setPlaceholders(Player player, String text) {
        if (RunningData.hasPlaceholderAPI) {
            return PlaceholderAPI.setPlaceholders(player, text);
        }else {
            return text;
        }
    }
}
