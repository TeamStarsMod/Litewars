package xyz.litewars.litewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xyz.litewars.litewars.api.command.ParentCommand;
import xyz.litewars.litewars.commands.partysubcommands.CreateParty;

public class Party extends ParentCommand {
    public Party(){
        super("party");
        addSubCommand(new CreateParty(this));
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        return false;
    }
}
