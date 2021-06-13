package net.kunmc.lab.followingexplosion;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

import static org.bukkit.Bukkit.getLogger;

class Tasks extends BukkitRunnable {
    private final Random random = new Random();
    ArrayList<String> playerList;

    private Long explosionTick = 0L;
    private Long randomTick = 0L;

    @Override
    public void run() {
        explosionTick++;
        if (Config.gameMode.equals("random"))
            randomTick++;

        // ランダム設定
        if (randomTick >= Config.randomInterval) {
            playerList = new ArrayList<>();
            LocationMap.clear();
            ArrayList<Player> allPlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
            int limit = Math.min(Config.randomPersons, allPlayers.size());
            int n = 0;
            while (n < limit) {
                Player p = allPlayers.get(random.nextInt(allPlayers.size()));
                LocationMap.put(p.getUniqueId(), Config.locationInterval, p.getLocation());
                playerList.add(p.getName());
                allPlayers.remove(p);
                n++;
            }
            if (playerList.size() > 0)
                getLogger().info("ランダム設定を実行しました。　プレイヤー：" + playerList.stream().sorted(Comparator.naturalOrder()).collect(Collectors.joining(",")));
            randomTick = 0L;
        }

        //対象プレイヤーの位置取得
        for (UUID key : LocationMap.keySet()) {
            Player player = Bukkit.getPlayer(key);
            if (player != null) {
                if (player.isOnline()) {
                    LocationMap.put(key, Config.locationInterval, player.getLocation());
                }
            }
        }

        //爆発処理
        if(explosionTick >= Config.explosionInterval) {
            playerList = new ArrayList<>();
            for (UUID key : LocationMap.keySet()) {
                Player player = Bukkit.getPlayer(key);
                Location location = LocationMap.get(key, Config.locationInterval);
                if (player != null) {
                    if (location != null && player.isOnline()) {
                        location.createExplosion(Config.explosionPower);
                        playerList.add(player.getName());
                    }
                }
            }
            if (playerList.size() > 0)
                getLogger().info("爆発しました。　プレイヤー：" + playerList.stream().sorted(Comparator.naturalOrder()).collect(Collectors.joining(",")));
            explosionTick = 0L;
        }
    }

    public void resetExplosionTick(){
        explosionTick = 0L;
    }

    public void resetRandomTick(){
        randomTick = 0L;
    }

    public void setRandomTick(Long n) { randomTick = n; }
}
