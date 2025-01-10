package xyz.litewars.litewars;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.slf4j.LoggerFactory;
import xyz.litewars.litewars.api.command.LiteCommandManager;
import xyz.litewars.litewars.api.database.hikaricp.DatabaseManager;
import xyz.litewars.litewars.api.database.hikaricp.HikariCPConfig;
import xyz.litewars.litewars.api.database.hikaricp.HikariCPSupport;
import xyz.litewars.litewars.api.versionsupport.VersionControl;
import xyz.litewars.litewars.commands.AddKillCount;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.commands.Party;
import xyz.litewars.litewars.database.CreateTables;
import xyz.litewars.litewars.event.OnPlayerJoin;
import xyz.litewars.litewars.event.OnPlayerLeave;
import xyz.litewars.litewars.event.OnServerReload;
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
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Litewars.class);
    public static Logger logger;
    public static Server server;
    public static PluginManager pluginManager;
    public static Plugin plugin;
    public static File dataFolder;
    public static VersionControl nms;
    public static LiteCommandManager commandManager;

    @Override
    public void onEnable() {
        long start = Instant.now().toEpochMilli();
        logger = getLogger();
        logger.info("Litewars正在加载中...");
        plugin = getPlugin(getClass());
        server = getServer();
        pluginManager = server.getPluginManager();
        dataFolder = getDataFolder();
        commandManager= new LiteCommandManager(this);
        RunningData.serverVersion = Bukkit.getServer().getClass().getName().split("\\.")[3];
        saveDefaultConfig();

        // NMS
        logger.info("正在检查当前版本NMS...");
        nms = getNMS();
        if (nms == null) {
            pluginManager.disablePlugin(this);
        }else {
            logger.info("成功寻找到当前版本NMS支持类！");
        }

        File languageFolder = new File(dataFolder, "Languages");
        if (languageFolder.mkdirs()) logger.info("已创建语言文件夹");
        try {
            // ScoreBoard in here
            RunningData.init();
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

        String databaseType = RunningData.config.getString("database-type");
        if (!databaseType.equalsIgnoreCase("MySQL") && !databaseType.equalsIgnoreCase("SQLite")){
            databaseType = "SQLite";
            logger.warning("未知的数据库类型，已自动设置为SQLite！");
        }

        if (databaseType.equalsIgnoreCase("MySQL")) {
            //jdbc:mysql://database-host:database-port/database-name
            String url = "jdbc:mysql://" +
                    RunningData.config.getString("database-host") +
                    ":" + RunningData.config.getString("database-port") +
                    "/" + RunningData.config.getString("database-name");
            RunningData.cpSupport = new HikariCPSupport(new HikariCPConfig(
                    url,
                    RunningData.config.getString("database-user"),
                    RunningData.config.getString("database-password")
            ), false);
        } else if (databaseType.equalsIgnoreCase("SQLite")) {
            File cacheFolder = new File(dataFolder + "/Data");
            if (cacheFolder.mkdirs()) logger.info("已创建数据文件夹");
            String url = "jdbc:sqlite:" + dataFolder + "/Data/database.db";
            RunningData.cpSupport = new HikariCPSupport(new HikariCPConfig(
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

        RunningData.cpSupport.start();
        RunningData.databaseManager = new DatabaseManager(RunningData.cpSupport);
        CreateTables.initDatabase();

        // SoftDepends
        if (pluginManager.getPlugin("PlaceholderAPI") == null) {
            RunningData.hasPlaceholderAPI = false;
            logger.warning("未找到PlaceholderAPI，我们推荐您安装此插件");
        }else {
            RunningData.hasPlaceholderAPI = true;
            logger.info("已找到PlaceholderAPI，正在加载支持...");
            new LobbyPlaceHolder().register();
        }

        //Events
        pluginManager.registerEvents(new OnPlayerJoin(), plugin);
        pluginManager.registerEvents(new OnPlayerLeave(), plugin);
        pluginManager.registerEvents(new OnServerReload(), plugin);

        // Commands
        getCommand("version-control").setExecutor(nms.VCMainCommand());
        new LitewarsCommand();
        new AddKillCount();
        new Party();

        RunningData.lobby.run();

        try {
            if (RunningData.config.getBoolean("Tips")) {
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
        RunningData.cpSupport.stop();
        Tips.stopTips();
        logger.info("本次卸载耗时 " + (Instant.now().toEpochMilli() - start) + " ms");
    }

    public static VersionControl getNMS () {
        if (nms != null) {
            return nms;
        }
        try {
            Class<?> versionControlClass = Class.forName("xyz.litewars.litewars.support." + RunningData.serverVersion + ".VersionControl");
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
