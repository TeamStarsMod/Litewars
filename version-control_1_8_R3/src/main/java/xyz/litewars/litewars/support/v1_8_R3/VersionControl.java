package xyz.litewars.litewars.support.v1_8_R3;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.Litewars;
import xyz.litewars.litewars.utils.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class VersionControl implements xyz.litewars.litewars.api.versionsupport.VersionControl {
    @Override
    public CommandExecutor VCMainCommand() {
        return (commandSender, command, s, strings) -> {
            if (commandSender.hasPermission("Litewars.admin")) {
                commandSender.sendMessage(Utils.reColor("&7当前正在运行的&bVersion Control&7版本为1.8.8 (By cyh2)"));
                commandSender.sendMessage(Utils.reColor("&7" + getClass().getName()));
                return true;
            }
            return false;
        };
    }

    @Override
    public void sendActionBar(Player player, String message) {
        try {
            String serverVersion = Bukkit.getServer().getClass().getName().split("\\.")[3];

            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + serverVersion + ".entity.CraftPlayer");
            Object craftPlayer = craftPlayerClass.cast(player);

            Class<?> nmsPlayerClass = Class.forName("net.minecraft.server." + serverVersion + "EntityPlayer");
            Object nmsPlayer = craftPlayerClass.getMethod("getHandle").invoke(craftPlayer);

            Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server." + serverVersion + ".PacketPlayOutChat");
            Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + serverVersion + ".ChatMessageType");
            Field chatMessageTypeField = chatMessageTypeClass.getField("GAME_INFO");

            Constructor<?> packetConstructor = packetPlayOutChatClass.getConstructor(
                    chatMessageTypeClass,
                    String.class
            );
            Object packet = packetConstructor.newInstance(
                    chatMessageTypeField.get(null),
                    ChatColor.translateAlternateColorCodes('&', message)
            );

            Class<?> playerConnectionClass = Class.forName("net.minecraft.server." + serverVersion + ".PlayerConnection");
            Object playerConnection = nmsPlayerClass.getField("playerConnection").get(nmsPlayer);

            Method sendPacketMethod = playerConnectionClass.getMethod("sendPacket", Class.forName("net.minecraft.server." + serverVersion + ".Packet"));
            sendPacketMethod.invoke(playerConnection, packet);
        } catch (Exception e) {
            Litewars.logger.severe("发生错误！" + e);
        }
    }
}
