package net.kunmc.lab.followingexplosion;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class TabComplete implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        switch (args.length) {
            case 1:
                completions.add(CommandConst.COMMAND_PLAYER_SET);
                completions.add(CommandConst.COMMAND_PLAYER_REMOVE);
                completions.add(CommandConst.COMMAND_MODE);
                completions.add(CommandConst.COMMAND_CONFIG);
                break;
            case 2:
                if (args[0].equals(CommandConst.COMMAND_CONFIG)) {
                    completions.add(CommandConst.CONFIG_DISPLAY);
                    completions.add(CommandConst.CONFIG_EXPLOSION_POWER);
                    completions.add(CommandConst.CONFIG_EXPLOSION_INTERVAL);
                    completions.add(CommandConst.CONFIG_LOCATION_INTERVAL);
                    completions.add(CommandConst.CONFIG_RANDOM_PERSONS);
                    completions.add(CommandConst.CONFIG_RANDOM_INTERVAL);
                } else if (args[0].equals(CommandConst.COMMAND_MODE)) {
                    completions.add(CommandConst.MODE_RANDOM);
                    completions.add(CommandConst.MODE_ASSIGN);
                    completions.add(CommandConst.MODE_RESUME);
                    completions.add(CommandConst.MODE_STOP);
                } else {
                    if (args[0].equals(CommandConst.COMMAND_PLAYER_SET)) {
                        completions.addAll(Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList()));
                        completions.removeIf(p -> LocationMap.containsKey(Objects.requireNonNull(Bukkit.getPlayer(p)).getUniqueId()));
                        completions.add(0, "@r");
                    } else {
                        for (UUID key : LocationMap.keySet()) {
                            completions.add(Objects.requireNonNull(Bukkit.getPlayer(key)).getName());
                        }
                    }
                    completions.add(0, "@a");
                }
                break;
            case 3:
                if (args[0].equals(CommandConst.COMMAND_CONFIG) && !args[1].equals(CommandConst.CONFIG_DISPLAY))
                    completions.add("<number>");
                break;
        }
        return completions;
    }
}
