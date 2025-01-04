package xyz.litewars.litewars.utils;

public class Utils {
    public static String reColor(String message) {
        if (message == null) return "NULL";
        return message.replace("&", "§").replace("§§", "&");
    }
}
