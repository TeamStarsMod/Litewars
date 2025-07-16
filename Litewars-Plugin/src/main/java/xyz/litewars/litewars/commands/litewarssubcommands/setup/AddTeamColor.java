package xyz.litewars.litewars.commands.litewarssubcommands.setup;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.arena.team.Colors;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Utils;

import java.util.ArrayList;
import java.util.Locale;

public class AddTeamColor extends SubCommand {
    public AddTeamColor(LitewarsCommand parent) {
        super(
                parent,
                "add-team-color",
                "",
                "Litewars.admin",
                true,
                true
        );
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        YamlConfiguration configuration = Utils.getArenaConfig(player);
        if (configuration == null) {
            return false;
        }
        if (args.length < 1) {
            return false;
        }
        if (configuration.get("Team." + args[0]) != null) {
            return false;
        }
        try {
            Colors color = Colors.valueOf(args[0].toUpperCase(Locale.ROOT));
            String team_key = "Team." + color;
            configuration.set(team_key + ".Color", color.toString());
            configuration.set(team_key + ".Spawn", new ArrayList<Float>());
            configuration.set(team_key + ".Iron", new ArrayList<Float>());
            configuration.set(team_key + ".Gold", new ArrayList<Float>());
            configuration.set(team_key + ".Emerald", new ArrayList<Float>());
            configuration.set(team_key + ".Shop", new ArrayList<Float>());
            configuration.set(team_key + ".Upgrade", new ArrayList<Float>());
            configuration.set(team_key + ".Bed", new ArrayList<Float>());
            player.sendMessage(Utils.reColor("添加队伍 " + color.getColor() + color + " 成功！"));
        } catch (IllegalArgumentException e) {
            player.sendMessage(Utils.reColor("&c未知的颜色"));
            return false;
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "addcolor <" + Messages.readLanguageFile(Messages.COLOR) + "> : " + Messages.readLanguageFile(Messages.LW_ADD_COLOR_MESSAGE);
    }
}
