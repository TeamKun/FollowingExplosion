package net.kunmc.lab.followingexplosion;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!LocationMap.containsKey(e.getPlayer().getUniqueId()))
            e.getPlayer().setGlowing(false);
    }
}
