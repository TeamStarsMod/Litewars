package xyz.litewars.litewars.game.gaming;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import xyz.litewars.litewars.Litewars;
import xyz.litewars.litewars.api.arena.Arena;
import xyz.litewars.litewars.api.arena.team.Team;
import xyz.litewars.litewars.api.events.GameStartEvent;
import xyz.litewars.litewars.api.game.Game;
import xyz.litewars.litewars.utils.Utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static xyz.litewars.litewars.Litewars.nms;

public class GameLogic {
    private final Game bindGame;
    private final List<Player> players;
    private final Arena bindArena;
    private Map<Player, Team> playerTeams = new HashMap<>();

    public GameLogic(Game game) {
        this.bindGame = game;
        this.bindArena = game.getArena();
        this.players = game.getPlayers();

        // 开始游戏
        Bukkit.getScheduler().runTask(Litewars.plugin, this::start);
    }

    public void start() {
        Litewars.pluginManager.callEvent(new GameStartEvent(this.bindGame));

        List<Team> teams = bindArena.getTeams();
        Collections.shuffle(players);

        int teamIndex = 0;
        for (Player player : players) {
            Team team = teams.get(teamIndex % teams.size());
            team.addPlayer(player);
            playerTeams.put(player, team);
            teamIndex++;
        }

        for (Team team : teams) {
            Location shopLoc = team.getShop();
            if (shopLoc != null) {
                Villager shopVillager = (Villager) nms.spawnNoAIVillagerEntity(shopLoc, Utils.reColor("&b没错我是升级商店！"));  // 变量备用
                shopVillager.setAdult();
                shopVillager.setCustomName(Utils.reColor("&b没错我是升级商店！"));
                shopVillager.setCustomNameVisible(true);
            }

            Location upgradeLoc = team.getUpgrade();
            if (upgradeLoc != null) {
                Villager upgradeVillager = (Villager) nms.spawnNoAIVillagerEntity(upgradeLoc, Utils.reColor("&b没错我是升级商店！"));  // 变量备用
                upgradeVillager.setAdult();
                upgradeVillager.setCustomNameVisible(true);
            }
        }
    }

    public void setPlayerTeams(Map<Player, Team> playerTeams) {
        this.playerTeams = playerTeams;
    }
}
