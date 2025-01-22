package xyz.litewars.litewars.commands.litewarssubcommands.setup;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.api.arena.Arena;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Utils;

public class SetLocations extends SubCommand {
    public SetLocations(LitewarsCommand parent) {
        super(parent, "set-locations", "", "Litewars.admin", true, true,
                "waitingLobby");
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        Arena arena = RunningData.onSetupPlayerMap.get(player);
        if (args.length == 0) {
            player.sendMessage(Utils.reColor("&c需要更多参数！"));
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "waitinglobby" -> {
                arena.setWaitingLobbyLocation(player.getLocation());
                player.sendMessage(Utils.reColor("&a设置完成！"));
                return true;
            }

            default -> {
                player.sendMessage(Utils.reColor("&c没有这个参数！"));
                return false;
            }
        }
    }

    @Override
    public String getDescription() {
        return "<set-locations> 设置位置";
    }
}
