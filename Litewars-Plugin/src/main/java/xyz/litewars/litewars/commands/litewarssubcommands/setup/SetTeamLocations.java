package xyz.litewars.litewars.commands.litewarssubcommands.setup;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.LitewarsRunningData;
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
        if (LitewarsRunningData.playerSetupArenaMap.containsKey(p)) {
            if (args.length >= 1) {
                Team team = LitewarsRunningData.playerSetupTeamMap.get(p);
                switch (args[0]) {
                    case "spawn" -> {
                        team.setSpawn(p.getLocation());
                        p.sendMessage(Utils.reColor("&a设置成功！"));
                        if (team.getBed() == null) {
                            p.sendMessage(Utils.reColor("&e正在尝试寻找此队伍的床...可能会些许卡顿"));
                            boolean foundBed = false;
                            final int xzRange = 30, yRange = 10;
                            final org.bukkit.Location base = p.getLocation();
                            // 优先 y 轴为外层，减少 add 调用
                            for (int y = -yRange; y <= yRange && !foundBed; y++) {
                                for (int x = -xzRange; x <= xzRange && !foundBed; x++) {
                                    for (int z = -xzRange; z <= xzRange && !foundBed; z++) {
                                        org.bukkit.Location checkLoc = base.clone().add(x, y, z);
                                        org.bukkit.Material type = checkLoc.getBlock().getType();
                                        String typeName = type.name();
                                        if ((typeName.endsWith("_BED") || typeName.equals("BED_BLOCK"))
                                                && !typeName.equals("BEDROCK")) {
                                            team.setBed(checkLoc.getBlock());
                                            p.sendMessage(Utils.reColor("&a成功设置床！在 " + x + ", " + y + ", " + z + " 处"));
                                            foundBed = true;
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
