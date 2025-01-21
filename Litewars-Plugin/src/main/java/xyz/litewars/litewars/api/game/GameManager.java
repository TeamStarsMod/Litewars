package xyz.litewars.litewars.api.game;

import org.bukkit.entity.Player;
import xyz.litewars.litewars.api.arena.Arena;

public interface GameManager {
    Game[] getRunningGames ();
    Game newGameInstance (Arena bindArena, Player player);
}
