package xyz.litewars.litewars.commands.partysubcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.commands.Party;

public class CreateParty extends SubCommand {
    public CreateParty(Party parent) {
        super(parent, "createParty", "", null, true, false);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        return false;
    }
}
