package xyz.litewars.litewars.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import xyz.litewars.litewars.Litewars;
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
                    String title = "&3Lite&ewars";
                    Objective MainOBJ = sb.getObjective(Utils.reColor(title));
                    if (MainOBJ == null) MainOBJ = sb.registerNewObjective(Utils.reColor(title), "dummy");
                    Score score = MainOBJ.getScore("");
                    score.setScore(6);
                    score = MainOBJ.getScore(Utils.reColor(PlaceholderAPISupport.setPlaceholders(player, "&7%player_name%")));
                    score.setScore(5);
                    score = MainOBJ.getScore(PlaceholderAPISupport.setPlaceholders(player, "击杀数: %litewars_kills%"));
                    score.setScore(4);
                    score = MainOBJ.getScore("");
                    score.setScore(3);
                    score = MainOBJ.getScore(Utils.reColor("&b欢迎来到"));
                    score.setScore(2);
                    score = MainOBJ.getScore(Utils.reColor("&3Lite&ewars"));
                    score.setScore(1);
                    MainOBJ.setDisplaySlot(DisplaySlot.SIDEBAR);
                    player.setScoreboard(sb);
                }
            }
        }.runTaskTimer(Litewars.plugin, 0L,100L);
    }
}
