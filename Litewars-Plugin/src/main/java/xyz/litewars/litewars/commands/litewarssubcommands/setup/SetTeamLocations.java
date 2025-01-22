package xyz.litewars.litewars.commands.litewarssubcommands.setup;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.api.arena.team.Team;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Utils;

public class SetTeamLocations extends SubCommand {
    public SetTeamLocations(LitewarsCommand parent) {
        super(parent, "set-team-locations", "", "Litewars.admin", true, true,
                "spawn",
                "resources",
                "shop",
                "upgrade");
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
                    case "resources" -> {
                        team.setIron(p.getLocation());
                        team.setGold(p.getLocation());
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
            p.sendMessage(Utils.reColor("&c请先使用/EditTeam选择一个队伍编辑！"));
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "<set-team-location> 设置队伍位置";
    }
}
