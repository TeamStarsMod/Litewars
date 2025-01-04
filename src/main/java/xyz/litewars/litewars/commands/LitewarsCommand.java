package xyz.litewars.litewars.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.Utils;
import xyz.litewars.litewars.api.languages.Messages;

public class LitewarsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            switch (strings.length) {
                case 0:
                    if (player.hasPermission("Litewars.admin")) {
                        player.sendMessage(Utils.reColor("&3Lite&6wars &cadmin commands"));
                        player.sendMessage(Utils.reColor(String.format("&2+ /lw join <%s/%s> : %s", Messages.ARENA_NAME, Messages.ARENA_GROUP, Messages.LW_JOIN_MESSAGE)));
                        player.sendMessage(Utils.reColor(String.format("&2+ /lw arenas [%s] : %s", Messages.ARENA_GROUP, Messages.LW_ARENAS_MESSAGE)));
                        player.sendMessage(Utils.reColor("&6+ /lw tp <竞技场名称> : 传送到指定竞技场"));
                        player.sendMessage(Utils.reColor("&6+ /lw setup <世界文件夹名称> : 设置竞技场"));
                        player.sendMessage(Utils.reColor("&6+ /lw group : 设置竞技组"));
                    } else {
                        player.sendMessage(Utils.reColor("&2Litewars player commands"));
                        player.sendMessage(Utils.reColor("&2+ /lw join <竞技场名称/竞技场组> : 加入一个竞技场/竞技场组中的地图"));
                        player.sendMessage(Utils.reColor("&2+ /lw arenas [竞技场组] : 查看所有(指定竞技场组的)竞技场"));
                    }
                    break;
                case 1:

            }
            return true;
        } else {
            commandSender.sendMessage(Messages.PREFIX + ChatColor.RED + Messages.ONLY_PLAYERS);
            return false;
        }
    }
}
