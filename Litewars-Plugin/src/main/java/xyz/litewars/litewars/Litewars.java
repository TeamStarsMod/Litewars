package xyz.litewars.litewars;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.litewars.litewars.api.LitewarsAPI;
import xyz.litewars.litewars.api.command.LiteCommandManager;
import xyz.litewars.litewars.api.database.hikaricp.DatabaseManager;
import xyz.litewars.litewars.api.database.hikaricp.HikariCPConfig;
import xyz.litewars.litewars.api.database.hikaricp.HikariCPSupport;
import xyz.litewars.litewars.api.exceptions.PluginLoadException;
import xyz.litewars.litewars.api.versionsupport.VersionControl;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.commands.Party;
import xyz.litewars.litewars.database.DatabaseUtils;
import xyz.litewars.litewars.event.OnBedSetting;
import xyz.litewars.litewars.event.OnPlayerJoin;
import xyz.litewars.litewars.event.OnPlayerLeave;
import xyz.litewars.litewars.lobby.listener.OnLobbyMessage;
import xyz.litewars.litewars.lobby.tips.Tips;
import xyz.litewars.litewars.supports.papi.LobbyPlaceHolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.logging.Logger;

public final class Litewars extends JavaPlugin {
    public static Logger logger;
    public static Server server;
    public static PluginManager pluginManager;
    public static Plugin plugin;
    public static File dataFolder;
    public static VersionControl nms;
    public static LiteCommandManager commandManager;
    public static LitewarsCommand litewarsCommand;

    @Override
    public void onEnable() {
        long start = Instant.now().toEpochMilli();
        logger = getLogger();
        logger.info("Litewars正在加载中...");
        plugin = getPlugin(getClass());
        server = getServer();
        pluginManager = server.getPluginManager();
        dataFolder = getDataFolder();
        commandManager = new LiteCommandManager(this);
        logger.info(Bukkit.getServer().getClass().getName());

        String bukkitVersion = Bukkit.getBukkitVersion(); // 例如 "1.21-R0.1-SNAPSHOT"
        String[] parts = bukkitVersion.split("-")[0].split("\\."); // ["1", "21"]
        String revision = bukkitVersion.split("-")[1].replace("R", "").split("\\.")[0]; // "0"
        LitewarsRunningData.nmsVersion = "v" + parts[0] + "_" + parts[1] + "_R" + revision; // "v1_21_R0"

        saveDefaultConfig();
        // NMS
        logger.info("正在检查当前版本NMS...");
        nms = getNMS();
        if (nms == null) {
            pluginManager.disablePlugin(this);
            throw new PluginLoadException("无法找到当前版本NMS支持类！");
        }else {
            logger.info("成功寻找到当前版本NMS支持类！");
        }

        File languageFolder = new File(dataFolder, "Languages");
        if (languageFolder.mkdirs()) logger.info("已创建语言文件夹");
        try {
            // ScoreBoard in here
            LitewarsRunningData.init();
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

        String databaseType = LitewarsRunningData.mainConfig.getString("database-type");
        if (!databaseType.equalsIgnoreCase("MySQL") && !databaseType.equalsIgnoreCase("SQLite")){
            databaseType = "SQLite";
            logger.warning("未知的数据库类型，已自动设置为SQLite！");
        }

        if (databaseType.equalsIgnoreCase("MySQL")) {
            //jdbc:mysql://database-host:database-port/database-name
            String url = "jdbc:mysql://" +
                    LitewarsRunningData.mainConfig.getString("database-host") +
                    ":" + LitewarsRunningData.mainConfig.getString("database-port") +
                    "/" + LitewarsRunningData.mainConfig.getString("database-name");
            LitewarsRunningData.hikariCPSupport = new HikariCPSupport(new HikariCPConfig(
                    url,
                    LitewarsRunningData.mainConfig.getString("database-user"),
                    LitewarsRunningData.mainConfig.getString("database-password")
            ), false);
        } else if (databaseType.equalsIgnoreCase("SQLite")) {
            String url = "jdbc:sqlite:" + dataFolder + "/Data/database.db";
            LitewarsRunningData.hikariCPSupport = new HikariCPSupport(new HikariCPConfig(
                    url,
                    null,
                    null
            ), true);
        } else {
            logger.severe("插件在启动时遇到错误！");
        }

        // 添加一个用于警告的文件
        try {
            File warningFile = new File(dataFolder + "/Data/LOOK_THIS.txt");
            if (warningFile.createNewFile()) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(warningFile));
                bufferedWriter.append("DO NOT DELETE THIS FOLDER! YOU WILL LOSE ALL DATA!");
                bufferedWriter.flush();
                bufferedWriter.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LitewarsRunningData.hikariCPSupport.start();
        LitewarsRunningData.databaseManager = new DatabaseManager(LitewarsRunningData.hikariCPSupport);
        DatabaseUtils.initDatabase();

        // SoftDepends
        if (pluginManager.getPlugin("PlaceholderAPI") == null) {
            LitewarsRunningData.hasPlaceholderAPI = false;
            logger.warning("未找到PlaceholderAPI，我们推荐您安装此插件");
        }else {
            LitewarsRunningData.hasPlaceholderAPI = true;
            logger.info("已找到PlaceholderAPI，正在加载支持...");
            new LobbyPlaceHolder().register();
        }

        //Events
        pluginManager.registerEvents(new OnPlayerJoin(), plugin);
        pluginManager.registerEvents(new OnPlayerLeave(), plugin);
        pluginManager.registerEvents(new OnLobbyMessage(), plugin);
        pluginManager.registerEvents(new OnBedSetting(), plugin);

        // Commands
        litewarsCommand = new LitewarsCommand();
        getCommand("litewars-version-control").setExecutor(nms.VCMainCommand());
        new Party();

        LitewarsRunningData.lobbyManager.run();

        try {
            if (LitewarsRunningData.mainConfig.getBoolean("Tips")) {
                Tips.init();
                Tips.startTips();
            }
        } catch (IOException e) {
            logger.severe("发生错误！" + e);
        }

        logger.info("本次启动耗时 " + (Instant.now().toEpochMilli() - start) + "ms");
    }

    @Override
    public void onDisable() {
        long start = Instant.now().toEpochMilli();
        logger.info("Litewars正在卸载中...");
        LitewarsRunningData.hikariCPSupport.stop();
        Tips.stopTips();
        logger.info("本次卸载耗时 " + (Instant.now().toEpochMilli() - start) + " ms");
    }

    public static VersionControl getNMS () {
        if (nms != null) {
            return nms;
        }
        try {
            Class<?> versionControlClass = Class.forName("xyz.litewars.litewars.support." + LitewarsRunningData.nmsVersion + ".VersionControl");
            Constructor<?> constructor = versionControlClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return (VersionControl) constructor.newInstance();
        } catch (ClassNotFoundException e) {
            logger.severe("无法找到 NMS 支持类，请检查插件版本是否与服务器版本匹配：" + e.getMessage());
        } catch (NoSuchMethodException e) {
            logger.severe("无法找到无参构造器：" + e.getMessage());
        } catch (IllegalAccessException e) {
            logger.severe("无法访问 NMS 支持类：" + e.getMessage());
        } catch (InstantiationException e) {
            logger.severe("无法实例化 NMS 支持类：" + e.getMessage());
        } catch (InvocationTargetException e) {
            logger.severe("构造器抛出异常：" + e.getCause().getMessage());
        }
        logger.info("正在卸载插件……");
        return null;
    }
}
