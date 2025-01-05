package xyz.litewars.litewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.command.ParentCommand;
import xyz.litewars.litewars.commands.litewarssubcommands.*;
import xyz.litewars.litewars.utils.Utils;
import xyz.litewars.litewars.api.languages.Messages;

public class LitewarsCommand extends ParentCommand {
    public LitewarsCommand() {
        super("Litewars", "lw");
        addSubCommand(new arenas(this));
        addSubCommand(new createArena(this));
        addSubCommand(new group(this));
        addSubCommand(new join(this));
        addSubCommand(new setup(this));
        addSubCommand(new tp(this));
    }

    @Override
    public boolean execute(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            return true;
        } else {
            commandSender.sendMessage(Utils.reColor(Messages.readLanguageFile(Messages.PREFIX) + "&c" + Messages.readLanguageFile(Messages.ONLY_PLAYERS)));
            return false;
        }
    }
}
