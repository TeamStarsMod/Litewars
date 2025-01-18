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
    private Block bed;
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
        Location bedLoc = LocationUtils.getLocation(configuration.getFloatList(getKey("Bed")), world);
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

    private String getKey (String key) {
        return "Team." + name + "." + key;
    }
}
