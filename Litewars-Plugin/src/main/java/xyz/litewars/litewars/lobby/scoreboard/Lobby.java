package xyz.litewars.litewars.lobby.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import xyz.litewars.litewars.Litewars;
import xyz.litewars.litewars.LitewarsRunningData;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.api.scoreboard.ScoreBoard;
import xyz.litewars.litewars.supports.papi.PlaceholderAPISupport;
import xyz.litewars.litewars.utils.Utils;

import static xyz.litewars.litewars.Litewars.server;

public class Lobby extends ScoreBoard {
    public void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : getPlayers()) {
                    Scoreboard sb = server.getScoreboardManager().getNewScoreboard();
                    String title = LitewarsRunningData.languageConfig.getString(Messages.LOBBY_SCOREBOARD_TITLE);
                    Objective MainOBJ = sb.getObjective(Utils.reColor(title));
                    if (MainOBJ == null) MainOBJ = sb.registerNewObjective(Utils.reColor(title), "dummy");
                    for (int i = 0; i < LitewarsRunningData.lobbyScoreboardLines.size(); i++) {
                        Score score = MainOBJ.getScore(Utils.reColor(PlaceholderAPISupport.setPlaceholders(player, LitewarsRunningData.lobbyScoreboardLines.get(i))));
                        score.setScore(-i);
                    }
                    MainOBJ.setDisplaySlot(DisplaySlot.SIDEBAR);
                    player.setScoreboard(sb);
                }
            }
        }.runTaskTimer(Litewars.plugin, 0L,100L);
    }
}
