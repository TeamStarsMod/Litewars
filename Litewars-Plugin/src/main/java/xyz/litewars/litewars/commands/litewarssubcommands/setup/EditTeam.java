package xyz.litewars.litewars.commands.litewarssubcommands.setup;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
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
                p.sendMessage("正在编辑队伍 " + args[0]);
            } else {
                p.sendMessage("没有这个队伍哦~");
            }
        } else {
            if (keys.isEmpty()) {
                p.sendMessage("当前没有可以编辑的队伍哦");
                return false;
            }
            keys.forEach((s1, v) -> {
                ComponentBuilder builder = new ComponentBuilder(" - " + s1 + " [ 点击编辑 ]");
                builder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/lw edit-team " + s1));
                builder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/lw edit-team " + s1).create()));
                p.spigot().sendMessage(builder.create());
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
