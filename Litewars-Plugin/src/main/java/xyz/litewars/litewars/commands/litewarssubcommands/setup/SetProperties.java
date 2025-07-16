package xyz.litewars.litewars.commands.litewarssubcommands.setup;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.LitewarsRunningData;
import xyz.litewars.litewars.api.arena.Arena;
import xyz.litewars.litewars.api.arena.ArenaGroup;
import xyz.litewars.litewars.api.arena.team.Team;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Utils;

public class SetProperties extends SubCommand {
    public SetProperties (LitewarsCommand parent) {
        super(parent, "set-properties", "", "Litewars.admin", true, true);
    }

    @Override
    public boolean execute (CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        if (args.length >= 2) {
            Arena arena = LitewarsRunningData.playerSetupArenaMap.get(player);
            switch (args[0].toLowerCase()) {
                case "arenagroup" -> {
                    ArenaGroup arenaGroup = LitewarsRunningData.arenaGroupMap.get(args[1].toLowerCase());
                    if (arenaGroup != null) {
                        arena.setArenaGroup(arenaGroup);
                        player.sendMessage(Utils.reColor("&a设置完成！"));
                        return true;
                    } else {
                        player.sendMessage(Utils.reColor("&cArenaGroup is null!"));
                        return false;
                    }
                }

                case "maxplayer" -> {
                    Team team = LitewarsRunningData.playerSetupTeamMap.get(player);
                    if (team == null) {
                        player.sendMessage(Utils.reColor("&c您要先使用/lw edit-team [TeamName] 来编辑一个队伍"));
                        return false;
                    }

                    int maxPlayer;
                    try {
                        maxPlayer = Integer.parseInt(args[1]);
                    } catch (NumberFormatException ignored) {
                        player.sendMessage(Utils.reColor("&c请输入一个正整数！"));
                        return false;
                    }

                    if (maxPlayer < 0) {
                        player.sendMessage(Utils.reColor("&c请输入一个正整数！"));
                        return false;
                    }

                    team.setMaxPlayer(maxPlayer);
                    player.sendMessage(Utils.reColor("&a设置完成！"));
                    return true;
                }

                default -> {
                    player.sendMessage("&c没有这个参数！");
                    return false;
                }
            }
        } else {
            player.sendMessage(Utils.reColor("&c参数不足！"));
            return false;
        }
    }
}
