package xyz.litewars.litewars.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import xyz.litewars.litewars.Litewars;

import static xyz.litewars.litewars.Litewars.server;

public class Lobby {
    public static void initScoreBoard(){
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()){
                    Scoreboard scoreboard = player.getScoreboard(); //获取玩家当前计分板
                    if (scoreboard == null || scoreboard.equals(server.getScoreboardManager().getMainScoreboard())){ //检查计分板是否不存在
                        scoreboard = server.getScoreboardManager().getNewScoreboard();
                    }
                    Objective objective = scoreboard.getObjective("LitewarsLobby"); //检查计分项是否已存在
                    if (objective == null) objective = scoreboard.registerNewObjective("LitewarsLobby", "dummy"); //创建新计分项
                    Score score = objective.getScore("X:");
                    score.setScore(player.getLocation().getBlockX());
                    score = objective.getScore("Y:");
                    score.setScore(player.getLocation().getBlockY());
                    score = objective.getScore("Z:");
                    score.setScore(player.getLocation().getBlockZ());
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                    player.setScoreboard(scoreboard);
                }
            }
        }.runTaskTimer(Litewars.plugin, 0L, 100L);
    }
}
