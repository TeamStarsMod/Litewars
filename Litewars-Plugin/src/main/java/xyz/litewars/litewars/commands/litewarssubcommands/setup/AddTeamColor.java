package xyz.litewars.litewars.commands.litewarssubcommands.setup;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.commands.LitewarsCommand;

public class AddTeamColor extends SubCommand {
    public AddTeamColor(LitewarsCommand parent) {
        super(
                parent,
                "addTeamColor",
                "",
                "Litewars.admin",
                true,
                true
        );
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        sender.sendMessage("yes");
        return true;
    }

    @Override
    public String getDescription() {
        return "addcolor <" + Messages.readLanguageFile(Messages.COLOR) + "> : " + Messages.readMessage(Messages.LW_ADD_COLOR_MESSAGE);
    }
}
