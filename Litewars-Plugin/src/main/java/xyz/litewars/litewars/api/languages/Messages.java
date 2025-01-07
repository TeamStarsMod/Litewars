package xyz.litewars.litewars.api.languages;

import org.bukkit.configuration.file.YamlConfiguration;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.utils.Utils;

public class Messages {
    public static String PREFIX = "prefix";

    public static String ARENA_GROUP = "arena_group";
    public static String ARENA_NAME = "arena_name";
    public static String PLAYER_NAME = "player_name";
    public static String WORLD_FOLDER_NAME = "world_folder_name";
    public static String COLOR = "color";
    public static String LW_ARENAS_MESSAGE = "lw_arenas_message";
    public static String LW_JOIN_MESSAGE = "lw_join_message";
    public static String LW_CREATE_ARENA_MESSAGE = "lw_create_arena_message";
    public static String LW_GROUP_MESSAGE = "lw_group_message";
    public static String LW_SETUP_MESSAGE = "lw_setup_message";
    public static String LW_TP_MESSAGE = "lw_tp_message";
    public static String LW_ADD_COLOR_MESSAGE = "lw_add_color_message";
    public static String ONLY_PLAYERS = "only_players";
    public static String NEED_MORE_ARGS = "need_more_args";
    public static String CANT_FOUND_GROUP_OR_ARENA = "cant_found_group_or_arena";
    public static String COMMAND_SYSTEM = "command_system";
    public static String AVAILABLE_COMMANDS = "available_commands";
    public static String FOUND_ARENA = "found_arena";
    public static String FOUND_ARENA_GROUP = "found_arena_group";
    public static String WORLD_NOT_FOUND = "world_not_found";
    public static String WORLD_LOAD_ERROR = "world_load_error";
    public static String WORLD_LOAD_SUCCESS = "world_load_success";
    public static String TP_TO_WORLD = "tp_to_world";
    public static String NOT_IN_SETUP_MODE = "not_in_setup_mode";
    public static String SOMETHING_WAS_WRONG = "something_was_wrong";

    public static Object readLanguageFile(String key) {
        YamlConfiguration languageFile = RunningData.languageFile;
        return languageFile.get(key);
    }

    public static String readMessage(String key, String color) {
        Object read = readLanguageFile(key);
        if (read instanceof String readString){
            return Utils.reColor(readLanguageFile(Messages.PREFIX) + color + readString);
        }else {
            return null;
        }
    }

    public static String readMessage(String key) {
        return readMessage(key, "");
    }
}
