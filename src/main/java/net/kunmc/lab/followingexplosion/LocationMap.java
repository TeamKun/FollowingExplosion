package net.kunmc.lab.followingexplosion;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class LocationMap {
    static HashMap<UUID, ArrayList<Location>> locationMap = new HashMap<>();

    public static Location get(UUID key, long period) {
        if (locationMap.containsKey(key)) {
            ArrayList<Location> array = locationMap.get(key);
            if (array.size() >= period) {
                return array.get((int) period - 1);
            }
        }
        return null;
    }

    public static void put(UUID key, long period, Location location) {
        if (locationMap.containsKey(key)) {
            ArrayList<Location> array = locationMap.get(key);
            array.add(0, location);
            if (array.size() > (int) period) array.remove(array.size() - 1);
        } else {
            ArrayList<Location> array = new ArrayList<>();
            array.add(0, location);
            locationMap.put(key, array);
        }
    }

    public static void putIfAbsent(UUID key, Location location) {
        ArrayList<Location> array = new ArrayList<>();
        array.add(0, location);
        locationMap.putIfAbsent(key, array);
    }

    public static void remove(UUID key) {
        locationMap.remove(key);
    }

    public static void clear() {
        locationMap.clear();
    }

    public static Set<UUID> keySet() {
        return locationMap.keySet();
    }

    public static boolean containsKey(UUID key) {
        return locationMap.containsKey(key);
    }
}