package xyz.litewars.litewars.commands.litewarssubcommands.normal;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import xyz.litewars.litewars.Litewars;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.api.arena.Arena;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Teleport;
import xyz.litewars.litewars.utils.Utils;

import java.io.File;
import java.io.IOException;

public class Setup extends SubCommand {
    public Setup(LitewarsCommand parent) {
        super (parent, "setup", "", "Litewars.admin", true, false);
    }

    @Override
    public boolean execute (CommandSender sender, Command command, String s, String[] strings) {
        Player player = (Player) sender;
        if (strings.length < 1) {
            sender.sendMessage(Messages.readMessage(Messages.NEED_MORE_ARGS, "&c"));
            return false;
        }

        if (!(new File("./" + strings[0]).exists())) {
            sender.sendMessage(Messages.readMessage(Messages.WORLD_NOT_FOUND, "&c"));
            return false;
        }

        if (Bukkit.getWorlds().get(0).getName().equalsIgnoreCase(strings[0])) {
            sender.sendMessage(Messages.readMessage(Messages.CANT_SETUP_DEFAULT_WORLD, "&c"));
            return false;
        }

        WorldCreator worldCreator = new WorldCreator(strings[0]);
        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.type(WorldType.FLAT);
        worldCreator.generateStructures(false);
        World world1 = worldCreator.createWorld();

        if (world1 != null) {
            world1.setGameRuleValue("doDaylightCycle", "false");
            world1.setGameRuleValue("doWeatherCycle", "false");
            world1.setTime(1000);
            player.sendMessage(Messages.readMessage(Messages.WORLD_LOAD_SUCCESS, "&a"));
            Teleport.tpPlayerToWorld(player, world1);
            if ((new File(Litewars.dataFolder, "Arenas").mkdirs())) Litewars.logger.info("已创建竞技场配置文件夹");
            File arenaFile = new File(Litewars.dataFolder, "Arenas/" + world1.getName() + ".yml");
            try {
                if (arenaFile.createNewFile()) Litewars.logger.info("已创建新竞技场配置文件：" + arenaFile.getName());
            } catch (IOException e) {
                Litewars.logger.severe("无法创建竞技场文件：" + e.getMessage());
            }
            YamlConfiguration config = YamlConfiguration.loadConfiguration(arenaFile);
            if (config.get("World") == null) config.set("World", world1.getName());
            if (config.get("Name") == null) config.set("Name", world1.getName());
            RunningData.onSetupPlayerMap.put(player, new Arena(world1.getName(), config));
            RunningData.lobby.removePlayer(player);
            RunningData.playersInLobby.remove(player);
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard defaultScoreboard = manager.getMainScoreboard();
            player.setScoreboard(defaultScoreboard);
            player.performCommand("lw");
            return true;
        } else {
            player.sendMessage(Messages.readMessage(Messages.WORLD_LOAD_ERROR, "&c"));
            return false;
        }
    }

    @Override
    public String getDescription () {
        return Utils.reColor(String.format("setup <%s> : %s", Messages.readLanguageFile(Messages.WORLD_FOLDER_NAME), Messages.readLanguageFile(Messages.LW_SETUP_MESSAGE)));
    }
}
