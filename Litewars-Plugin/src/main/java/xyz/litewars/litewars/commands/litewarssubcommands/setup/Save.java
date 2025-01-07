package xyz.litewars.litewars.commands.litewarssubcommands.setup;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Teleport;

public class Save extends SubCommand {
    public Save(LitewarsCommand parent) {
        super(parent, "save", "", "Litewars.admin", true, true);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        RunningData.onSetupPlayerMap.remove(player);
        sender.sendMessage(Messages.readMessage(Messages.SETTINGS_SAVED, "&a"));
        Teleport.tpPlayerToWorld(player, Bukkit.getWorlds().get(0)); // 将玩家传送回默认世界
        return true;
    }
}
