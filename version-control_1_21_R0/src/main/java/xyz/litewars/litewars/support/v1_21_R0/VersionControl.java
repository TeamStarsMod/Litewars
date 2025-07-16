package xyz.litewars.litewars.support.v1_21_R0;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.npc.EntityVillager;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.craftbukkit.v1_21_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.utils.Utils;

import java.util.Objects;


@xyz.litewars.litewars.api.versionsupport.VersionControl.Support
public class VersionControl implements xyz.litewars.litewars.api.versionsupport.VersionControl {

    @Override
    public CommandExecutor VCMainCommand() {
        return (commandSender, command, s, strings) -> {
            if (commandSender.hasPermission("Litewars.admin")) {
                commandSender.sendMessage(Utils.reColor("&7当前正在运行的&bVersion Control&7版本为1.21.1 (By NekoEpisode/CYsonHab)"));
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
    public void sendTitle(Player player, String s, String s1, int i1, int i2, int i3) {
        player.sendTitle(s, s1, i1, i2, i3);
    }

    @Override
    public Entity spawnNoAIVillagerEntity(Location location, String name) {
        // 获取NMS世界对象
        WorldServer world = ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle();

        // 创建村民实体
        EntityVillager villager = new EntityVillager(EntityTypes.bj, world);

        // 设置实体位置和朝向
        villager.a(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        // 设置无AI属性
        NBTTagCompound nbt = new NBTTagCompound();
        villager.c(nbt);          // 读取当前NBT数据
        nbt.a("NoAI", 1);         // 设置NoAI标记
        villager.f(nbt);          // 重新应用NBT数据

        // 设置自定义名字和名字可见
        villager.a(name);
        villager.a(true);

        // 将实体添加到世界中
        world.a(villager);

        // 返回Bukkit实体
        return villager.getBukkitEntity();
    }
}
