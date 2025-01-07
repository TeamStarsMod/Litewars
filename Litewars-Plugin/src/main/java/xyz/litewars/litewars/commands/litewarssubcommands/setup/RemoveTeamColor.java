package xyz.litewars.litewars.commands.litewarssubcommands.setup;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.commands.LitewarsCommand;

public class RemoveTeamColor extends SubCommand {
    public RemoveTeamColor(LitewarsCommand parent) {
        super(
                parent,
                "removeTeamColor",
                "",
                "Litewars.admin",
                true,
                true
        );
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        return false;
    }
}
