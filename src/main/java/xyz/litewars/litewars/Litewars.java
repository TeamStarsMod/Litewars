package xyz.litewars.litewars;

import org.bukkit.plugin.java.JavaPlugin;

public final class Litewars extends JavaPlugin {

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
}
