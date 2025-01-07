package xyz.litewars.litewars.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.litewars.litewars.RunningData;

public class OnPlayerLeave implements Listener {
    @EventHandler
    public static void onLeave(PlayerQuitEvent e){
        Player player = e.getPlayer();
        RunningData.onSetupPlayerMap.remove(player);
    }
}
