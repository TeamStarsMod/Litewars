package xyz.litewars.litewars.support.v1_21_R0;

import com.google.common.base.Preconditions;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.projectile.EntityPotion;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.craftbukkit.v1_21_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_21_R1.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.C;
import xyz.litewars.litewars.utils.Utils;

import java.util.Objects;
import java.util.function.Consumer;


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
        Villager vlg = (Villager) Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.VILLAGER);
        vlg.setAI(false);
        vlg.setInvulnerable(true);
        vlg.setSilent(true);
        vlg.setCustomName(name);
        vlg.setCustomNameVisible(true);
        return vlg;
    }
}
