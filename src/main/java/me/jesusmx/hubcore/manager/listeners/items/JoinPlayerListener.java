package me.jesusmx.hubcore.manager.listeners.items;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.bukkit.ItemBuilder;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinPlayerListener implements Listener {

    private ConfigFile toggle = SharkHub.getInstance().getTogglesConfig();
    private ConfigFile config = SharkHub.getInstance().getMainConfig();
    private ConfigFile hotbar = SharkHub.getInstance().getHotbarConfig();
    private ConfigFile settings = SharkHub.getInstance().getSettingsConfig();

    @EventHandler
    public void RegisterSelector(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (toggle.getBoolean("normal.server-selector.enabled")) {
                    ItemStack selector = new ItemBuilder(Material.valueOf(hotbar.getString("server-selector.material")))
                            .name(hotbar.getString("server-selector.name"))
                            .lore(hotbar.getStringList("server-selector.lore"))
                            .data(hotbar.getInt("server-selector.data"))
                            .build();
                    player.getInventory().setItem(hotbar.getInt("server-selector.slot"), selector);
                }
                if (toggle.getBoolean("normal.hub-selector.enabled")) {
                    ItemStack selector = new ItemBuilder(Material.valueOf(hotbar.getString("hub-selector.material")))
                            .name(hotbar.getString("hub-selector.name"))
                            .lore(hotbar.getStringList("hub-selector.lore"))
                            .data(hotbar.getInt("hub-selector.data"))
                            .build();
                    player.getInventory().setItem(hotbar.getInt("hub-selector.slot"), selector);
                }
                if (toggle.getBoolean("normal.cosmetics.enabled")) {
                    ItemStack cosmetics = new ItemBuilder(Material.valueOf(hotbar.getString("cosmetics.material")))
                            .name(hotbar.getString("cosmetics.name"))
                            .lore(hotbar.getStringList("cosmetics.lore"))
                            .data(hotbar.getInt("cosmetics.data"))
                            .build();
                    player.getInventory().setItem(hotbar.getInt("cosmetics.slot"), cosmetics);
                }

                if (toggle.getBoolean("normal.pvp-mode.enabled")) {
                    ItemStack pvpmode = new ItemBuilder(Material.valueOf(hotbar.getString("pvp-mode.material")))
                            .name(hotbar.getString("pvp-mode.name"))
                            .lore(hotbar.getStringList("pvp-mode.lore"))
                            .data(hotbar.getInt("pvp-mode.data"))
                            .build();
                    player.getInventory().setItem(hotbar.getInt("pvp-mode.slot"), pvpmode);
                }
            }
        } .runTaskLater(SharkHub.getInstance(), 0);
    }

    @EventHandler
    public void RegisterListeners(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        player.getInventory().clear();

        Location location = player.getLocation();
        float yaw = settings.getInt("world.spawn.yaw");
        float pitch = settings.getInt("world.spawn.pitch");
        double x = settings.getDouble("world.spawn.x");
        double y = settings.getDouble("world.spawn.y");
        double z = settings.getDouble("world.spawn.z");
        location.setYaw(yaw);
        location.setPitch(pitch);
        location.setY(y);
        location.setX(x);
        location.setZ(z);
        player.teleport(location);

        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.setAllowFlight(true);
        if (toggle.getBoolean("normal.join-message")) {
            config.getStringList("join-message.lines").stream()
                    .map(line -> PlaceholderAPI.setPlaceholders(player, line))
                    .map(line -> line.replace("%rank%", SharkHub.getInstance().getPermissionCore().getRank(player)))
                    .map(line -> line.replace("%rankcolor%", SharkHub.getInstance().getPermissionCore().getRankColor(player)))
                    .map(line -> line.replace("%player%", player.getName()))
                    .forEach(m -> player.sendMessage(CC.translate(m)));
        }
        if (toggle.getBoolean("normal.join-sound")) {
            player.playSound(player.getLocation(), Sound.valueOf(config.getString("join-sound.sound").toUpperCase()), 1.0F, 1.0F);
        }

        if (toggle.getBoolean("normal.player.join")) {
            config.getStringList("join-player.message").stream()
                    .map(line -> PlaceholderAPI.setPlaceholders(player, line))
                    .map(line -> line.replace("%rank%", SharkHub.getInstance().getPermissionCore().getRank(player)))
                    .map(line -> line.replace("%rankcolor%", SharkHub.getInstance().getPermissionCore().getRankColor(player)))
                    .map(line -> line.replace("%player%", player.getName()))
                    .forEach(m -> player.sendMessage(CC.translate(m)));
        }

        player.updateInventory();
    }

    @EventHandler
    public void JoinSpeed(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        if (toggle.getBoolean("normal.join-speed")) {
            player.setWalkSpeed((float) config.getDouble("join-speed.velocity"));
        } else {
            player.setWalkSpeed(0.2F);
        }
    }

    @EventHandler
    public void ExitMessage(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (toggle.getBoolean("normal.player.leave")) {
            if (player.hasPermission("hubcore.donator")) {
                config.getStringList("exit-player.message").stream()
                        .map(line -> PlaceholderAPI.setPlaceholders(player, line))
                        .map(line -> line.replace("%rank%", SharkHub.getInstance().getPermissionCore().getRank(player)))
                        .map(line -> line.replace("%player%", player.getName()))
                        .map(line -> line.replace("%prefix%", SharkHub.chat.getPlayerPrefix(player)))
                        .forEach(m -> player.sendMessage(CC.translate(m)));
            }
        }
        event.setQuitMessage(null);
    }
}