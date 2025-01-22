package xyz.litewars.litewars.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.supports.papi.PlaceholderAPISupport;
import xyz.litewars.litewars.utils.Utils;

public class OnPlayerJoin implements Listener {
    @EventHandler
    public void onJoin (PlayerJoinEvent event) {//Time for dinner
        if (RunningData.config.getBoolean("EditJoinMessage")) {
            event.setJoinMessage(
                    Utils.reColor(
                            PlaceholderAPISupport.setPlaceholders(
                                    event.getPlayer(),
                                    RunningData.config.getString("JoinMessage").replace(
                                            "{player}",
                                            event.getPlayer().getDisplayName()
                                    )
                            )
                    )
            );
        }

        RunningData.lobby.addPlayer(event.getPlayer());
        RunningData.playersInLobby.add(event.getPlayer());
    }
}
