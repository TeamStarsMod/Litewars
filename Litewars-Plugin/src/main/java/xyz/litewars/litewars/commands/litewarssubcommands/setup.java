package xyz.litewars.litewars.commands.litewarssubcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Utils;

public class setup extends SubCommand {
    public setup (LitewarsCommand parent) {
        super (parent, "setup", "", "Litewars.admin");
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
        return Utils.reColor("&2setup <世界文件夹名称> : 设置竞技场");
    }
}
