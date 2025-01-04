package xyz.litewars.litewars.game;

import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.game.Game;

import java.util.ArrayList;
import java.util.List;

public class SimpleGameInstance implements Game {
    private final List<Player> players = new ArrayList<>();
    private final List<String> offlinePlayers = new ArrayList<>();
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
    }
}
