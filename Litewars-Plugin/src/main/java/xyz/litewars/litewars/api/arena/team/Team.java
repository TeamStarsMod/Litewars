package xyz.litewars.litewars.api.arena.team;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.litewars.litewars.utils.LocationUtils;

public class Team {
    private final Colors colors;
    private final String name;
    private final YamlConfiguration configuration;
    private final World world;
    private Location spawn;
    private Location iron;
    private Location gold;
    private Location emerald;
    private Location shop;
    private Location upgrade;
    private Block bed;
    private int maxPlayer;
    private boolean isEditing;

    public Team (Colors colors, boolean isEditing, String name, YamlConfiguration configuration, World world) {
        this.colors = colors;
        this.name = name;
        this.configuration = configuration;
        this.isEditing = isEditing;
        this.world = world;
        this.spawn = LocationUtils.getLocation(configuration.getFloatList(getKey("Spawn")), world);
        this.iron = LocationUtils.getLocation(configuration.getFloatList(getKey("Iron")), world);
        this.gold = LocationUtils.getLocation(configuration.getFloatList(getKey("Gold")), world);
        this.emerald = LocationUtils.getLocation(configuration.getFloatList(getKey("Emerald")), world);
        this.shop = LocationUtils.getLocation(configuration.getFloatList("Shop"), world);
        this.upgrade = LocationUtils.getLocation(configuration.getFloatList("Upgrade"), world);
        Location bedLoc = LocationUtils.getLocation(configuration.getFloatList(getKey("Bed")), world);
        this.maxPlayer = configuration.getInt(getKey("MaxPlayer"));
        this.bed = bedLoc == null ? null : bedLoc.getBlock();
    }

    public Colors getColors() {
        return colors;
    }

    public boolean isEditing() {
        return isEditing;
    }

    public Team setEditing(boolean editing) {
        isEditing = editing;
        return this;
    }

    public String getName() {
        return name;
    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }

    public Team setSpawn (Location location) {
        this.spawn = location;
        configuration.set(getKey("Spawn"), LocationUtils.getLocationList(location));
        return this;
    }

    public Location getSpawn() {
        return spawn;
    }

    public World getWorld() {
        return world;
    }

    public Location getIron() {
        return iron;
    }

    public void setIron(Location iron) {
        this.iron = iron;
        configuration.set(getKey("Iron"), LocationUtils.getLocationList(iron));
    }

    public Location getGold() {
        return gold;
    }

    public void setGold(Location gold) {
        this.gold = gold;
        configuration.set(getKey("Gold"), LocationUtils.getLocationList(gold));
    }

    public Block getBed () {
        return this.bed;
    }

    public void setBed (Block bed) {
        this.bed = bed;
        configuration.set(getKey("Bed"), LocationUtils.getLocationList(bed.getLocation()));
    }

    public Location getEmerald () {
        return this.emerald;
    }

    public void setEmerald (Location emerald) {
        this.emerald = emerald;
        configuration.set(getKey("Emerald"), LocationUtils.getLocationList(emerald));
    }

    public Location getShop () {
        return this.shop;
    }

    public void setShop (Location shop) {
        this.shop = shop;
        configuration.set(getKey("Shop"), LocationUtils.getLocationList(shop));
    }

    public Location getUpgrade () {
        return this.upgrade;
    }

    public void setUpgrade (Location upgrade) {
        this.upgrade = upgrade;
        configuration.set(getKey("Upgrade"), LocationUtils.getLocationList(upgrade));
    }

    public void setMaxPlayer (int max) {
        this.maxPlayer = max;
        configuration.set(getKey("MaxPlayer"), max);
    }

    public int getMaxPlayer () {
        return this.maxPlayer;
    }

    private String getKey (String key) {
        return "Team." + name + "." + key;
    }
}
