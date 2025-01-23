package xyz.litewars.litewars.game;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.litewars.litewars.Litewars;
import xyz.litewars.litewars.api.arena.Arena;
import xyz.litewars.litewars.api.arena.ArenaStatus;
import xyz.litewars.litewars.api.arena.team.Team;
import xyz.litewars.litewars.api.events.AsyncGameWaitingEvent;
import xyz.litewars.litewars.api.events.AsyncGameEndEvent;
import xyz.litewars.litewars.api.events.GameStartEvent;
import xyz.litewars.litewars.api.game.Game;
import xyz.litewars.litewars.game.gaming.GameLogic;
import xyz.litewars.litewars.utils.Utils;

import java.util.*;

public class GameInstance implements Game {
    private final List<Player> players = new ArrayList<>();
    private final List<String> offlinePlayers = new ArrayList<>();
    private Map<Player, Team> playerTeams = new HashMap<>();
    private Arena bindArena;
    private boolean start;
    int maxPlayers = 8;
    int minPlayers = 2;

    public GameInstance(Arena bindArena) {
        this.bindArena = bindArena;
    }

    @Override
    public List<Player> getPlayers () {
        return players;
    }

    @Override
    public void addPlayer (Player player) {
        players.add(player);
    }

    @Override
    public void removePlayer (Player player) {
        players.remove(player);
    }

    @Override
    public void rejoin (Player player) {
        if (offlinePlayers.contains(player.getName())) {
            players.add(player);
            offlinePlayers.remove(player.getName());
        }
    }

    @Override
    public void startWaiting () {
        for (Player p : players) {
            Bukkit.getServer().getScheduler().runTask(Litewars.plugin, () -> p.teleport(bindArena.getWaitingLobbyLocation()));
            p.sendMessage("Litewars >>> 游戏等待开始……");
        }
        new BukkitRunnable() {
            private int countDown = 20;
            private boolean counting = false;
            private int runTicks = 0;
            @Override
            public void run () {
                if (!start) {
                    runTicks++;
                    Litewars.pluginManager.callEvent(new AsyncGameWaitingEvent(GameInstance.this));
                    if (players.size() >= minPlayers) {
                        if (!counting) counting = true;
                        countDown--;
                        if (countDown % 20 == 0) {
                            for (Player p : players) {
                                p.sendMessage(Utils.reColor("&b" + countDown / 20));
                                Litewars.nms.sendTitle(p, Utils.reColor("&b" + countDown / 20), "", 0, 23, 0);
                            }
                        }
                        if (countDown <= 0) {
                            start = true;
                            new GameLogic(GameInstance.this);
                            bindArena.setStatus(ArenaStatus.PLAYING);
                            this.cancel();
                        }
                    } else {
                        if (counting) {
                            counting = false;
                            countDown = 800;
                            for (Player player : players) {
                                player.sendMessage(Utils.reColor("&c人数不足！倒计时已取消！"));
                            }
                        }
                    }
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously(Litewars.plugin, 0L, 1L);
    }

    @Override
    public Arena getArena() {
        return this.bindArena;
    }

    @Override
    public void setArena(Arena bindArena) {
        this.bindArena = bindArena;
    }

    @Override
    public boolean isStart () {
        return this.start;
    }

    @Override
    public boolean forceStart (CommandSender sender, boolean isDebug) {
        if (isDebug) {
            Litewars.pluginManager.callEvent(new GameStartEvent(this));
            this.start = true;
            return true;
        } else {
            if (!start) {
                if (players.size() >= minPlayers) {
                    Litewars.pluginManager.callEvent(new GameStartEvent(this));
                    this.start = true;
                    return true;
                } else {
                    sender.sendMessage(Utils.reColor("&c你现在不能开始游戏！"));
                    sender.sendMessage(Utils.reColor("&c如果你想以debug模式强制开始游戏"));
                    sender.sendMessage(Utils.reColor("&c请输入/lw start debug！"));
                    return false;
                }
            }else {
                sender.sendMessage(Utils.reColor("&c你不能开始一个已开始的游戏！"));
                return false;
            }
        }
    }

    @Override
    public void forceEnd () {
        Litewars.pluginManager.callEvent(new AsyncGameEndEvent(this));
        this.start = false;
    }

    @Override
    public int getMinPlayers() {
        return maxPlayers;
    }

    @Override
    public int getMaxPlayers() {
        return minPlayers;
    }
}
