package xyz.litewars.litewars.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import xyz.litewars.litewars.Litewars;
import xyz.litewars.litewars.api.socreboard.ScoreBoard;
import xyz.litewars.litewars.utils.Utils;

import static xyz.litewars.litewars.Litewars.server;

public class Lobby extends ScoreBoard {
    public void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : getPlayers()) {
                    Scoreboard sb = server.getScoreboardManager().getNewScoreboard();
                    String title = "Litewars - Lobby";
                    Objective MainOBJ = sb.getObjective(Utils.reColor(title));
                    if (MainOBJ == null) MainOBJ = sb.registerNewObjective(Utils.reColor(title), "dummy");
                    MainOBJ.getScore(Utils.reColor("&bLitewars"));
                    MainOBJ.setDisplaySlot(DisplaySlot.SIDEBAR);
                    p.setScoreboard(sb);
                }
            }
        }.runTaskTimer(Litewars.plugin, 0L,100L);
    }
}
