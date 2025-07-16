package xyz.litewars.litewars.utils;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class WorldUtils {
    public static World loadWorld (String worldName) {
        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.type(WorldType.FLAT);
        worldCreator.generateStructures(false);
        World world = worldCreator.createWorld();

        if (world != null) {
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setGameRuleValue("doWeatherCycle", "false");
            world.setTime(1000);
            return world;
        } else {
            return null;
        }
    }
}
