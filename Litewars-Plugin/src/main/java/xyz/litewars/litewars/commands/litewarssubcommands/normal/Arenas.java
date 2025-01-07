package xyz.litewars.litewars.commands.litewarssubcommands.normal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Utils;

public class Arenas extends SubCommand {
    public Arenas(LitewarsCommand parent) {
        super (parent, "arenas", "", null, false, false);
    }

    @Override
    public boolean execute (CommandSender sender, Command command, String s, String[] strings) {
        return false;
    }

    @Override
    public String getDescription () {
        return Utils.reColor(String.format("arenas [%s] : %s", Messages.readLanguageFile(Messages.ARENA_GROUP), Messages.readLanguageFile(Messages.LW_ARENAS_MESSAGE)));
    }
}
