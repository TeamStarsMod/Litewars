package xyz.litewars.litewars.utils;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class LocationUtils {
    /**
     * 将Bukkit的Location对象转换为包含位置坐标、偏航角和俯仰角的浮点数列表。
     *
     * @param location 要转换的Bukkit Location对象。
     * @return 表示位置x、y、z、偏航角和俯仰角的浮点数列表。
     */
    public static List<Float> getLocationList (Location location) {
        List<Float> list = new ArrayList<>();
        list.add(0, (float) location.getX());
        list.add(1, (float) location.getY());
        list.add(2, (float) location.getZ());
        list.add(3, location.getYaw());
        list.add(4, location.getPitch());
        return list;
    }

    public static Location getLocation (List<Float> list, World world) {
        return list == null ? null : (list.isEmpty() ? null : (list.size() < 5 ? null : (world == null ? null : new Location(world,
                list.get(0),
                list.get(1),
                list.get(2),
                list.get(3),
                list.get(4)))));
    }
}
