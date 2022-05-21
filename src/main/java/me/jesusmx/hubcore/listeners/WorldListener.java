package me.jesusmx.hubcore.listeners;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.pvpmode.cache.PvPModeHandler;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WorldListener implements Listener {

    private ConfigFile toggle = SharkHub.getInstance().getTogglesConfig();
    private ConfigFile config = SharkHub.getInstance().getSettingsConfig();

    @EventHandler
    private void onCreatureSpawn(CreatureSpawnEvent event) {
        if (toggle.getBoolean("world.no-mobs-spawn.enabled")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onWeather(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    private void onFood(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    private void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (PvPModeHandler.isOnPvPMode((Player) event.getEntity())) return;
        if (toggle.getBoolean("world.no-damage.enabled")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onEntitySpawn(EntitySpawnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    private void onClick(InventoryClickEvent event) {
        if (PvPModeHandler.isOnPvPMode((Player) event.getWhoClicked())) event.setCancelled(false);
        if (!event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) event.setCancelled(true);
    }

    @EventHandler
    private void onPickup(PlayerPickupItemEvent event) {
        event.setCancelled(true);
    }


    @EventHandler
    private void onDrop(PlayerDropItemEvent event) {
        if (!toggle.getBoolean("world.drop-items.enabled")) event.setCancelled(true);
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (!toggle.getBoolean("world.place-blocks.enabled")) {
            if (!player.hasPermission("hubcore.command.place") || !event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!toggle.getBoolean("world.break-blocks.enabled")) {
            if (!player.hasPermission("hubcore.command.break") || !event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void bucketEmpty(PlayerBucketFillEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    private void entityExplode(EntityExplodeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    private void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (toggle.getBoolean("world.void-teleport.enabled") && event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            event.setCancelled(true);
            player.teleport(event.getEntity().getWorld().getSpawnLocation());
        }
    }

    @EventHandler
    private void disablePhysicsBlocks(BlockPhysicsEvent event) {
        event.setCancelled(true);
    }

}