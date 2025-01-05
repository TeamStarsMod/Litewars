package xyz.litewars.litewars.api.scoreboard;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class ScoreBoard {
    private final List<Player> players = new ArrayList<>();

    public void addPlayer (Player p) {
        players.add(p);
    }

    public void removePlayer (Player p) {
        players.remove(p);
    }

    public List<Player> getPlayers () {
        return players;
    }

    public abstract void run ();
}
