package xyz.litewars.litewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xyz.litewars.litewars.api.command.ParentCommand;
import xyz.litewars.litewars.commands.litewarssubcommands.normal.*;

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
        return true;
    }
}
