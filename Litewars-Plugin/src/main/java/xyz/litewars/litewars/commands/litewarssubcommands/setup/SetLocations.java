package xyz.litewars.litewars.commands.litewarssubcommands.setup;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.api.arena.team.Team;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Utils;

public class SetLocations extends SubCommand {
    public SetLocations(LitewarsCommand parent) {
        super(parent, "set-locations", "", "Litewars.admin", true, true, "spawn", "iron", "gold");
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        Player p = (Player) sender;
        if (RunningData.playerTeamMap.containsKey(p)) {
            if (args.length >= 1) {
                Team team = RunningData.playerTeamMap.get(p);
                switch (args[0]) {
                    case "spawn" -> {
                        team.setSpawn(p.getLocation());
                        p.sendMessage(Utils.reColor("&a设置成功！"));
                    }
                    case "iron" -> {
                        team.setIron(p.getLocation());
                        p.sendMessage(Utils.reColor("&a设置成功！"));
                    }
                    case "gold" -> {
                        team.setGold(p.getLocation());
                        p.sendMessage(Utils.reColor("&a设置成功！"));
                    }
                    case "emerald" -> {
                        team.setEmerald(p.getLocation());
                        p.sendMessage(Utils.reColor("&a设置成功！"));
                    }
                    case "shop" -> {
                        team.setShop(p.getLocation());
                        p.sendMessage(Utils.reColor("&a设置成功！"));
                    }
                    case "upgrade" -> {
                        team.setUpgrade(p.getLocation());
                        p.sendMessage(Utils.reColor("&a设置成功！"));
                    }
                }
            }
        } else {
            p.sendMessage(Utils.reColor("&c请先选择一个队伍编辑！"));
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "<set-spawn> 设置队伍出生点";
    }
}
