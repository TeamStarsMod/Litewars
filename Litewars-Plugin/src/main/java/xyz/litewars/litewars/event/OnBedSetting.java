package xyz.litewars.litewars.event;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.api.arena.team.Team;

import java.util.Map;

public class OnBedSetting implements Listener {
    @EventHandler
    public void onBedSetting (PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            if (event.getClickedBlock().getType().equals(Material.BED_BLOCK)) {
                Player p = event.getPlayer();
                Map<Player, Boolean> booleanMap = RunningData.onSetupData.getBooleanMap("PlayerBedSetting");
                Map<Player, Team> teamMap = RunningData.onSetupData.getTeamMap("PlayerTeam");
                if (booleanMap.containsKey(p)) {
                    p.sendMessage("设置成功！");
                    Team team = teamMap.get(p);
                    team.setBed(event.getClickedBlock());
                    booleanMap.remove(p);
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBedSetting$1 (BlockBreakEvent event) {
        if (event.getBlock().getType().equals(Material.BED_BLOCK)) {
            Player p = event.getPlayer();
            Map<Player, Boolean> booleanMap = RunningData.onSetupData.getBooleanMap("PlayerBedSetting");
            Map<Player, Team> teamMap = RunningData.onSetupData.getTeamMap("PlayerTeam");
            if (booleanMap.containsKey(p)) {
                p.sendMessage("设置成功！");
                Team team = teamMap.get(p);
                team.setBed(event.getBlock());
                booleanMap.remove(p);
                event.setCancelled(true);
            }
        }
    }
}
