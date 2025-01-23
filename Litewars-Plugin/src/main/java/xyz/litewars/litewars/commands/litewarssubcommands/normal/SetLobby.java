package xyz.litewars.litewars.commands.litewarssubcommands.normal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.LocationUtils;

import java.io.IOException;

public class SetLobby extends SubCommand {
    public SetLobby(LitewarsCommand parent) {
        super(parent, "setlobby", "", "Litewars.admin", true, false);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        Player p = (Player) sender;
        RunningData.dataConfig.set("Lobby.world", p.getWorld().getName());
        RunningData.dataConfig.set("Lobby.login-location", LocationUtils.getLocationList(p.getLocation()));
        try {
            RunningData.dataConfig.save(RunningData.dataConfigFile);
        } catch (IOException e) {
            p.sendMessage("这个活人代码又炸了！跟管理员说去！");
            e.printStackTrace();
        }
        return true;
    }
}
