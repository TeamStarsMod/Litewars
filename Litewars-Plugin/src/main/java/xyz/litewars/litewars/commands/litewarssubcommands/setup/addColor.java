package xyz.litewars.litewars.commands.litewarssubcommands.setup;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.commands.LitewarsCommand;

public class addColor extends SubCommand {
    public addColor(LitewarsCommand parent) {
        super(
                parent,
                "addColor",
                "",
                "Litewars.admin",
                true,
                true
        );
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        if (RunningData.onSetupPlayerMap.containsKey(player)) {

            return true;
        }else {
            player.sendMessage(Messages.readMessage(Messages.NOT_IN_SETUP_MODE, "&c"));
            return false;
        }
    }

    @Override
    public String getDescription() {
        return "addcolor <" + Messages.readLanguageFile(Messages.COLOR) + "> : " + Messages.readMessage(Messages.LW_ADD_COLOR_MESSAGE);
    }
}
