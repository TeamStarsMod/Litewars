package xyz.litewars.litewars.api.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class LiteCommandManager {
    private final JavaPlugin plugin;

    public LiteCommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public CommandMap getCommandMap() {
        if (Bukkit.getPluginManager() instanceof SimplePluginManager pluginManager) {
            try {
                Field field = SimplePluginManager.class.getDeclaredField("commandMap");
                field.setAccessible(true);
                return (CommandMap) field.get(pluginManager);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                plugin.getLogger().severe("无法获取CommandMap" + e.getMessage());
            }
        }
        return null;
    }

    public void registerCommands(String[] commandNames, CommandExecutor executor) {
        CommandMap commandMap = getCommandMap();
        if (commandMap != null) {
            for (String commandName : commandNames) {
                PluginCommand command = plugin.getCommand(commandName);
                if (command == null) {
                    try {
                        Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, org.bukkit.plugin.Plugin.class);
                        constructor.setAccessible(true);
                        command = constructor.newInstance(commandName, plugin);
                        command.setExecutor(executor);
                        commandMap.register(plugin.getName(), command);
                        plugin.getLogger().info("注册命令：" + commandName.toLowerCase());
                    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                        plugin.getLogger().severe("未能成功创建命令 " + commandName + "，原因: " + e.getMessage());
                    }
                } else {
                    command.setExecutor(executor);
                    plugin.getLogger().info("为命令 " + commandName + " 增加处理器中");
                }
            }
        } else {
            plugin.getLogger().severe("CommandMap不可用");
        }
    }
}