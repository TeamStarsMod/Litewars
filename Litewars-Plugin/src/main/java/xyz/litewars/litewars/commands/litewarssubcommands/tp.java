package xyz.litewars.litewars.commands.litewarssubcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Utils;

public class tp extends SubCommand {
    public tp (LitewarsCommand parent) {
        super (parent, "tp", "", "Litewars.tp");
    }

    @Override
    public boolean execute (CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
        }
        return false;
    }

    @Override
    public String getDescription () {
        return Utils.reColor(String.format("tp <%s> : %s", Messages.readLanguageFile(Messages.PLAYER_NAME), Messages.readLanguageFile(Messages.LW_TP_MESSAGE)));
    }
}
