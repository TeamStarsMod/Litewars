package xyz.litewars.litewars.support.v1_8_R3;

import org.bukkit.command.CommandExecutor;

public class VersionControl implements xyz.litewars.litewars.api.support.VersionControl {

    public CommandExecutor VCMainCommand () {
        return (commandSender, command, s, strings) -> {
            if (commandSender.hasPermission("Litewars.admin")) {
                commandSender.sendMessage("当前运行的NMS Version Control版本为1.8.8");
                commandSender.sendMessage(getClass().getName());
            }
            return true;
        };
    }
}
