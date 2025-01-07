package xyz.litewars.litewars.commands.litewarssubcommands.normal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Utils;

public class Tp extends SubCommand {
    public Tp(LitewarsCommand parent) {
        super (parent, "tp", "", "Litewars.tp", true, false);
    }

    @Override
    public boolean execute (CommandSender sender, Command command, String s, String[] strings) {
        Player p = (Player) sender;
        return false;
    }

    @Override
    public String getDescription () {
        return Utils.reColor(String.format("tp <%s> : %s", Messages.readLanguageFile(Messages.PLAYER_NAME), Messages.readLanguageFile(Messages.LW_TP_MESSAGE)));
    }
}
