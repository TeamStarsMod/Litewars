package xyz.litewars.litewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.Litewars;
import xyz.litewars.litewars.api.command.ParentCommand;

import java.sql.SQLException;

import static xyz.litewars.litewars.RunningData.databaseManager;

public class AddKillCount extends ParentCommand {
    public AddKillCount(){
        super("addKill");
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player player) {
            try {
                if (!databaseManager.checkDataExists("player_datas", "player_uuid", player.getUniqueId().toString())) {
                    databaseManager.insert("player_datas", new String[] {
                            "player_name",
                            "player_uuid",
                    }, new Object[] {
                            player.getName(),
                            player.getUniqueId().toString(),
                    });
                }

                int kills = databaseManager.getInt("player_datas", "kills", "player_uuid", player.getUniqueId().toString());

                kills++;

                databaseManager.update("player_datas", new String[] {
                        "kills"
                }, new Object[] {
                        kills
                }, "player_name", player.getName());

                player.sendMessage("击杀数已增加到 " + kills);
            } catch (SQLException e) {
                Litewars.logger.severe("在操作数据库时发生错误！" + e.getMessage());
            }
        }
        return true;
    }
}
