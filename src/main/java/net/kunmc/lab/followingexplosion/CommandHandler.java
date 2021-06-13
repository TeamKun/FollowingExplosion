package net.kunmc.lab.followingexplosion;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import static net.kunmc.lab.followingexplosion.FollowingExplosion.commandHandler;

public class CommandHandler implements CommandExecutor {
    private Tasks task;
    private final Random random = new Random();
    UUID id;

    public void start() {
        Plugin plugin = FollowingExplosion.plugin;
        task = new Tasks();
        task.runTaskTimer(plugin, 0, 1L);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<Player> allPlayers;
        task = commandHandler.task;

        if (args.length < 2) {
            sender.sendMessage("引数が不足しています。");
            return false;
        }

        switch (args[0]) {
            case CommandConst.COMMAND_PLAYER_SET:
                try {
                    switch (args[1]) {
                        case "@a":
                            allPlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
                            for (Player p : allPlayers)
                                LocationMap.putIfAbsent(p.getUniqueId(), p.getLocation());
                            sender.sendMessage("全てのプレイヤーを追加しました。");
                            break;

                        case "@r":
                            allPlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
                            allPlayers.removeIf(p -> LocationMap.containsKey(p.getUniqueId()));
                            if (allPlayers.size() > 0) {
                                Player p = allPlayers.get(random.nextInt(allPlayers.size()));
                                LocationMap.putIfAbsent(p.getUniqueId(), p.getLocation());
                                sender.sendMessage(p.getName() + "を追加しました。");
                            } else {
                                sender.sendMessage("追加するプレイヤーが存在しません。");
                            }
                            break;

                        default:
                            Player player = Bukkit.getPlayer(args[1]);
                            if (player != null) {
                                id = player.getUniqueId();
                                if (!LocationMap.containsKey(id)) {
                                    LocationMap.putIfAbsent(id, player.getLocation());
                                    sender.sendMessage(player.getName() + "を追加しました。");
                                } else {
                                    sender.sendMessage("すでに追加されているプレイヤーです。");
                                }
                            }
                            break;
                    }
                } catch (Exception e) {
                    sender.sendMessage("存在しないプレイヤーを指定しています。");
                }
                break;

            case CommandConst.COMMAND_PLAYER_REMOVE:
                try {
                    if (args[1].equals("@a")) {
                        LocationMap.clear();
                        sender.sendMessage("全てのプレイヤーを削除しました。");
                    } else {
                        Player player = Bukkit.getPlayer(args[1]);
                        if (player != null) {
                            LocationMap.remove(player.getUniqueId());
                            sender.sendMessage(player.getName() + "を削除しました。");
                        }
                    }
                } catch (Exception e) {
                    sender.sendMessage("存在しないプレイヤーを指定しています。");
                }
                break;

            case CommandConst.COMMAND_MODE:
                LocationMap.clear();
                task.resetRandomTick();
                switch (args[1]) {
                    case CommandConst.MODE_RANDOM:
                        Config.gameMode = CommandConst.MODE_RANDOM;
                        task.setRandomTick(Config.randomInterval);
                        sender.sendMessage("ランダムモードに設定しました。");
                        break;

                    case CommandConst.MODE_ASSIGN:
                        Config.gameMode = CommandConst.MODE_ASSIGN;
                        sender.sendMessage("プレイヤー指定モードに設定しました。");
                        break;

                    default:
                        sender.sendMessage("引数が不正です。");
                        return false;
                }
                break;

            case CommandConst.COMMAND_CONFIG:
                if (args.length < 3) {
                    if (args[1].equals(CommandConst.CONFIG_DISPLAY)) {
                        ArrayList<String> playerList = new ArrayList<>();
                        for (UUID key : LocationMap.keySet()) {
                            Player player = Bukkit.getPlayer(key);
                            if (player != null) {
                                playerList.add(player.getName());
                            }
                        }
                        sender.sendMessage("対象プレイヤー：" + playerList.stream().sorted(Comparator.naturalOrder()).collect(Collectors.joining(",")));
                        sender.sendMessage("爆発範囲：" + Config.explosionPower);
                        sender.sendMessage("爆発間隔：" + Config.explosionInterval + "Tick");
                        sender.sendMessage("過去位置の時間：" + Config.locationInterval + "Tick前");
                        sender.sendMessage("ランダムモードのランダム設定人数：" + Config.randomPersons + "人");
                        sender.sendMessage("ランダムモードの設定間隔：" + Config.randomInterval + "Tick");
                        return true;
                    } else {
                        sender.sendMessage("引数が不足しています。");
                        return false;
                    }
                }

                try {
                    if (Integer.parseInt(args[2]) < 1) {
                        sender.sendMessage("設定値は1以上である必要があります。");
                        return false;
                    }
                    switch (args[1]) {
                        case CommandConst.CONFIG_EXPLOSION_POWER:
                            Config.explosionPower = Integer.parseInt(args[2]);
                            sender.sendMessage("爆発範囲：" + Config.explosionPower);
                            break;

                        case CommandConst.CONFIG_EXPLOSION_INTERVAL:
                            Config.explosionInterval = Long.parseLong(args[2]);
                            task.resetExplosionTick();
                            sender.sendMessage("爆発間隔：" + Config.explosionInterval + "Tick");
                            break;

                        case CommandConst.CONFIG_LOCATION_INTERVAL:
                            Config.locationInterval = Long.parseLong(args[2]);
                            sender.sendMessage("過去位置の時間：" + Config.locationInterval + "Tick前");
                            break;

                        case CommandConst.CONFIG_RANDOM_PERSONS:
                            Config.randomPersons = Integer.parseInt(args[2]);
                            sender.sendMessage("ランダムモードのランダム設定人数：" + Config.randomPersons + "人");
                            break;

                        case CommandConst.CONFIG_RANDOM_INTERVAL:
                            Config.randomInterval = Long.parseLong(args[2]);
                            task.resetRandomTick();
                            sender.sendMessage("ランダムモードの設定間隔：" + Config.randomInterval + "Tick");
                            break;

                        default:
                            sender.sendMessage("引数が不正です。");
                            return false;
                    }
                } catch (Exception e) {
                    sender.sendMessage("引数が不正です。");
                    return false;
                }
                break;

            default:
                sender.sendMessage("引数が不正です。");
                return false;
        }
        return true;
    }
}
