package xyz.litewars.litewars.commands.litewarssubcommands.normal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Utils;

public class Group extends SubCommand {
    public Group(LitewarsCommand parent) {
        super (parent, "group", "", "Litewars.admin", true, false);
    }

    @Override
    public boolean execute (CommandSender sender, Command command, String s, String[] strings) {
        Player p = (Player) sender;
        return false;
    }

    @Override
    public String getDescription () {
        return Utils.reColor("group : 设置竞技组");
    }
}
