package xyz.litewars.litewars.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static xyz.litewars.litewars.Litewars.commandManager;

public abstract class ParentCommand implements CommandExecutor, TabCompleter {
    private final List<SubCommand> subCommands = new ArrayList<>();
    private final String[] name;
    private String description = "";

    public ParentCommand (String... name) {
        this.name = name;
        commandManager.registerCommands(name, this);
    }

    public List<SubCommand> getSubCommands () {
        return this.subCommands;
    }

    public void addSubCommand (SubCommand subCommand) {
        this.subCommands.add(subCommand);
    }

    @Override
    public boolean onCommand (CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            boolean exe = this.execute(sender, command, s, args);
            StringBuilder sb = new StringBuilder();
            if (RunningData.mainConfig.getBoolean("ConsoleColor")) {
                sb.append(String.format("&9Lite&bwars &7%s%n", Messages.readLanguageFile(Messages.COMMAND_SYSTEM)));
                sb.append(Utils.reColor("&6" + Messages.readLanguageFile(Messages.AVAILABLE_COMMANDS) + "\n"));
            } else {
                sb.append(String.format("Litewars %s%n", Messages.readLanguageFile(Messages.COMMAND_SYSTEM)));
                sb.append(Utils.reColor(Messages.readLanguageFile(Messages.AVAILABLE_COMMANDS) + "\n"));
            }
            subCommands.forEach(sub -> {
                // 检查是否只对玩家可用
                if (sub.getIsOnlyPlayer() && !(sender instanceof Player)) {
                    // 如果是控制台执行且子命令只对玩家可用，则跳过
                    return;
                }

                // 检查是否处于设置模式，并且子命令是否仅适用于设置模式
                if (sender instanceof Player player) {
                    boolean playerInSetupMap = RunningData.playerSetupArenaMap.containsKey(player);
                    if ((playerInSetupMap && !sub.getIsOnlySetup()) || (!playerInSetupMap && sub.getIsOnlySetup())) {
                        // 如果玩家在设置模式中，但子命令不适用于设置模式，或者玩家不在设置模式中，但子命令仅适用于设置模式，则跳过
                        return;
                    }
                }

                // 检查权限
                if (sub.getPermission() == null || sender.hasPermission(sub.getPermission())) {
                    // 构建并追加子命令信息到StringBuilder
                    if (sender instanceof Player) {
                        sb.append("&c  + ")
                                .append("&6").append(sub.getName())
                                .append("&c | ")
                                .append("&2").append(sub.getDescription())
                                .append("\n");
                    } else {
                        // 防止因控制台不能正常解析颜色代码而导致不可读问题
                        if (!RunningData.mainConfig.getBoolean("ConsoleColor")) {
                            sb.append("  + ")
                                    .append(sub.getName())
                                    .append(" | ")
                                    .append(sub.getDescription())
                                    .append("\n");
                        } else {
                            sb.append("&c  + ")
                                    .append("&6").append(sub.getName())
                                    .append("&c | ")
                                    .append("&2").append(sub.getDescription())
                                    .append("\n");
                        }
                    }
                }
            });
            //检查最后一个是不是换行符
            if (!sb.isEmpty() && sb.charAt(sb.length() - 1) == '\n') {
                //如果是，将其移除
                sb.setLength(sb.length() - 1);
            }

            sender.sendMessage(Utils.reColor(sb.toString()));
            return exe;
        } else {
            boolean executed = false;
            boolean commandFound = false;

            for (SubCommand sub : subCommands) {
                if (sub.getName().equalsIgnoreCase(args[0])) {
                    commandFound = true;

                    boolean isPlayer = sender instanceof Player;
                    Player player = isPlayer ? (Player) sender : null;
                    boolean isSetupMode = isPlayer && RunningData.playerSetupArenaMap.containsKey(player);

                    if (sub.getIsOnlyPlayer() && !isPlayer) {
                        sender.sendMessage(Messages.readMessage(Messages.ONLY_PLAYERS, "&c"));
                        break;
                    } else if ((sub.getIsOnlySetup() && !isSetupMode) || (!sub.getIsOnlySetup() && isSetupMode)) {
                        sender.sendMessage(Messages.readMessage(Messages.NOT_AVAILABLE_IN_YOUR_MODE, "&c"));
                        break;
                    } else if (sub.getPermission() == null || sender.hasPermission(sub.getPermission())) {
                        if (sub.onCommand(sender, command, s, args)) {
                            executed = true;
                        }
                        break;
                    } else {
                        sender.sendMessage(Messages.readMessage(Messages.NO_PERMISSION, "&c"));
                        break;
                    }
                }
            }

            if (!commandFound) {
                sender.sendMessage(Messages.readMessage(Messages.COMMAND_NOT_FOUND, "&c"));
            }

            return executed;
        }
    }

    public abstract boolean execute (CommandSender sender, Command command, String s, String[] args);

    public String[] getName() {
        return name;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            for (SubCommand subCommand : subCommands) {
                boolean isPlayer = commandSender instanceof Player;
                boolean playerInSetupMode = isPlayer && RunningData.playerSetupArenaMap.containsKey((Player) commandSender);

                if ((subCommand.getIsOnlySetup() && playerInSetupMode) || (!subCommand.getIsOnlySetup() && !playerInSetupMode)) {
                    if (subCommand.getIsOnlyPlayer() && !isPlayer) {
                        continue;
                    }
                    if (subCommand.getPermission() == null || commandSender.hasPermission(subCommand.getPermission())) {
                        if (subCommand.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                            completions.add(subCommand.getName());
                        }
                    }
                }
            }
        } else {
            for (SubCommand sub : subCommands) {
                if (sub.getSubs() != null) {
                    if (sub.getName().equals(args[0])) {
                        boolean isPlayer = commandSender instanceof Player;
                        boolean playerInSetupMode = isPlayer && RunningData.playerSetupArenaMap.containsKey((Player) commandSender);

                        if ((sub.getIsOnlySetup() && playerInSetupMode) || (!sub.getIsOnlySetup() && !playerInSetupMode)) {
                            if (sub.getIsOnlyPlayer() && !isPlayer) {
                                continue;
                            }
                            if (sub.getPermission() == null || commandSender.hasPermission(sub.getPermission())) {
                                for (String str : sub.getSubs()) {
                                    if (str.startsWith(args[1])) {
                                        completions.add(str);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return completions;
    }

    @Override
    public String toString () {
        return "Litewars-ParentCommand [" + this.getClass().getName() + "]\n" +
                "{name=" + Arrays.toString(name) + "}" +
                "Subs [" + subCommands + "]";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
