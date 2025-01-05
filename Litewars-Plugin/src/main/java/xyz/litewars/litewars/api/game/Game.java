package xyz.litewars.litewars.api.game;

import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.arena.Arena;

import java.util.List;

public interface Game {
    List<Player> getPlayers ();
    void addPlayer (Player player);
    void removePlayer (Player player);
    void rejoin (Player player);
    void startWaiting ();
    Arena getArena ();
    void setArena (Arena arena);
    boolean isStart ();
    void forceStart ();
    void forceEnd ();
}