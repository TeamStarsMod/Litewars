package xyz.litewars.litewars.commands.litewarssubcommands;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Teleport;
import xyz.litewars.litewars.utils.Utils;

import java.io.File;

public class setup extends SubCommand {
    public setup (LitewarsCommand parent) {
        super (parent, "setup", "", "Litewars.admin");
    }

    @Override
    public boolean execute (CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (strings.length < 1){
                sender.sendMessage(Messages.readMessage(Messages.NEED_MORE_ARGS, "&c"));
                return false;
            }

            if (!(new File("./" + strings[0]).exists())){
                sender.sendMessage(Messages.readMessage(Messages.WORLD_NOT_FOUND, "&c"));
                return false;
            }

            WorldCreator worldCreator = new WorldCreator(strings[0]);
            worldCreator.environment(World.Environment.NORMAL);
            worldCreator.type(WorldType.FLAT);
            worldCreator.generateStructures(false);
            World world1 = worldCreator.createWorld();

            if (world1 != null) {
                player.sendMessage(Messages.readMessage(Messages.WORLD_LOAD_SUCCESS, "&a"));
                Teleport.tpPlayerToWorld(player, world1);
                return true;
            }else {
                player.sendMessage(Messages.readMessage(Messages.WORLD_LOAD_ERROR, "&c"));
                return false;
            }

        }else {
            sender.sendMessage(Messages.readMessage(Messages.ONLY_PLAYERS, "&c"));
            return false;
        }
    }

    @Override
    public String getDescription () {
        return Utils.reColor("&2setup <世界文件夹名称> : 设置竞技场");
    }
}
