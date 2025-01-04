package xyz.litewars.litewars.commands.litewarssubcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Utils;

public class arenas extends SubCommand {
    public arenas (LitewarsCommand parent) {
        super (parent, "arenas", "", null); //az
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
        return Utils.reColor(String.format("&2arenas [%s] : %s", Messages.readLanguageFile(Messages.ARENA_GROUP), Messages.readLanguageFile(Messages.LW_ARENAS_MESSAGE)));
    }
}
