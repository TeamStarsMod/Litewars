package xyz.litewars.litewars.game.gaming;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.Litewars;
import xyz.litewars.litewars.api.arena.Arena;
import xyz.litewars.litewars.api.arena.team.Team;
import xyz.litewars.litewars.api.events.GameStartEvent;
import xyz.litewars.litewars.api.game.Game;
import xyz.litewars.litewars.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static xyz.litewars.litewars.Litewars.logger;

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
        int playersPerTeam = players.size() / teams.size();
        int remainingPlayers = players.size() % teams.size();

        int playerIndex = 0;
        for (Team team : teams) {
            //生成(升级)商店
            Location shop = team.getShop();
            logger.info(shop.toString());
            Entity shopEntity = bindArena.getArenaWorld().spawnEntity(shop, EntityType.VILLAGER);
            shopEntity.setCustomName(Utils.reColor("&b没错我是商店！\n&c没错我在测试第二行！"));
            shopEntity.setCustomNameVisible(true);
            Location upgrade = team.getUpgrade();
            Entity upgradeEntity = bindArena.getArenaWorld().spawnEntity(upgrade, EntityType.VILLAGER);
            upgradeEntity.setCustomName(Utils.reColor("&b没错我是升级商店！\n&c没错我在测试第二行！"));
            upgradeEntity.setCustomNameVisible(true);

            // 开始分队 (假设目前只有Solo)
            int playersToAdd = playersPerTeam + (remainingPlayers > 0 ? 1 : 0);
            remainingPlayers--;

            for (int i = 0; i < playersToAdd; i++) {
                if (playerIndex < players.size()) {
                    Player player = players.get(playerIndex);
                    team.addPlayer(player);
                    playerTeams.put(player, team);
                    playerIndex++;
                }
            }
        }
    }

    public void setPlayerTeams(Map<Player, Team> playerTeams) {
        this.playerTeams = playerTeams;
    }
}
