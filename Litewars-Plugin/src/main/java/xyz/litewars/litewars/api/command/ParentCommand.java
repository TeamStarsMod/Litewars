package xyz.litewars.litewars.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
            sb.append(String.format("&3Lite&ewars &7%s%n", Messages.readLanguageFile(Messages.COMMAND_SYSTEM)));
            sb.append(Utils.reColor("&6" + Messages.readLanguageFile(Messages.AVAILABLE_COMMANDS) + "\n"));
            subCommands.forEach(sub -> {
                if (sub.getPermission() == null) {
                    sb
                            .append("&c  + ")
                            .append("&6").append(sub.getName())
                            .append("&c | ")
                            .append("&2").append(sub.getDescription())
                            .append("\n");
                } else {
                    if (sender.hasPermission(sub.getPermission())) {
                        sb
                                .append("&c  + ")
                                .append("&6").append(sub.getName())
                                .append("&c | ")
                                .append("&2").append(sub.getDescription())
                                .append("\n");
                    }
                }
            });
            sender.sendMessage(Utils.reColor(sb.toString()));
            return exe;
        } else {
            boolean[] exe = new boolean[] {false};
            subCommands.forEach(sub -> {
                if (sub.onCommand(sender, command, s, args)) {
                    exe[0] = true;
                }
            });
            return exe[0];
        }
    }

    public abstract boolean execute (CommandSender sender, Command command, String s, String[] args);

    public String[] getName() {
        return name;
    }
}
