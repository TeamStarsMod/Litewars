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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tips {
    private static boolean isStart = false;
    private static BukkitTask task;
    private static List<String> tips;
    private static YamlConfiguration tipsYaml;

    public static void init() throws IOException {
        File tipsFile = new File(Litewars.dataFolder, "Tips.yml");
        if (tipsFile.createNewFile()) Litewars.logger.info("已创建Tips文件");

        tipsYaml = YamlConfiguration.loadConfiguration(tipsFile);
        tips = tipsYaml.getStringList("Tips");
        if (tips == null || tips.isEmpty()) {
            tips = new ArrayList<>();
            tips.add("在资源上比不过你的对手? 试试合理分配队伍资源吧！");
            tips.add("对手在战斗中总是占据上风? 试试分析并模仿他们的战斗技巧");
            tips.add("&c认为您的对手有作弊嫌疑? 使用/report举报他们！");
            tips.add("&a你喜欢&3Lite&ewars&a吗?");
            tips.add("&b还有什么Tip是我能写的呢qwq...");
            tips.add("你知道吗? 这些Tip可以在插件目录的Tips.yml下自定义！");
            tips.add("谢谢你使用Litewars！");
            tipsYaml.set("Tips", tips);

            if (!tipsYaml.contains("UpdateSpeed")) {
                tipsYaml.set("UpdateSpeed", 40L);
            }

            tipsYaml.save(tipsFile);
            Litewars.logger.info("已添加默认Tips，在Tips.yml自定义！");
        }
    }

    private static void startTipsTask() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (!isStart) {
                    this.cancel();
                    return;
                }

                if (!tips.isEmpty()) {
                    String tip = tips.get(new Random().nextInt(tips.size()));
                    for (Player player : RunningData.playersInLobby) {
                        Litewars.nms.sendActionBar(player, Utils.reColor("&e" + tip));
                    }
                }
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
        }
    }
}
