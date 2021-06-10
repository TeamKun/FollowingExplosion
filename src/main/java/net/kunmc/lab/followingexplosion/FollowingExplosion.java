package net.kunmc.lab.followingexplosion;

import org.bukkit.plugin.java.JavaPlugin;

public final class FollowingExplosion extends JavaPlugin {
    public static FollowingExplosion plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        getCommand(CommandConst.MAIN_COMMAND).setExecutor(new CommandHandler());
        getCommand(CommandConst.MAIN_COMMAND).setTabCompleter(new TabComplete());

        new CommandHandler().start();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
