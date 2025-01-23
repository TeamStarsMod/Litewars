package xyz.litewars.litewars.commands.litewarssubcommands.normal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.api.game.Game;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Utils;

public class Start extends SubCommand {
    public Start(LitewarsCommand parent) {
        super(parent, "start", "", "Litewars.start", false, false);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player player) {
            // Player logic
            Game game = RunningData.getGameWithPlayer(player);
            boolean isFound = (game != null);
            if (!isFound) {
                player.sendMessage(Utils.reColor("&c你没在任何竞技场里！"));
                return false;
            }
            if (args.length == 0) {
                return game.forceStart(player, false);
            }
            if (args[0].equalsIgnoreCase("debug")) {
                if (player.hasPermission("Litewars.admin")) {
                    return game.forceStart(player, true);
                }else {
                    player.sendMessage(Utils.reColor("&c你没有权限使用debug模式开始游戏！"));
                    return false;
                }
            }else {
                player.sendMessage(Utils.reColor("&c未知的参数！"));
                return false;
            }
        }else {
            // Console logic
            if (args.length == 0) {
                sender.sendMessage(Utils.reColor("&c你需要指定一个竞技场！"));
                return false;
            }
            String arenaName = args[0];
            Game game = RunningData.getGameWithArenaName(arenaName);
            String arg;
            boolean isDebug = false;
            if (args.length > 1) arg = args[1]; else arg = "";
            if (arg.equalsIgnoreCase("debug")) isDebug = true;
            if (game != null) return game.forceStart(sender, isDebug); else {
                sender.sendMessage(Utils.reColor("&c此游戏不存在或未开启！"));
                return false;
            }
        }
    }
}
