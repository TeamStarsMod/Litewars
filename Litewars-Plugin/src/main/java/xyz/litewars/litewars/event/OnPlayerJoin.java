package xyz.litewars.litewars.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.litewars.litewars.ExceptionUtils;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.supports.papi.PlaceholderAPISupport;
import xyz.litewars.litewars.utils.Utils;

import java.sql.SQLException;

public class OnPlayerJoin implements Listener {
    @EventHandler
    public void onJoin (PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (RunningData.config.getBoolean("EditJoinMessage")) {
            event.setJoinMessage(
                    Utils.reColor(
                            PlaceholderAPISupport.setPlaceholders(
                                    player,
                                    RunningData.config.getString("JoinMessage").replace(
                                            "{player}",
                                            player.getDisplayName()
                                    )
                            )
                    )
            );
        }

        RunningData.lobby.addPlayer(event.getPlayer());
        RunningData.playersInLobby.add(event.getPlayer());

        String playerLanguage = null;
        try {
             playerLanguage = RunningData.databaseManager.getString("player_datas", "language", "player_uuid", player.getUniqueId().toString());
        }catch (SQLException e) {
            ExceptionUtils.printException(e);
        }
        if (playerLanguage != null) {
            RunningData.playerLanguageMap.put(player, playerLanguage);
        }
    }
}
