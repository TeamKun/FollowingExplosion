package net.kunmc.lab.followingexplosion;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

import static org.bukkit.Bukkit.getLogger;

class Tasks extends BukkitRunnable {
    private final String taskMode;
    private final Random random = new Random();
    ArrayList<String> playerList;

    Tasks(String s) {
        taskMode = s;
    }

    @Override
    public void run() {
        switch (taskMode) {
            case "location":
                for (UUID key : LocationMap.keySet()) {
                    if (!Objects.isNull(Bukkit.getPlayer(key))) {
                        Player player = Bukkit.getPlayer(key);
                        if (player.isOnline()) {
                            LocationMap.put(key, Config.locationInterval, player.getLocation());
                        }
                    }
                }
                break;
            case "explosion":
                playerList = new ArrayList<>();
                for (UUID key : LocationMap.keySet()) {
                    if (!Objects.isNull(Bukkit.getPlayer(key))) {
                        if ((!Objects.isNull(LocationMap.get(key, Config.locationInterval))) && (Bukkit.getPlayer(key).isOnline())) {
                            LocationMap.get(key, Config.locationInterval).createExplosion(Config.explosionPower);
                            playerList.add(Bukkit.getPlayer(key).getName());
                        }
                    }
                }
                if (playerList.size() > 0)
                    getLogger().info("爆発しました。　プレイヤー：" + playerList.stream().sorted(Comparator.naturalOrder()).collect(Collectors.joining(",")));
                break;
            case "random":
                playerList = new ArrayList<>();
                LocationMap.clear();
                ArrayList<Player> allPlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
                int times = Math.min(Config.randomPersons, allPlayers.size());
                int n = 0;
                while (n < times) {
                    Player p = allPlayers.get(random.nextInt(allPlayers.size()));
                    LocationMap.put(p.getUniqueId(), Config.locationInterval, p.getLocation());
                    playerList.add(p.getName());
                    n++;
                }
                if (playerList.size() > 0)
                    getLogger().info("ランダム設定を実行しました。　プレイヤー：" + playerList.stream().sorted(Comparator.naturalOrder()).collect(Collectors.joining(",")));
                break;
        }
    }
}
