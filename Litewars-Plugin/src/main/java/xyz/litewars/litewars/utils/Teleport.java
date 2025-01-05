package xyz.litewars.litewars.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.languages.Messages;

import java.util.Objects;

public class Teleport {
    public static void tpPlayerToWorld (Player player, World world) {
        player.sendMessage(Objects.requireNonNull(Messages.readMessage(Messages.TP_TO_WORLD, "&a")).replace("%world%", world.getName()));
        Location spawnLocation = world.getSpawnLocation();
        player.teleport(spawnLocation);
    }
}
