package xyz.litewars.litewars.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.supports.papi.PlaceholderAPISupport;
import xyz.litewars.litewars.utils.Teleport;
import xyz.litewars.litewars.utils.Utils;

public class OnPlayerJoin implements Listener {
    @EventHandler
    public void onJoin (PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (RunningData.mainConfig.getBoolean("EditJoinMessage")) {
            event.setJoinMessage(
                    Utils.reColor(
                            PlaceholderAPISupport.setPlaceholders(
                                    player,
                                    RunningData.mainConfig.getString("JoinMessage").replace(
                                            "{player}",
                                            player.getDisplayName()
                                    )
                            )
                    )
            );
        }

        RunningData.lobbyManager.addPlayer(event.getPlayer());
        RunningData.lobbyPlayers.add(event.getPlayer());

        Teleport.tpPlayerToLobby(player, true); //"Silent"参数指是否显示传送信息
    }
}
