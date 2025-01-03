package xyz.litewars.litewars.api.arena.interfaces;

import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.arena.ArenaStatus;

import java.util.List;

public interface GameArena {
    //我想想要写什么
    void setName(String name);
    String getName();
    void addPlayer (Player player);
    void removePlayer (Player player);
    List<Player> getPlayers ();
    void setStatus (ArenaStatus status);
    ArenaStatus getStatus ();

}
