package xyz.litewars.litewars.lobby.tips;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import xyz.litewars.litewars.Litewars;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Tips {
    private static boolean isStart = false;
    private static BukkitTask task;
    private static List<String> tips;
    private static YamlConfiguration tipsYaml;
    private static int repeatCount = 0;
    private static int currentTipIndex = -1;

    public static void init() throws IOException {
        Files.copy(Objects.requireNonNull(Tips.class.getClassLoader().getResourceAsStream("Tips.yml")), Path.of(Litewars.dataFolder + "Tips.yml"));
        File tipsFile = new File(Litewars.dataFolder, "Tips.yml");
        tipsYaml = YamlConfiguration.loadConfiguration(tipsFile);
        tips = tipsYaml.getStringList("Tips");
        repeatCount = tipsYaml.getInt("RepeatCount");
    }

    private static void startTipsTask() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (!isStart) {
                    this.cancel();
                    return;
                }

                if (currentTipIndex == -1 || repeatCount == 0) {
                    currentTipIndex = new Random().nextInt(tips.size());
                    repeatCount = tipsYaml.getInt("RepeatCount");
                }

                String tip = tips.get(currentTipIndex);
                for (Player player : RunningData.playersInLobby) {
                    Litewars.nms.sendActionBar(player, Utils.reColor("&e" + tip));
                }

                repeatCount--;
            }
        }.runTaskTimer(Litewars.plugin, 0L, tipsYaml.getLong("UpdateSpeed"));
    }

    public static void startTips() {
        isStart = true;
        if (task == null) {
            startTipsTask();
        }
    }

    public static void stopTips() {
        isStart = false;
        if (task != null) {
            task.cancel();
            task = null;
            currentTipIndex = -1;
            repeatCount = 0;
        }
    }
}
