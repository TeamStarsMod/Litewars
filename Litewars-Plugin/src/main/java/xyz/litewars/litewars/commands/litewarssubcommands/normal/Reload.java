package xyz.litewars.litewars.commands.litewarssubcommands.normal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.Litewars;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.lobby.scoreboard.Lobby;
import xyz.litewars.litewars.lobby.tips.Tips;
import xyz.litewars.litewars.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static xyz.litewars.litewars.Litewars.*;

public class Reload extends SubCommand {
    public Reload(LitewarsCommand parent) {
        super(parent, "reload", "", "Litewars.admin", false, false);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        RunningData.lobby = new Lobby();
        RunningData.config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
        RunningData.languageName = RunningData.config.getString("language");
        RunningData.languageFiles.clear();
        RunningData.languages.clear();
        RunningData.lobbyScoreboardLines = new HashMap<>();
        for (File languageFile : (Objects.requireNonNull(new File(dataFolder, "Languages").listFiles()))) {
            if (languageFile.getName().contains(".yml") || languageFile.getName().contains(".yaml")) {
                String languageName = languageFile.getName().replace(".yml", "").replace(".yaml", "");
                YamlConfiguration langYaml = YamlConfiguration.loadConfiguration(languageFile);
                RunningData.languages.add(languageName);
                RunningData.languageFiles.put(languageName, langYaml);
                String name = langYaml.getString("Name");
                logger.info("加载语言：" + languageName + " - " + (name == null ? "未知" : name));
            }
        }
        RunningData.languageFiles.forEach((name, yaml) -> {
            List<String> list = yaml.getStringList(Messages.LOBBY_SCOREBOARD_LINES);
            List<String> re = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isEmpty()) {
                    String[] strings = String.valueOf(i).split("");
                    StringBuilder line = new StringBuilder();
                    for (String s1 : strings) {
                        line.append("&").append(s1);
                    }
                    re.add(i, Utils.reColor(line.toString()));
                } else {
                    re.add(i, list.get(i));
                }
            }

            RunningData.lobbyScoreboardLines.put(name, re);
        });
        for (Player p : Bukkit.getOnlinePlayers()) {
            RunningData.lobby.addPlayer(p);
            RunningData.playersInLobby.add(p);
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
