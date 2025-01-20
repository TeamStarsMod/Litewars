package xyz.litewars.litewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xyz.litewars.litewars.api.command.ParentCommand;
import xyz.litewars.litewars.commands.litewarssubcommands.normal.*;
import xyz.litewars.litewars.commands.litewarssubcommands.setup.*;

public class LitewarsCommand extends ParentCommand {
    public LitewarsCommand() {
        super("Litewars", "lw");
        addSubCommand(new Arenas(this));
        addSubCommand(new Group(this));
        addSubCommand(new Join(this));
        addSubCommand(new Setup(this));
        addSubCommand(new Tp(this));
        addSubCommand(new Reload(this));
        // Setup
        addSubCommand(new AddTeamColor(this));
        addSubCommand(new AutoDetectTeamColor(this));
        addSubCommand(new Save(this));
        addSubCommand(new EditTeam(this));
        addSubCommand(new SetLocations(this));
        addSubCommand(new SetBed(this));
    }

    @Override
    public boolean execute(CommandSender commandSender, Command command, String s, String[] strings) {
        return true;
    }

    @Override
    public String getDescription () {
        return "Litewars主命令";
    }
}
