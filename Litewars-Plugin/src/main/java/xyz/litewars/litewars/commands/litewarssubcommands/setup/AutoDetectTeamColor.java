package xyz.litewars.litewars.commands.litewarssubcommands.setup;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;
import xyz.litewars.litewars.api.arena.team.Colors;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Utils;

import java.util.HashSet;
import java.util.Set;

public class AutoDetectTeamColor extends SubCommand {
    public AutoDetectTeamColor(LitewarsCommand parent) {
        super(parent, "autoDetectTeamColor", "", "Litewars.admin", true, true);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        YamlConfiguration arenaConfig = Utils.getArenaConfig(player);

        World w = player.getWorld();
        if (arenaConfig != null && arenaConfig.get("Team") == null) {
            player.sendMessage(Utils.reColor(Messages.readLanguageFile(Messages.PREFIX) + "&7正在搜索队伍，这可能会导致些许卡顿"));

            Set<String> found = new HashSet<>();

            // 遍历200x100x200的区域
            for (int x = -100; x < 100; x++) {
                for (int y = 50; y < 150; y++) {
                    for (int z = -100; z < 100; z++) {
                        Block b = new Location(w, x, y, z).getBlock();
                        if (isWool(b)) {
                            String woolType = getWoolColor(b);
                            if (!found.contains(woolType)) {
                                int count = 0;
                                // 在找到羊毛后，检查附近30x30x30的区域
                                for (int dx = -15; dx <= 15; dx++) {
                                    for (int dy = -15; dy <= 15; dy++) {
                                        for (int dz = -15; dz <= 15; dz++) {
                                            Block b2 = new Location(w, x + dx, y + dy, z + dz).getBlock();
                                            if (isWool(b2) && getWoolColor(b2).equals(woolType)) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                                // 如果找到5个或更多相同类型的羊毛块
                                if (count >= 5) {
                                    try {
                                        Colors color = Colors.valueOf(woolType.replace("_WOOL", ""));
                                        if (arenaConfig.get("Team." + color) == null) {
                                            found.add(woolType);
                                        }
                                    } catch (IllegalArgumentException ignored) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!found.isEmpty()) {
                for (String s1 : found) {
                    ComponentBuilder builder1 = new ComponentBuilder(Colors.getTeamColor(s1.replace("_WOOL", "")) + "+ 队伍 " + s1.replace("_WOOL", ""));
                    builder1.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Utils.reColor(s1.replace("_WOOL", "") + "\n&7点击添加这个队伍！")).create()));
                    builder1.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/lw addTeamColor " + s1.replace("_WOOL", "")));
                    player.spigot().sendMessage(builder1.create());
                }
                player.sendMessage(Utils.reColor(Messages.readLanguageFile(Messages.PREFIX) + "找到共 &e" + found.size() + " &7个队伍"));
            } else {
                player.sendMessage(Utils.reColor(Messages.readLanguageFile(Messages.PREFIX) + "&7未找到任何队伍，您可能需要自主添加"));
            }
        }
        return false;
    }

    private boolean isWool(Block b) {
        Material type = b.getType();
        return type == Material.WOOL || type.name().contains("WOOL");
    }

    private String getWoolColor(Block b) {
        if (b.getType() == Material.WOOL) {
            MaterialData data = b.getState().getData();
            if (data instanceof Wool wool) {
                return wool.getColor().name() + "_WOOL";
            }
        } else {
            return b.getType().name();
        }
        return null;
    }
}