package me.jesusmx.hubcore.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.hotbar.HotbarManager;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

    public JoinListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    private final ConfigFile toggle = SharkHub.getInstance().getTogglesConfig();
    private final ConfigFile config = SharkHub.getInstance().getMainConfig();

    @EventHandler
    public void registerListeners(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();
        HotbarManager.setHotbarItems(player);
        event.setJoinMessage(null);
        SharkHub.getInstance().getSpawnManager().sendToSpawn(player);

        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.setAllowFlight(true);
        if (toggle.getBoolean("normal.join-message")) {
            config.getStringList("join-message.lines").stream()
                    .map(line -> PlaceholderAPI.setPlaceholders(player, line))
                    .map(line -> line.replace("%rank%", SharkHub.getInstance().getRankManager().getRank().getRank(player)))
                    .map(line -> line.replace("%rankcolor%", SharkHub.getInstance().getRankManager().getRank().getRankColor(player)))
                    .map(line -> line.replace("%player%", player.getName()))
                    .forEach(m -> player.sendMessage(CC.translate(m)));
        }
        if (toggle.getBoolean("normal.join-sound")) {
            player.playSound(player.getLocation(), Sound.valueOf(config.getString("join-sound.sound").toUpperCase()), 1.0F, 1.0F);
        }

        if (toggle.getBoolean("normal.player.join")) {
            config.getStringList("join-player.message").stream()
                    .map(line -> PlaceholderAPI.setPlaceholders(player, line))
                    .map(line -> line.replace("%rank%", SharkHub.getInstance().getRankManager().getRank().getRank(player)))
                    .map(line -> line.replace("%rankcolor%", SharkHub.getInstance().getRankManager().getRank().getRankColor(player)))
                    .map(line -> line.replace("%player%", player.getName()))
                    .forEach(m -> player.sendMessage(CC.translate(m)));
        }

        player.updateInventory();
    }

    @EventHandler
    public void joinSpeed(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        if (toggle.getBoolean("normal.join-speed")) {
            player.setWalkSpeed((float) config.getDouble("join-speed.velocity"));
        } else {
            player.setWalkSpeed(0.2F);
        }
    }

    @EventHandler
    public void exitMessage(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (toggle.getBoolean("normal.player.leave")) {
            if (player.hasPermission("hubcore.donator")) {
                config.getStringList("exit-player.message").stream().map(line -> PlaceholderAPI.setPlaceholders(player, line)).map(line -> line.replace("%rank%", SharkHub.getInstance().getRankManager().getRank().getRank(player))).map(line -> line.replace("%player%", player.getName())).map(line -> line.replace("%prefix%", SharkHub.getInstance().getRankManager().getChat().getPlayerPrefix(player))).forEach(m -> player.sendMessage(CC.translate(m)));
            }
        }
        event.setQuitMessage(null);
    }
}