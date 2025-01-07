package xyz.litewars.litewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xyz.litewars.litewars.api.command.ParentCommand;
import xyz.litewars.litewars.commands.litewarssubcommands.normal.*;
import xyz.litewars.litewars.commands.litewarssubcommands.setup.AddTeamColor;
import xyz.litewars.litewars.commands.litewarssubcommands.setup.Save;

public class LitewarsCommand extends ParentCommand {
    public LitewarsCommand() {
        super("Litewars", "lw");
        addSubCommand(new Arenas(this));
        addSubCommand(new Group(this));
        addSubCommand(new Join(this));
        addSubCommand(new Setup(this));
        addSubCommand(new Tp(this));
        addSubCommand(new AddTeamColor(this));
        addSubCommand(new Save(this));
    }

    @Override
    public boolean execute(CommandSender commandSender, Command command, String s, String[] strings) {
        return true;
    }
}
