package xyz.litewars.litewars.commands.litewarssubcommands.setup;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.api.arena.team.Colors;
import xyz.litewars.litewars.api.arena.team.Team;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Utils;

import java.util.Map;

public class EditTeam extends SubCommand {
    public EditTeam(LitewarsCommand parent) {
        super(parent, "edit-team", "", "Litewars.admin", true, true);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        Player p = (Player) sender;
        YamlConfiguration config = Utils.getArenaConfig(p);
        Map<String, Object> keys = Utils.getYamlKeys(config, "Team");

        if (args.length >= 1) {
            if (keys.containsKey(args[0])) {
                Colors color = Colors.valueOf(args[0]);
                if (RunningData.playerTeamMap.containsKey(p)) {
                    if (config != null && RunningData.playerTeamMap.get(p).getColors().equals(
                            Colors.valueOf(config.getString("Team." + args[0] + ".Color"))
                    )) {
                        p.sendMessage("你已经在编辑此队伍了哦~");
                        return true;
                    }
                }
                p.sendMessage("正在编辑队伍 " + color.getColor() + args[0]);
                if (config != null) {
                    RunningData.playerTeamMap.put(p, new Team(Colors.valueOf(config.getString("Team." + args[0] + ".Color")), true));
                }
            } else {
                p.sendMessage("没有这个队伍哦~");
            }
        } else {
            if (keys.isEmpty()) {
                p.sendMessage("当前没有可以编辑的队伍哦");
                return false;
            }
            keys.forEach((s1, v) -> {
                Colors color = null;
                if (config != null) {
                    color = Colors.valueOf(config.getString("Team." + s1 + ".Color"));
                }
                ComponentBuilder builder = null;
                if (color != null) {
                    builder = new ComponentBuilder(" - " + color.getColor() + s1 + " [ 点击编辑 ]");
                }
                if (builder != null) {
                    builder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/lw edit-team " + s1));
                    builder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/lw edit-team " + s1).create()));
                    p.spigot().sendMessage(builder.create());
                }
            });
            p.sendMessage("当前共有 " + keys.size() + " 个队伍可以编辑！");
        }
        return false;
    }

    @Override
    public String getDescription () {
        return "<edit-team> : 编辑一个队伍";
    }
}
