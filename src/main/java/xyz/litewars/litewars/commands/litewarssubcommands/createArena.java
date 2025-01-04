package xyz.litewars.litewars.commands.litewarssubcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.commands.LitewarsCommand;

public class createArena extends SubCommand {

    public createArena(LitewarsCommand parent) {
        super(parent, "createArena", "Create an Arena", "Litewars.admin");
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        return false;
    }
}
