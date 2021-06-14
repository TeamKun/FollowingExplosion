package net.kunmc.lab.followingexplosion;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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

    public static void put(UUID key, long interval, Location location) {
        if (locationMap.containsKey(key)) {
            ArrayList<Location> array = locationMap.get(key);
            array.add(0, location);
            while (array.size() > (int) interval) array.remove(array.size() - 1);
        } else {
            ArrayList<Location> array = new ArrayList<>();
            array.add(0, location);
            locationMap.put(key, array);
        }
        Player player = Bukkit.getPlayer(key);
        if (player != null) {
            player.setGlowing(true);
        }
    }

    public static void putIfAbsent(UUID key, Location location) {
        ArrayList<Location> array = new ArrayList<>();
        array.add(0, location);
        locationMap.putIfAbsent(key, array);
        Player player = Bukkit.getPlayer(key);
        if (player != null) {
            player.setGlowing(true);
        }
    }

    public static void remove(UUID key) {
        locationMap.remove(key);
        Player player = Bukkit.getPlayer(key);
        if (player != null) {
            player.setGlowing(false);
        }
    }

    public static void clear() {
        for (UUID key : locationMap.keySet()) {
            Player player = Bukkit.getPlayer(key);
            if (player != null) {
                player.setGlowing(false);
            }
        }
        locationMap.clear();
    }

    public static Set<UUID> keySet() {
        return locationMap.keySet();
    }

    public static boolean containsKey(UUID key) {
        return locationMap.containsKey(key);
    }
}