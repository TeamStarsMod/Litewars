package xyz.litewars.litewars.support.v1_8_R3;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.command.CommandExecutor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.utils.Utils;

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
        if (player == null || message == null || message.isEmpty()) {
            return;
        }

        // 将消息转换为IChatBaseComponent
        IChatBaseComponent chatComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message + "\"}");

        // 创建PacketPlayOutChat对象，ActionBar类型为2
        PacketPlayOutChat packet = new PacketPlayOutChat(chatComponent, (byte) 2);

        // 将Packet发送给玩家
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
