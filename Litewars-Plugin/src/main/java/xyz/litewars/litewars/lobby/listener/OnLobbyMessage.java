package xyz.litewars.litewars.lobby.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.supports.papi.PlaceholderAPISupport;
import xyz.litewars.litewars.utils.Utils;

public class OnLobbyMessage implements Listener {
    @EventHandler
    public static void onMessage(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if (RunningData.playersInLobby.contains(player)) {
            e.setCancelled(true);
            Bukkit.broadcastMessage(
                    Utils.reColor(
                            PlaceholderAPISupport.setPlaceholders(
                                    player,
                                    RunningData.config.getString("LobbyMessageType")
                                            .replace("{message}", e.getMessage())
                            )
                    )
            );
        }
    }
}
