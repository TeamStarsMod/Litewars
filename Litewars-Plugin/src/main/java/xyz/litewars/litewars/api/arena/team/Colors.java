package xyz.litewars.litewars.api.arena.team;

import org.bukkit.ChatColor;

public enum Colors {
    RED,
    BLUE,
    GREEN,
    YELLOW,
    WHITE,
    AQUA,
    GRAY,
    DARK_GRAY,
    PINK,
    DARK_GREEN,
    CYAN,
    LIME;

    public static ChatColor getTeamColor(String team) {
        switch (team.toLowerCase()) {
            case "red" -> {
                return ChatColor.RED;
            }
            case "blue" -> {
                return ChatColor.BLUE;
            }
            case "green", "lime" -> {
                return ChatColor.GREEN;
            }
            case "yellow" -> {
                return ChatColor.YELLOW;
            }
            case "white" -> {
                return ChatColor.WHITE;
            }
            case "aqua", "cyan" -> {
                return ChatColor.AQUA;
            }
            case "gray" -> {
                return ChatColor.GRAY;
            }
            case "dark_gray" -> {
                return ChatColor.DARK_GRAY;
            }
            case "pink" -> {
                return ChatColor.LIGHT_PURPLE;
            }
            case "dark_green" -> {
                return ChatColor.DARK_GREEN;
            }
            default -> {
                return null;
            }
        }
    }
}
