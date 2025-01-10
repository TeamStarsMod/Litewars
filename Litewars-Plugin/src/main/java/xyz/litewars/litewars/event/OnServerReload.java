package xyz.litewars.litewars.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import xyz.litewars.litewars.Litewars;
import xyz.litewars.litewars.RunningData;

public class OnServerReload implements Listener {
    @EventHandler
    public void onLoad (PluginEnableEvent event) {
        if (event.getPlugin().getName().equals(Litewars.plugin.getName())) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                RunningData.lobby.addPlayer(p);
                RunningData.playersInLobby.add(p);
            }
        }
    }
}
