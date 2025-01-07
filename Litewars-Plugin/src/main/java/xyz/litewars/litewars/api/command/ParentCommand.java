package xyz.litewars.litewars.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static xyz.litewars.litewars.Litewars.commandManager;

public abstract class ParentCommand implements CommandExecutor { //qwq
    private final List<SubCommand> subCommands = new ArrayList<>();
    private final String[] name;

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
            if (RunningData.config.getBoolean("ConsoleColor")){
                sb.append(String.format("&3Lite&ewars &7%s%n", Messages.readLanguageFile(Messages.COMMAND_SYSTEM)));
                sb.append(Utils.reColor("&6" + Messages.readLanguageFile(Messages.AVAILABLE_COMMANDS) + "\n"));
            }else {
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
                    boolean playerInSetupMap = RunningData.onSetupPlayerMap.containsKey(player);
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
                    }else {
                        // 防止因控制台不能正常解析颜色代码而导致不可读问题(可选)
                        if (!RunningData.config.getBoolean("ConsoleColor")) {
                            sb.append("  + ")
                                    .append(sub.getName())
                                    .append(" | ")
                                    .append(sub.getDescription())
                                    .append("\n");
                        }else {
                            sb.append("&c  + ")
                                    .append("&6").append(sub.getName())
                                    .append("&c | ")
                                    .append("&2").append(sub.getDescription())
                                    .append("\n");
                        }
                    }
                }
            });
            sender.sendMessage(Utils.reColor(sb.toString()));
            return exe;
        } else {
            boolean executed = false;
            boolean isPlayerInSetupMode = sender instanceof Player && RunningData.onSetupPlayerMap.containsKey((Player) sender);

            for (SubCommand sub : subCommands) {
                // 检查是否是玩家且是否在setup模式下，或者执行者不是玩家
                if ((sub.getIsOnlySetup() && isPlayerInSetupMode) || (!sub.getIsOnlySetup() && !isPlayerInSetupMode)) {
                    // 如果玩家在非setup模式下尝试执行setup命令，或者想在setup模式下执行非setup命令，则不允许并显示消息
                    if ((sub.getIsOnlySetup() && !isPlayerInSetupMode) || (!sub.getIsOnlySetup() && isPlayerInSetupMode)) {
                        sender.sendMessage(Messages.readMessage(Messages.NOT_AVAILABLE_IN_YOUR_MODE, "&c"));
                        continue;
                    }

                    // 如果子命令是只允许玩家执行的，并且执行者不是玩家，则显示错误消息
                    if (sub.getIsOnlyPlayer() && !(sender instanceof Player)) {
                        sender.sendMessage(Messages.readMessage(Messages.ONLY_PLAYERS, "&c"));
                        continue;
                    }

                    // 执行子命令
                    if (sub.onCommand(sender, command, s, args)) {
                        executed = true;
                        break;
                    }
                } else {
                    // 如果执行者不是玩家，则按照非setup模式处理
                    if (!(sender instanceof Player)) {
                        if (sub.onCommand(sender, command, s, args)) {
                            executed = true;
                            break;
                        }
                    } else {
                        // 如果玩家在错误的模式下执行命令，则显示错误消息
                        sender.sendMessage(Messages.readMessage(Messages.NOT_AVAILABLE_IN_YOUR_MODE, "&c"));
                    }
                }
            }
            return executed;
        }
    }

    public abstract boolean execute (CommandSender sender, Command command, String s, String[] args);

    public String[] getName() {
        return name;
    }
}
