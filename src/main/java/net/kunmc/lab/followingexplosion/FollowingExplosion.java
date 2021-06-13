package net.kunmc.lab.followingexplosion;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class FollowingExplosion extends JavaPlugin {
    public static FollowingExplosion plugin;
    public static CommandHandler commandHandler;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        commandHandler = new CommandHandler();
        commandHandler.start();

        Objects.requireNonNull(getCommand(CommandConst.MAIN_COMMAND)).setExecutor(new CommandHandler());
        Objects.requireNonNull(getCommand(CommandConst.MAIN_COMMAND)).setTabCompleter(new TabComplete());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
