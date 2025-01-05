package xyz.litewars.litewars.api.game;

public interface GameManager {
    Game[] getRunningGames ();
    Game newGameInstance ();
}
