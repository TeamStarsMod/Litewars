package xyz.litewars.litewars.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.Utils;

public class LitewarsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            if (player.hasPermission("Litewars.admin")){
                player.sendMessage(Utils.reColor("&2Litewars player commands"));
                player.sendMessage(Utils.reColor("&2+ /lw join <竞技场名称/竞技场组> : 加入一个竞技场/竞技场组中的地图"));
                player.sendMessage(Utils.reColor("&2+ /lw arenas [竞技场组] : 查看所有(指定竞技场组的)竞技场"));
                return true;
            }else {
                player.sendMessage(Utils.reColor("&3Lite&6wars &cadmin commands"));
                player.sendMessage(Utils.reColor("&2+ /lw join <竞技场名称/竞技场组> : 加入一个竞技场/竞技场组中的地图"));
                player.sendMessage(Utils.reColor("&2+ /lw arenas [竞技场组] : 查看所有(指定竞技场组的)竞技场"));
                player.sendMessage(Utils.reColor("&6+ /lw tp [竞技场名称] : 传送到指定竞技场"));
                player.sendMessage(Utils.reColor("&6+ /lw setup [世界文件夹名称] : 设置竞技场"));
                return true;
            }
        }else {
            commandSender.sendMessage(ChatColor.RED + "这个命令只能被玩家使用！");
            return false;
        }
    }
}
