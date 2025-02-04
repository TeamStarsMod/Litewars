package xyz.litewars.litewars.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.Litewars;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.api.languages.Messages;

import java.util.Objects;

public class Teleport {
    public static void tpPlayerToWorld (Player player, World world, boolean silent) {
        if (!silent) {
            player.sendMessage(Objects.requireNonNull(Messages.readMessage(Messages.TP_TO_WORLD, "&a")).replace("%world%", world.getName()));
        }
        Location spawnLocation = world.getSpawnLocation();
        player.teleport(spawnLocation);
    }
    public static void tpPlayerToWorld (Player player, World world) {
        tpPlayerToWorld(player, world, false);
    }

    public static void tpPlayerToLobby(Player player, boolean silent) {
        if (!silent) {
            player.sendMessage(Objects.requireNonNull(Messages.readMessage(Messages.TP_TO_LOBBY, "&a")));
        }
        String lobbyWorldName = RunningData.dataConfig.getString("Lobby.world");
        if (lobbyWorldName == null) {
            player.sendMessage(Messages.readMessage(Messages.SOMETHING_WAS_WRONG, "&c"));
            Litewars.logger.warning("未配置主大厅！玩家 " + player.getDisplayName() + " 已被传送至默认世界出生点！");
            tpPlayerToWorld(player, Bukkit.getWorlds().get(0));
            return;
        }
        Location location = LocationUtils.getLocation(
                RunningData.dataConfig.getFloatList("Lobby.login-location"),
                Bukkit.getWorld(lobbyWorldName)
        );

        player.setGameMode(GameMode.ADVENTURE);

        if (location != null) {
            player.teleport(location);
        }else {
            player.sendMessage(Messages.readMessage(Messages.SOMETHING_WAS_WRONG, "&c"));
            Litewars.logger.warning("未配置主大厅！玩家 " + player.getDisplayName() + " 已被传送至默认世界出生点！");
            tpPlayerToWorld(player, Bukkit.getWorlds().get(0));
        }
    }
    public static void tpPlayerToLobby(Player player) {
        tpPlayerToLobby(player, false);
    }
}
