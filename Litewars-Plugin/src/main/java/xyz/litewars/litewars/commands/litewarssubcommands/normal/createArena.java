package xyz.litewars.litewars.commands.litewarssubcommands.normal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.commands.LitewarsCommand;

public class createArena extends SubCommand {

    public createArena(LitewarsCommand parent) {
        super(parent, "createArena", "", "Litewars.admin", true, false);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        return false;
    }

    @Override
    public String getDescription() {
        return Messages.readMessage(Messages.LW_CREATE_ARENA_MESSAGE);
    }
}
