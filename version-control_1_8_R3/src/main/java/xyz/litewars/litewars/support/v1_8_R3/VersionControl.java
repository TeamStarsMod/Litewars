package xyz.litewars.litewars.support.v1_8_R3;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.utils.Utils;

@xyz.litewars.litewars.api.versionsupport.VersionControl.Support
public class VersionControl implements xyz.litewars.litewars.api.versionsupport.VersionControl {
    @Override
    public CommandExecutor VCMainCommand() {
        return (commandSender, command, s, strings) -> {
            if (commandSender.hasPermission("Litewars.admin")) {
                commandSender.sendMessage(Utils.reColor("&7当前正在运行的&bVersion Control&7版本为1.8.8 (By CYsonHab)"));
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

    @Override
    public void sendTitle(Player player, String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        if (player == null) return;

        PlayerConnection connection = ((org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer) player).getHandle().playerConnection;

        // 创建标题和副标题的IChatBaseComponent对象
        IChatBaseComponent titleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', title) + "\"}");
        IChatBaseComponent subTitleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', subTitle) + "\"}");

        // 发送设置标题的包
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleComponent);
        connection.sendPacket(titlePacket);

        // 发送设置副标题的包
        PacketPlayOutTitle subTitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subTitleComponent);
        connection.sendPacket(subTitlePacket);

        // 发送标题持续时间 淡入时间和淡出时间的包
        PacketPlayOutTitle lengthPacket = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
        connection.sendPacket(lengthPacket);
    }

    @Override
    public Entity spawnNoAIVillagerEntity(Location location, String name) {
        WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
        EntityVillager villager = new EntityVillager(world);
        villager.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        // 设置名字和显示名字
        villager.setCustomName(name);
        villager.setCustomNameVisible(true);

        // 设置 NoAI 标签
        NBTTagCompound nbt = new NBTTagCompound();
        villager.c(nbt); // 写入当前NBT
        nbt.setInt("NoAI", 1);
        villager.f(nbt); // 重新应用NBT

        world.addEntity(villager);

        return villager.getBukkitEntity();
    }
}
