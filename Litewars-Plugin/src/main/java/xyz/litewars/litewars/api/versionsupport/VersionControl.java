package xyz.litewars.litewars.api.versionsupport;

import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

public interface VersionControl {
    CommandExecutor VCMainCommand ();
    void sendActionBar(Player player, String message);
}
