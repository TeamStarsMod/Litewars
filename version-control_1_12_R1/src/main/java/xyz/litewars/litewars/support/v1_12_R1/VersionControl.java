package xyz.litewars.litewars.support.v1_12_R1;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.utils.Utils;

public class VersionControl implements xyz.litewars.litewars.api.versionsupport.VersionControl {
    @Override
    public CommandExecutor VCMainCommand() {
        return (commandSender, command, s, strings) -> {
            if (commandSender.hasPermission("Litewars.admin")) {
                commandSender.sendMessage(Utils.reColor("&7当前正在运行的&bVersion Control&7版本为1.12.2 (By NekoEpisode)"));
                commandSender.sendMessage(Utils.reColor("&7" + getClass().getName()));
                return true;
            }
            return false;
        };
    }

    @Override
    public void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }

    @Override
    public void sendTitle (Player player, String s, String s1, int i1, int i2, int i3) {
        player.sendTitle(s, s1, i1, i2, i3);
    }
}
