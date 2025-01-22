package xyz.litewars.litewars.api.game;

import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.arena.Arena;

import java.util.List;

public interface GameManager {
    List<Game> getRunningGames ();
    Game newGameInstance (Arena bindArena, Player player);
}
