package xyz.litewars.litewars.commands.litewarssubcommands.normal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xyz.litewars.litewars.Litewars;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.api.arena.ArenaGroup;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Arenas extends SubCommand {
    public static String arenaGroupListName = "ArenaGroups";

    public Arenas(LitewarsCommand parent) {
        super (parent, "arenas", "", null, false, false, "create", "remove", "list");
    }

    @Override
    public boolean execute (CommandSender sender, Command command, String s, String[] strings) {
        if (RunningData.dataConfig == null) {
            sender.sendMessage(Utils.reColor("&c似乎你的配置文件没有加载上..."));
            return false;
        }
        if (strings.length == 0) {
            sender.sendMessage(Utils.reColor("&c也许需要更多参数……"));
            return false;
        }
        switch (strings[0].toLowerCase()) {
            case "create" -> {
                if (strings.length < 2) {
                    sender.sendMessage(Utils.reColor("&c你也许需要输入竞技场名称..."));
                    return false;
                }
                List<String> arenaGroupList = RunningData.dataConfig.getStringList(arenaGroupListName);
                arenaGroupList = arenaGroupList == null ? new ArrayList<>() : arenaGroupList; //神秘三元运算符(
                StringBuilder builder = new StringBuilder();
                for (int i = 1; i < strings.length; i++) {
                    builder.append(strings[i]).append(" ");
                }
                String result = builder.toString().trim().toLowerCase();
                if (arenaGroupList.contains(result)) {
                    sender.sendMessage(Utils.reColor("&c已经有一个一模一样得啦！"));
                    return false;
                }
                arenaGroupList.add(result);
                RunningData.dataConfig.set(arenaGroupListName, arenaGroupList);
                try {
                    RunningData.dataConfig.save(RunningData.dataConfigFile);
                }catch (IOException e) {
                    Litewars.logger.severe("憨批你写的代码又炸了！！！！！！ " + e);
                }
                RunningData.arenaGroupMap.put(result, new ArenaGroup(result));
                sender.sendMessage(Utils.reColor("&a添加完成咯~ &e" + result));
                return true;
            }

            case "remove" -> {
                if (strings.length < 2) {
                    sender.sendMessage(Utils.reColor("&c参数都不填完，你还是人吗！"));
                    return false;
                }
                List<String> arenaGroupList = RunningData.dataConfig.getStringList(arenaGroupListName);
                if (arenaGroupList == null) {
                    sender.sendMessage(Utils.reColor("&c你的配置文件被你玩坏了！"));
                    return false;
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i <= strings.length - 1; i++) {
                    sb.append(strings[i]).append(" ");
                }
                String result = sb.toString().trim().toLowerCase();
                if (arenaGroupList.contains(result)) {
                    arenaGroupList.remove(result);
                } else {
                    sender.sendMessage(Utils.reColor("&c你真的确定有这个掉东西吗^_^"));
                    return false;
                }
                RunningData.dataConfig.set(arenaGroupListName, arenaGroupList);
                try {
                    RunningData.dataConfig.save(RunningData.dataConfigFile);
                }catch (IOException e) {
                    Litewars.logger.severe("憨批你写的代码又炸了！！！！！！ " + e);
                }
                RunningData.arenaGroupMap.remove(result);
                return true;
            }

            case "list" -> {
                List<String> arenaList = RunningData.dataConfig.getStringList(arenaGroupListName);
                if (arenaList == null) {
                    sender.sendMessage(Utils.reColor("&c你没有配置文件了！"));
                    return false;
                }//do not do this!
                StringBuilder builder = new StringBuilder();
                builder.append("&e所有竞技场组\n");
                for (String str : arenaList) {
                    builder.append(str).append("\n");
                }
                builder.append("&b共计 ").append(arenaList.size()).append(" 个竞技场组");
                sender.sendMessage(Utils.reColor(builder.toString()));
                return true;
            }

            default -> {
                sender.sendMessage(Utils.reColor("&c参数不对哦亲"));
                return false;
            }
        }
    }

    @Override
    public String getDescription () {
        return Utils.reColor(String.format("arenas [%s] : %s", Messages.readLanguageFile(Messages.ARENA_GROUP), Messages.readLanguageFile(Messages.LW_ARENAS_MESSAGE)));
    }
}
