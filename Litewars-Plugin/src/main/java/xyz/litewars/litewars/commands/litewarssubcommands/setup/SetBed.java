package xyz.litewars.litewars.commands.litewarssubcommands.setup;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Utils;

public class SetBed extends SubCommand {
    public SetBed (LitewarsCommand parent) {
        super(parent, "set-bed", "", "Litewars.admin", true, true);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        Player p = (Player) sender;
        p.sendMessage(Utils.reColor("&a请右键单击一张床的床尾来设置床~"));
        RunningData.onSetupData.getBooleanMap("PlayerBedSetting").put(p, true);
        return true;
    }
}
