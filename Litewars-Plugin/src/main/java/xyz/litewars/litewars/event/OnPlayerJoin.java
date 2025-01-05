package xyz.litewars.litewars.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.litewars.litewars.RunningData;

public class OnPlayerJoin implements Listener {
    @EventHandler
    public void onJoin (PlayerJoinEvent event) {
        RunningData.lobby.addPlayer(event.getPlayer());
    }
}
