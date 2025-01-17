package xyz.litewars.litewars.api.arena.team;

import org.bukkit.ChatColor;

import java.util.Locale;

public enum Colors {
    RED (ChatColor.RED.toString()),
    BLUE (ChatColor.BLUE.toString()),
    GREEN (ChatColor.GREEN.toString()),
    YELLOW (ChatColor.YELLOW.toString()),
    WHITE (ChatColor.WHITE.toString()),
    AQUA (ChatColor.AQUA.toString()),
    GRAY (ChatColor.GRAY.toString()),
    DARK_GRAY (ChatColor.DARK_GRAY.toString()),
    PINK (ChatColor.LIGHT_PURPLE.toString()),
    DARK_GREEN (ChatColor.DARK_GREEN.toString()),
    CYAN (ChatColor.AQUA.toString()),
    LIME (ChatColor.GREEN.toString());

    private final String color;

    Colors (String color) {
        this.color = color;
    }

    public static String getTeamColor(String team) {
        return Colors.valueOf(team.toUpperCase(Locale.ROOT)).getColor();
    }

    public String getColor() {
        return color;
    }
}
