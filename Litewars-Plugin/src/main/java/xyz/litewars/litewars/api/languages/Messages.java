package xyz.litewars.litewars.api.languages;

import org.bukkit.configuration.file.YamlConfiguration;
import xyz.litewars.litewars.RunningData;

public class Messages {
    public static String PREFIX = "prefix";

    public static String ARENA_GROUP = "arena_group";
    public static String ARENA_NAME = "arena_name";
    public static String LW_ARENAS_MESSAGE = "lw_arena_message";
    public static String LW_JOIN_MESSAGE = "lw_join_message";
    public static String ONLY_PLAYERS = "only_players";
    public static String NEED_MORE_ARGS = "need_more_args";
    public static String CANT_FOUND_GROUP_OR_ARENA = "cant_found_group_or_arena";
    public static String COMMAND_SYSTEM = "command_system";
    public static String AVAILABLE_COMMANDS = "available_commands";
    public static String FOUND_ARENA = "found_arena";
    public static String FOUND_ARENA_GROUP = "found_arena_group";

    public static Object readLanguageFile(String key) {
        YamlConfiguration languageFile = RunningData.languageFile;
        return languageFile.get(key);
    }
}
