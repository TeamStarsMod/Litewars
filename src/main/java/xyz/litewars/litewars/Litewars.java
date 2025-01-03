package xyz.litewars.litewars;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import xyz.litewars.litewars.api.support.VersionControl;
import xyz.litewars.litewars.commands.LitewarsCommand;

import java.io.File;
import java.time.Instant;
import java.util.logging.Logger;

public final class Litewars extends JavaPlugin {
    public static Logger logger;
    public static Server server;
    public static PluginManager pluginManager;
    public static Plugin plugin;
    public static File dataFolder;
    public static VersionControl nms;

    @Override
    public void onEnable() {
        long start = Instant.now().toEpochMilli();
        logger = getLogger();
        logger.info("Litewars正在加载中...");
        plugin = getPlugin(getClass());
        server = getServer();
        pluginManager = server.getPluginManager();
        dataFolder = getDataFolder();
        saveDefaultConfig();
        // NMS
        nms = getNMS();
        if (nms == null) {
            throw new RuntimeException("无法找到NMS支持类，请检查服务器版本！");
        }
        // Commands
        getCommand("version-control").setExecutor(nms.VCMainCommand());
        getCommand("Litewars").setExecutor(new LitewarsCommand());

        logger.info("本次启动耗时 " + (Instant.now().toEpochMilli() - start) + " ms");
    }

    @Override
    public void onDisable() {
        long start = Instant.now().toEpochMilli();
        logger.info("Litewars正在卸载中...");
        logger.info("本次卸载耗时 " + (Instant.now().toEpochMilli() - start) + " ms");
    }

    public static VersionControl getNMS () {
        if (nms != null) {
            return nms;
        }
        try {
            String serverVersion = Bukkit.getServer().getClass().getName().split("\\.")[3];
            return (VersionControl) Class.forName("xyz.litewars.litewars.support." + serverVersion + ".VersionControl").newInstance();
        } catch (ClassNotFoundException e) {
            logger.severe("无法找到 NMS 支持类，请检查插件版本是否与服务器版本匹配：" + e.getMessage());
        } catch (IllegalAccessException e) {
            logger.severe("无法访问 NMS 支持类：" + e.getMessage());
        } catch (InstantiationException e) {
            logger.severe("无法实例化 NMS 支持类：" + e.getMessage());
        }
        logger.info("正在卸载插件……");
        return null;
    }
}
