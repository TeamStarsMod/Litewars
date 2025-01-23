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
    public static void tpPlayerToWorld (Player player, World world) {
        player.sendMessage(Objects.requireNonNull(Messages.readMessage(Messages.TP_TO_WORLD, "&a")).replace("%world%", world.getName()));
        Location spawnLocation = world.getSpawnLocation();
        player.teleport(spawnLocation);
    }

    public static void tpPlayerToLobby(Player player) {
        player.sendMessage(Objects.requireNonNull(Messages.readMessage(Messages.TP_TO_LOBBY, "&a")));
        Location location = LocationUtils.getLocation(
                RunningData.dataConfig.getFloatList("Lobby.login-location"),
                Bukkit.getWorld(RunningData.dataConfig.getString("Lobby.world"))
        );

        player.setGameMode(GameMode.ADVENTURE);

        if (location != null) {
            player.teleport(location);
        }else {
            player.sendMessage(Utils.reColor("&c在将你传送至主大厅时出现了问题！正在尝试将你传送至默认世界出生点！"));
            Litewars.logger.warning("未配置主大厅坐标！玩家 " + player.getDisplayName() + " 已被传送至默认世界出生点！");
            tpPlayerToWorld(player, Bukkit.getWorlds().get(0));
        }
    }
}
