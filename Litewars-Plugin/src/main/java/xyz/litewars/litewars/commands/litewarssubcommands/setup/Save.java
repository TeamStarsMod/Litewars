package xyz.litewars.litewars.commands.litewarssubcommands.setup;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.Litewars;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.api.arena.Arena;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Teleport;

import java.io.File;
import java.io.IOException;

public class Save extends SubCommand {
    public Save(LitewarsCommand parent) {
        super(parent, "save", "", "Litewars.admin", true, true);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        Arena arena = RunningData.onSetupPlayerMap.get(player);
        try {
            arena.getYaml().save(new File(Litewars.dataFolder, "Data/Arenas/" + arena.getName() + ".yml"));
        } catch (IOException e) {
            player.sendMessage("竞技场配置文件保存失败！请查看控制台！");
            Litewars.logger.severe("无法保存竞技场配置文件，请检查您的文件系统！" + e);
            return false;
        }

        RunningData.onSetupPlayerMap.remove(player);
        sender.sendMessage(Messages.readMessage(Messages.SETTINGS_SAVED, "&a"));
        Teleport.tpPlayerToWorld(player, Bukkit.getWorlds().get(0)); // 将玩家传送回默认世界
        return true;
    }
}
