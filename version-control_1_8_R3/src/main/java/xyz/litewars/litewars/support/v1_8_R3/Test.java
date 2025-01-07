package xyz.litewars.litewars.support.v1_8_R3;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.command.ParentCommand;

public class Test extends ParentCommand {
    public Test() {
        super("find-bed");
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        Location playerLocation = player.getLocation();
        int radius = 0;
        Block bedBlock = null;

        while (radius < 100 && bedBlock == null) {
            int xMin = playerLocation.getBlockX() - radius;
            int xMax = playerLocation.getBlockX() + radius;
            int zMin = playerLocation.getBlockZ() - radius;
            int zMax = playerLocation.getBlockZ() + radius;

            for (int x = xMin; x <= xMax; x++) {
                for (int z = zMin; z <= zMax; z++) {
                    for (int y = playerLocation.getBlockY() - 3; y <= playerLocation.getBlockY() + 3; y++) {
                        if ((x == xMin || x == xMax || z == zMin || z == zMax) && isBed(player.getWorld().getBlockAt(x, y, z))) {
                            bedBlock = player.getWorld().getBlockAt(x, y, z);
                            break;
                        }
                    }
                    if (bedBlock != null) {
                        break;
                    }
                }
                if (bedBlock != null) {
                    break;
                }
            }

            if (bedBlock == null) {
                radius++;
            }
        }

        if (bedBlock != null) {
            Location location = new Location(bedBlock.getWorld(), bedBlock.getX(), bedBlock.getY(), bedBlock.getZ());
            bedBlock.getWorld().playSound(location, Sound.LEVEL_UP, 1, 1);
            Block block = bedBlock.getWorld().getBlockAt(new Location(bedBlock.getWorld(), bedBlock.getX(), (bedBlock.getY() + 2), bedBlock.getZ()));
            block.setType(Material.REDSTONE_BLOCK);
            player.sendMessage("已在此位置找到床: " + bedBlock.getX() + ", " + bedBlock.getY() + ", " + bedBlock.getZ());
        } else {
            player.sendMessage("在100方块内没有找到床");
        }

        return true;
    }

    private boolean isBed(Block block) {
        return block.getType() == Material.BED_BLOCK;
    }
}