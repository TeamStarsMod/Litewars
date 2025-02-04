package xyz.litewars.litewars.commands.litewarssubcommands.normal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.ExceptionUtils;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Utils;

import java.sql.SQLException;

public class Lang extends SubCommand {
    public Lang(LitewarsCommand parent) {
        super(parent, "lang", "", null, true, false);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            sender.sendMessage(Messages.readMessage(player, Messages.NEED_MORE_ARGS));
            return false;
        }
        String uuid = player.getUniqueId().toString();
        String languageName = args[0];
        boolean isFound = false;
        for (String language : RunningData.languages) {
            if (language.equals(languageName)) {
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            sender.sendMessage(Utils.reColor("&c此语言不存在！"));
            return false;
        }
        RunningData.playerLanguageMap.put(player, languageName);
        try {
            RunningData.databaseManager.update("player_datas", new String[]{
                    "language"
            }, new Object[]{
                    languageName
            }, "player_uuid", uuid);
        } catch (SQLException e) {
            ExceptionUtils.printException("在操作数据库时发生异常！", e);
            return false;
        }
        return true;
    }
}
