package xyz.litewars.litewars.commands.litewarssubcommands.normal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.commands.LitewarsCommand;

public class setlobby extends SubCommand {
    public setlobby(LitewarsCommand parent) {
        super(parent, "setlobby", "", "Litewars.admin", true, false);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        return false;
    }
}
