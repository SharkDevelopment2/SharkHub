package me.jesusmx.hubcore.listeners.items;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.hotbar.Hotbar;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.bukkit.ItemBuilder;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Bukkit;
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

    public JoinPlayerListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    private final ConfigFile toggle = SharkHub.getInstance().getTogglesConfig();
    private final ConfigFile config = SharkHub.getInstance().getMainConfig();
    private final ConfigFile hotbar = SharkHub.getInstance().getHotbarConfig();
    private final ConfigFile settings = SharkHub.getInstance().getSettingsConfig();

    @EventHandler
    public void registerSelector(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();

        for (Hotbar hotbar : SharkHub.getInstance().getHotbarManager().getHotbarItems()) {
            ItemStack item = new ItemBuilder(hotbar.getMaterial())
                    .name(hotbar.getDisplayName())
                    .data(hotbar.getData())
                    .lore(hotbar.getLore())
                    .setAmount(hotbar.getAmount())
                    .build();

            if (hotbar.getActions().contains("VISIBILITY_TOGGLE_OFF")) continue;
            player.getInventory().setItem(hotbar.getSlot() - 1, item);
        }

    }

    @EventHandler
    public void registerListeners(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);

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
                config.getStringList("exit-player.message").stream().map(line -> PlaceholderAPI.setPlaceholders(player, line)).map(line -> line.replace("%rank%", SharkHub.getInstance().getPermissionCore().getRank(player))).map(line -> line.replace("%player%", player.getName())).map(line -> line.replace("%prefix%", SharkHub.chat.getPlayerPrefix(player))).forEach(m -> player.sendMessage(CC.translate(m)));
            }
        }
        event.setQuitMessage(null);
    }
}