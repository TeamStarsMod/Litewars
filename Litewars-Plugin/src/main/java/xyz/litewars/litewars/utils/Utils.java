package xyz.litewars.litewars.utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.Litewars;
import xyz.litewars.litewars.RunningData;

import java.io.File;
import java.io.IOException;

public class Utils {
    public static String reColor(String message) {
        if (message == null) return "NULL";
        return message.replace("&", "§").replace("§§", "&");
    }

    public static YamlConfiguration getArenaConfig(Player player){
        try {
            if (!RunningData.onSetupPlayerMap.containsKey(player)) {
                return null;
            }

            String arenaName = RunningData.onSetupPlayerMap.get(player);
            if ((new File(Litewars.dataFolder, "Data/Arenas").mkdirs())) Litewars.logger.info("已创建竞技场配置文件夹");
            File arenaFile = new File(Litewars.dataFolder, "Data/Arenas/" + arenaName + ".yml");
            if (arenaFile.createNewFile()) Litewars.logger.info("已创建新竞技场配置文件：" + arenaFile + ".yml");
            return YamlConfiguration.loadConfiguration(arenaFile);
        }catch (IOException e){
            Litewars.logger.severe("发生错误！" + e.getMessage());
            return null;
        }
    }
}
