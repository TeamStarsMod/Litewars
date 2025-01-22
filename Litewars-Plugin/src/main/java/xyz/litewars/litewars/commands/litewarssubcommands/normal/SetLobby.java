package xyz.litewars.litewars.commands.litewarssubcommands.normal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.LocationUtils;

public class SetLobby extends SubCommand {
    public SetLobby(LitewarsCommand parent) {
        super(parent, "setlobby", "", "Litewars.admin", true, false);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        Player p = (Player) sender;
        RunningData.dataConfig.set("Lobby.world", p.getWorld().getName());
        RunningData.dataConfig.set("Lobby.login-location", LocationUtils.getLocationList(p.getLocation()));
        return true;
    }
}
