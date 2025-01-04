package xyz.litewars.litewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xyz.litewars.litewars.api.command.ParentCommand;
import xyz.litewars.litewars.api.command.SubCommand;

public class Test extends ParentCommand {

    public Test () {
        super("test", "testAli", "TESTTOo");
        addSubCommand(new SubCommand(this, "test", "test sub command", null) {
            @Override
            public boolean execute(CommandSender sender, Command command, String s, String[] args) {
                sender.sendMessage("TEST SUB COMMAND SUCCESS!");
                return false;
            }
        });
    }

    @Override
    public boolean execute (CommandSender sender, Command command, String s, String[] args) {
        sender.sendMessage("TEST PARENT COMMAND SUCCESS!");
        return false;
    }
}
