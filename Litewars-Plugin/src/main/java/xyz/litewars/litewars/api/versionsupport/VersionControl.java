package xyz.litewars.litewars.api.versionsupport;

import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface VersionControl {
    CommandExecutor VCMainCommand ();
    void sendActionBar(Player player, String message);
    void sendTitle (Player player, String s, String s1, int i1, int i2, int i3);
    Entity spawnNoAIVillagerEntity(Location location, String name);

    @interface Support {}
}
