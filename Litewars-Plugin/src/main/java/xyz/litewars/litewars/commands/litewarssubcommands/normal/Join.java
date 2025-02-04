package xyz.litewars.litewars.commands.litewarssubcommands.normal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.litewars.litewars.ExceptionUtils;
import xyz.litewars.litewars.RunningData;
import xyz.litewars.litewars.api.arena.Arena;
import xyz.litewars.litewars.api.arena.ArenaGroup;
import xyz.litewars.litewars.api.command.SubCommand;
import xyz.litewars.litewars.api.game.Game;
import xyz.litewars.litewars.api.languages.Messages;
import xyz.litewars.litewars.commands.LitewarsCommand;
import xyz.litewars.litewars.utils.Utils;

import static xyz.litewars.litewars.Litewars.logger;

public class Join extends SubCommand {

    public Join(LitewarsCommand parent) {
        super (parent, "join", "", null, true, false);
    }

    @Override
    public boolean execute (CommandSender sender, Command command, String s, String[] strings) {
        Player player = (Player) sender;
        if (strings.length > 0) {
            String arenaName = strings[0];
            boolean isFound = false;
            //查找有没有同名的竞技场，如果找到了，但此游戏没有在等待，则新建此游戏
            for (Game game : RunningData.gameManager.getRunningGames()) {
                Arena arena = game.getArena();
                if (arena.getName().equalsIgnoreCase(arenaName)) {
                    isFound = true;
                    if (!game.isStart()) {
                        player.sendMessage(Messages.readMessage(player, Messages.FOUND_ARENA, "&a"));
                        game.addPlayer(player);
                    } else {
                        player.sendMessage(Utils.reColor("&c您要加入的竞技场已开始游戏！"));
                        new Thread(() -> {
                            try {
                                Thread.sleep(4000);
                                player.sendMessage(Utils.reColor("&eNekoEpisode joined the game."));
                                Thread.sleep(1300);
                                player.sendMessage(Utils.reColor("&eCysonHab joined the game."));
                                Thread.sleep(2000);
                                player.sendMessage(Utils.reColor("&f<NekoEpisode> 什么? 你问我为什么没有观战模式?"));
                                Thread.sleep(2400);
                                player.sendMessage(Utils.reColor("&f<NekoEpisode> 额...我还没做("));
                                Thread.sleep(1200);
                                player.sendMessage(Utils.reColor("&f<CYsonHab> 我们就是懒(bushi"));
                                Thread.sleep(500);
                                player.sendMessage(Utils.reColor("&f<CYsonHab> 所以你还想玩吗(?"));
                            } catch (InterruptedException e) {
                                ExceptionUtils.printException(e);
                            }
                        }).start();
                        // arena.addSpectator(player); //(待定)观战模式
                    }
                    break;
                }
            }
            //如果没有，查找是否有相同竞技场组，如果找到了，但没有正在等待的游戏，则新建游戏实例
            if (!isFound) {
                ArenaGroup arenaGroup = RunningData.arenaGroupMap.get(arenaName.toLowerCase());
                if (arenaGroup != null) {
                    isFound = true;
                    Arena mostPlayersArena = getArena(arenaGroup);
                    if (mostPlayersArena == null) {
                        Arena arena = null;
                        for (Arena arena1 : arenaGroup.getArenas()) {
                            if (arena1.getGame() == null) {
                                arena = arena1;
                                break;
                            }
                        }
                        if (arena == null) {
                            player.sendMessage(Utils.reColor("&c此竞技场组没有空闲地图 :("));
                            return false;
                        }
                        RunningData.gameManager.newGameInstance(arena, player);
                        player.sendMessage(Messages.readMessage(player, Messages.FOUND_ARENA_GROUP, "&a"));
                    } else {
                        mostPlayersArena.getGame().addPlayer(player);
                        player.sendMessage(Messages.readMessage(player, Messages.FOUND_ARENA_GROUP, "&a"));
                    }
                }
            }

            if (!isFound) {
                for (ArenaGroup arenaG : RunningData.arenaGroupMap.values()) {
                    for (Arena arena : arenaG.getArenas()) {
                        if (arena.getName().equals(arenaName)) {
                            isFound = true;
                            Game game = arena.getGame();
                            if (game == null) {
                                RunningData.gameManager.newGameInstance(arena, player);
                            } else {
                                if (!game.isStart()) {
                                    game.addPlayer(player);
                                } else {
                                    player.sendMessage(Utils.reColor("&c此竞技场不处于空闲状态 :("));
                                }
                            }
                            break;
                        }
                    }
                }
            }

            if (!isFound) {
                player.sendMessage(Messages.readMessage(player, Messages.CANT_FOUND_GROUP_OR_ARENA, "&c"));
            }
        } else {
            player.sendMessage(Messages.readMessage(player, Messages.NEED_MORE_ARGS, "&c"));
            return false;
        }
        return false;
    }

    @Override
    public String getDescription () {
        return Utils.reColor(String.format("join <%s/%s> : %s", Messages.readLanguageFile(Messages.ARENA_NAME), Messages.readLanguageFile(Messages.ARENA_GROUP), Messages.readLanguageFile(Messages.LW_JOIN_MESSAGE)));
    }

    private static Arena getArena(ArenaGroup arenaGroup) {
        Arena mostPlayersArena = null;
        int arenaPlayers = 0;
        for (Arena arena : arenaGroup.getArenas()) {
            Game game = arena.getGame();
            if (game != null && !game.isStart()) {
                int currArenaPlayer = game.getPlayers().size();
                if (currArenaPlayer >= arenaPlayers) {
                    arenaPlayers = currArenaPlayer;
                    mostPlayersArena = arena;
                }
            }
        }
        return mostPlayersArena;
    }
}
