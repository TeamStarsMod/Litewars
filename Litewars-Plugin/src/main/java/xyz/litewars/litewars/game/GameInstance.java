package xyz.litewars.litewars.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.litewars.litewars.Litewars;
import xyz.litewars.litewars.api.arena.Arena;
import xyz.litewars.litewars.api.arena.ArenaStatus;
import xyz.litewars.litewars.api.arena.team.Team;
import xyz.litewars.litewars.api.events.GameEndEvent;
import xyz.litewars.litewars.api.events.GameStartEvent;
import xyz.litewars.litewars.api.events.GameWaitingEvent;
import xyz.litewars.litewars.api.game.Game;
import xyz.litewars.litewars.game.gaming.GameLogic;
import xyz.litewars.litewars.utils.Utils;

import java.util.*;
import java.util.logging.Logger;

public class GameInstance implements Game {

    private static final Logger log = Litewars.logger;

    private final Set<Player> players = Collections.synchronizedSet(new HashSet<>());
    private final Set<String> offlinePlayers = Collections.synchronizedSet(new HashSet<>());
    private final Map<Player, Team> playerTeams = Collections.synchronizedMap(new HashMap<>());

    private Arena bindArena;
    private boolean started = false;
    private final int maxPlayers = 8;
    private final int minPlayers = 2;

    public GameInstance(Arena bindArena) {
        this.bindArena = bindArena;
        log.info("新建 GameInstance 绑定竞技场: " + bindArena.getName());
    }

    @Override
    public List<Player> getPlayers() {
        synchronized (players) {
            return new ArrayList<>(players);
        }
    }

    @Override
    public void addPlayer(Player player) {
        if (players.add(player)) {
            log.info("玩家 " + player.getName() + " 加入游戏 [" + bindArena.getName() + "]");
            Location lobbyLocation = bindArena.getWaitingLobbyLocation();
            if (lobbyLocation != null) {
                Bukkit.getScheduler().runTask(Litewars.plugin, () -> player.teleport(lobbyLocation));
            } else {
                log.warning("竞技场 " + bindArena.getName() + " 未设置等待大厅位置！");
            }
        }
    }

    @Override
    public void removePlayer(Player player) {
        if (players.remove(player)) {
            log.info("玩家 " + player.getName() + " 离开游戏 [" + bindArena.getName() + "]");
            offlinePlayers.add(player.getName());
        }
    }

    @Override
    public void rejoin(Player player) {
        if (offlinePlayers.remove(player.getName())) {
            addPlayer(player);
            log.info("玩家 " + player.getName() + " 重新加入游戏 [" + bindArena.getName() + "]");
        }
    }

    @Override
    public void startWaiting() {
        log.info("开始等待倒计时 [" + bindArena.getName() + "]");
        for (Player p : getPlayers()) {
            Location lobbyLocation = bindArena.getWaitingLobbyLocation();
            if (lobbyLocation != null) {
                Bukkit.getScheduler().runTask(Litewars.plugin, () -> p.teleport(lobbyLocation));
            }
            p.sendMessage(Utils.reColor("&bLitewars >>> 游戏等待开始……"));
        }

        new BukkitRunnable() {
            private int countDown = 10;
            private boolean counting = false;

            @Override
            public void run() {
                if (started) {
                    cancel();
                    return;
                }

                Litewars.pluginManager.callEvent(new GameWaitingEvent(GameInstance.this));

                int currentPlayers = players.size();

                if (currentPlayers >= minPlayers) {
                    if (!counting) {
                        counting = true;
                        log.info("玩家人数已达标，启动倒计时 [" + bindArena.getName() + "]");
                    }
                    countDown--;

                    if (countDown % 20 == 0 || countDown <= 10) {
                        String msg = Utils.reColor("&b游戏即将开始: " + countDown + "秒");
                        for (Player p : getPlayers()) {
                            p.sendMessage(msg);
                        }
                    }

                    if (countDown <= 0) {
                        started = true;
                        log.info("游戏开始 [" + bindArena.getName() + "]");
                        bindArena.setStatus(ArenaStatus.PLAYING);
                        new GameLogic(GameInstance.this);
                        cancel();
                    }
                } else {
                    if (counting) {
                        counting = false;
                        countDown = 120;
                        log.warning("人数不足, 倒计时取消 [" + bindArena.getName() + "]");
                        for (Player p : getPlayers()) {
                            p.sendMessage(Utils.reColor("&c人数不足！倒计时已取消！"));
                        }
                    }
                }
            }
        }.runTaskTimer(Litewars.plugin, 0L, 20L);
    }

    @Override
    public Arena getArena() {
        return bindArena;
    }

    @Override
    public void setArena(Arena arena) {
        this.bindArena = arena;
    }

    @Override
    public boolean isStart() {
        return started;
    }

    @Override
    public boolean forceStart(CommandSender sender, boolean isDebug) {
        if (started) {
            sender.sendMessage(Utils.reColor("&c你不能开始一个已开始的游戏！"));
            return false;
        }

        if (isDebug || getPlayers().size() >= minPlayers) {
            started = true;
            bindArena.setStatus(ArenaStatus.PLAYING);
            Litewars.pluginManager.callEvent(new GameStartEvent(this));
            log.info("游戏被 " + sender.getName() + " 强制开始 [" + bindArena.getName() + "]");
            sender.sendMessage(Utils.reColor("&a游戏已强制开始！"));
            return true;
        } else {
            sender.sendMessage(Utils.reColor("&c你现在不能开始游戏！输入 /lw start debug 强制开始"));
            return false;
        }
    }

    @Override
    public void forceEnd() {
        Litewars.pluginManager.callEvent(new GameEndEvent(this));
        started = false;
        log.info("游戏强制结束 [" + bindArena.getName() + "]");
    }

    @Override
    public int getMinPlayers() {
        return minPlayers;
    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }
}