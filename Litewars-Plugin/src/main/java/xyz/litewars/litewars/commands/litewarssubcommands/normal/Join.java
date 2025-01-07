package xyz.litewars.litewars.commands.litewarssubcommands.normal;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
            //查找有没有同名的竞技场
            for (Game game : RunningData.gameManager.getRunningGames()) {
                Arena arena = game.getArena();
                if (arena.getName().equalsIgnoreCase(arenaName)) {
                    isFound = true;
                    if (!game.isStart()) {
                        player.sendMessage(Messages.readMessage(Messages.FOUND_ARENA, "&a"));
                        game.addPlayer(player);
                    }
                    break;
                }
            }
            //如果没有，查找是否有相同竞技场组
            if (!isFound) {
                for (ArenaGroup arenaGroup : RunningData.arenaGroups) {
                    if (arenaGroup.getName().equalsIgnoreCase(arenaName)) {
                        isFound = true;
                        player.sendMessage(Messages.readMessage(Messages.FOUND_ARENA_GROUP, "&a"));
                        Arena mostPlayersArena = getArena(arenaGroup);
                        if (mostPlayersArena != null) {
                            mostPlayersArena.getGame().addPlayer(player);
                        }else {
                            logger.warning("没有获取到任何竞技场！这可能是个错误！");
                            player.sendMessage(ChatColor.RED + "Something was wrong, please see Console.");
                        }
                        break;
                    }
                }
            }

            if (!isFound) {
                player.sendMessage(Messages.readMessage(Messages.CANT_FOUND_GROUP_OR_ARENA, "&c"));
            }
        } else {
            player.sendMessage(Messages.readMessage(Messages.NEED_MORE_ARGS, "&c"));
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
        for (Arena arena : arenaGroup.getArenas()){
            int currArenaPlayer = arena.getGame().getPlayers().size();
            if (currArenaPlayer >= arenaPlayers){
                arenaPlayers = currArenaPlayer;
                mostPlayersArena = arena;
            }
        }
        return mostPlayersArena;
    }
}
