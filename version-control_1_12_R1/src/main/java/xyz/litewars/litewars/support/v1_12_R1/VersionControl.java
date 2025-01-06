package xyz.litewars.litewars.support.v1_12_R1;

import org.bukkit.command.CommandExecutor;
import xyz.litewars.litewars.utils.Utils;

public class VersionControl implements xyz.litewars.litewars.api.support.VersionControl {
    @Override
    public CommandExecutor VCMainCommand() {
        return (commandSender, command, s, strings) -> {
            if (commandSender.hasPermission("Litewars.admin")) {
                commandSender.sendMessage(Utils.reColor("&7当前正在运行的&bVersion Control&7版本为1.12.2 (By NekoEpisode)"));
                commandSender.sendMessage(Utils.reColor("&7" + getClass().getName()));
                return true;
            }
            return false;
        };
    }
}
