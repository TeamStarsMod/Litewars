package xyz.litewars.litewars.commands.litewarssubcommands.normal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.Litewars;
import xyz.litewars.litewars.LitewarsRunningData;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.lobby.scoreboard.Lobby;
import xyz.litewars.litewars.lobby.tips.Tips;
import xyz.litewars.litewars.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static xyz.litewars.litewars.Litewars.plugin;

public class Reload extends SubCommand {
    public Reload(LitewarsCommand parent) {
        super(parent, "reload", "", "Litewars.admin", false, false);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        LitewarsRunningData.lobbyManager = new Lobby();
        LitewarsRunningData.mainConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
        LitewarsRunningData.languageCode = LitewarsRunningData.mainConfig.getString("language");
        LitewarsRunningData.languageConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "Languages/" + LitewarsRunningData.languageCode + ".yml"));
        List<String> list = LitewarsRunningData.languageConfig.getStringList(Messages.LOBBY_SCOREBOARD_LINES);
        LitewarsRunningData.lobbyScoreboardLines = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isEmpty()) {
                String[] strings = String.valueOf(i).split("");
                StringBuilder line = new StringBuilder();
                for (String str : strings) {
                    line.append("&").append(str);
                }
                LitewarsRunningData.lobbyScoreboardLines.add(i, Utils.reColor(line.toString()));
            } else {
                LitewarsRunningData.lobbyScoreboardLines.add(i, list.get(i));
            }
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            LitewarsRunningData.lobbyManager.addPlayer(p);
            LitewarsRunningData.lobbyPlayers.add(p);
        }
        Tips.stopTips();
        try {
            Tips.init();
        }catch (IOException e){
            Litewars.logger.severe("插件在重载时遇到错误！" + e);
            return false;
        }
        Tips.startTips();

        sender.sendMessage(ChatColor.GREEN + "Reload completed");
        sender.sendMessage(ChatColor.YELLOW + "Some options may be to restart server");
        return true;
    }
}
