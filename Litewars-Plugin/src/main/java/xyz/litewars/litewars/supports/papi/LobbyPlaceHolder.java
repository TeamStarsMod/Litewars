package xyz.litewars.litewars.supports.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import xyz.litewars.litewars.Litewars;

import java.sql.SQLException;

import static xyz.litewars.litewars.RunningData.databaseManager;

public class LobbyPlaceHolder extends PlaceholderExpansion {
    @Override
    public @NotNull String getAuthor() {
        return "CYson-Hab cyh2, Neko110923";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "litewars-lobby";
    }

    @Override
    public @NotNull String getVersion() {
        return Litewars.plugin.getName() + " / " + Litewars.plugin.getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        switch (params) {
            case "level" -> {
                return "0";
            }
            case "party" -> {
                return "当前没有";
            }
            case "kills" -> {
                try {
                    int kills = databaseManager.getInt("player_datas", "kills", "player_uuid", player.getUniqueId().toString());
                    return Integer.toString(kills);
                }catch (SQLException e){
                    Litewars.logger.severe("在操作数据库时发生错误！" + e.getMessage());
                }
            }
        }
        return null;
    }
}
