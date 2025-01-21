package xyz.litewars.litewars.game;

import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.arena.Arena;
import xyz.litewars.litewars.api.game.Game;

import java.util.ArrayList;
import java.util.List;

public class SimpleGameInstance implements Game {
    private final List<Player> players = new ArrayList<>();
    private final List<String> offlinePlayers = new ArrayList<>();
    private Arena bindArena;
    private boolean start;
    int maxPlayers = 0;
    int minPlayers = 0;

    public SimpleGameInstance (Arena bindArena) {
        this.bindArena = bindArena;
    }

    @Override
    public List<Player> getPlayers () {
        return players;
    }

    @Override
    public void addPlayer (Player player) {
        player.teleport(bindArena.getWaitingLobbyLocation());
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
        this.start = true;
    }

    @Override
    public void forceEnd () {
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
