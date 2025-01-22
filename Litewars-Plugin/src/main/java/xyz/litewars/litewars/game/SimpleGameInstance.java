package xyz.litewars.litewars.game;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.litewars.litewars.Litewars;
import xyz.litewars.litewars.api.arena.Arena;
import xyz.litewars.litewars.api.events.AsyncGameWaitingEvent;
import xyz.litewars.litewars.api.events.AsyncGameEndEvent;
import xyz.litewars.litewars.api.events.AsyncGameStartEvent;
import xyz.litewars.litewars.api.game.Game;

import java.util.ArrayList;
import java.util.List;

public class SimpleGameInstance implements Game {
    private final List<Player> players = new ArrayList<>();
    private final List<String> offlinePlayers = new ArrayList<>();
    private Arena bindArena;
    private boolean start;
    int maxPlayers = 8;
    int minPlayers = 2;

    public SimpleGameInstance (Arena bindArena) {
        this.bindArena = bindArena;
    }

    @Override
    public List<Player> getPlayers () {
        return players;
    }

    @Override
    public void addPlayer (Player player) {
        players.add(player);
    }

    @Override
    public void removePlayer (Player player) {
        players.remove(player);
    }

    @Override
    public void rejoin (Player player) {
        if (offlinePlayers.contains(player.getName())) {
            players.add(player);
            offlinePlayers.remove(player.getName());
        }
    }

    @Override
    public void startWaiting () {
        new BukkitRunnable() {
            private int countDown = 800;
            private int runTicks = 0;
            @Override
            public void run () {
                runTicks++;
                Litewars.pluginManager.callEvent(new AsyncGameWaitingEvent(SimpleGameInstance.this));
                for (Player p : players) {
                    p.teleport(bindArena.getWaitingLobbyLocation());
                    p.sendMessage("Litewars >>> 游戏等待开始……");
                }
                if (players.size() >= minPlayers) {
                    countDown--;
                    if (countDown % 20 == 0) {
                        for (Player p : players) {
                            Litewars.nms.sendTitle(p, String.valueOf(countDown), "", 0, 23, 0);
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(Litewars.plugin, 0L, 1L);
    }

    @Override
    public Arena getArena() {
        return this.bindArena;
    }

    @Override
    public void setArena(Arena bindArena) {
        this.bindArena = bindArena;
    }

    @Override
    public boolean isStart () {
        return this.start;
    }

    @Override
    public void forceStart () {
        Litewars.pluginManager.callEvent(new AsyncGameStartEvent(this));
        this.start = true;
    }

    @Override
    public void forceEnd () {
        Litewars.pluginManager.callEvent(new AsyncGameEndEvent(this));
        this.start = false;
    }

    @Override
    public int getMinPlayers() {
        return maxPlayers;
    }

    @Override
    public int getMaxPlayers() {
        return minPlayers;
    }
}
