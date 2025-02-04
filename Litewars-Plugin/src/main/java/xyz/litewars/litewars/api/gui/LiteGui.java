package xyz.litewars.litewars.api.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import xyz.litewars.litewars.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public abstract class LiteGui {
    private final Inventory inv;
    private final List<Player> inUsePlayers = new ArrayList<>();

    public LiteGui (String title, int size) {
        this.inv = Bukkit.createInventory(null, size % 9 == 0 ? size : 9, Utils.reColor(title));
    }

    public void addPlayer (Player p) {
        if (!inUsePlayers.contains(p)) {
            inUsePlayers.add(p);
            p.openInventory(inv);
        }
    }

    public void removePlayer (Player p) {
        if (inUsePlayers.contains(p)) {
            inUsePlayers.remove(p);
            p.closeInventory();
        }
    }

    public List<Player> getInUsePlayers () {
        return this.inUsePlayers;
    }

    public Inventory getInv() {
        return inv;
    }
}
