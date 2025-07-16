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
        if (RunningData.playerSetupArenaMap.containsKey(p)) {
            if (args.length >= 1) {
                Team team = RunningData.playerSetupTeamMap.get(p);
                switch (args[0]) {
                    case "spawn" -> {
                        team.setSpawn(p.getLocation());
                        p.sendMessage(Utils.reColor("&a设置成功！"));
                        if (team.getBed() == null) {
                            p.sendMessage(Utils.reColor("&e正在尝试寻找此队伍的床...可能会些许卡顿"));
                            // 遍历周围30x10x30的位置寻找床
                            boolean foundBed = false;
                            for (int x = -30; x <= 30; x++) {
                                if (foundBed) break;
                                for (int z = -30; z <= 30; z++) {
                                    if (foundBed) break;
                                    for (int y = -10; y <= 10; y++) {
                                        if (p.getWorld().getBlockAt(p.getLocation().add(x, y, z)).getType().name().contains("BED")
                                                // 防止和基岩方块冲突
                                        && !p.getWorld().getBlockAt(p.getLocation().add(x, y, z)).getType().name().equalsIgnoreCase("BEDROCK")) {
                                            team.setBed(p.getLocation().add(x, y, z).getBlock());
                                            p.sendMessage(Utils.reColor("&a成功设置床！在 " + x + ", " + y + ", " + z + " 处"));
                                            foundBed = true;
                                            break;
                                        }
                                    }
                                }
                            }

                            if (!foundBed) {
                                p.sendMessage(Utils.reColor("&c未找到床！请手动设置！"));
                            }
                        }
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
