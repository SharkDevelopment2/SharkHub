package es.hulk.hub.listeners;

import es.hulk.hub.SharkHub;
import es.hulk.hub.pvpmode.PvPModeHandler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
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
import org.bukkit.inventory.Inventory;

public class WorldListener implements Listener {

    public WorldListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    @EventHandler
    private void onCreatureSpawn(CreatureSpawnEvent event) {
        event.setCancelled(true);
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
        event.setCancelled(true);
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
        event.setCancelled(true);
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("hubcore.command.place") || !event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("hubcore.command.break") || !event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
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
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            event.setCancelled(true);
            if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                player.teleport(event.getEntity().getWorld().getSpawnLocation());
            }
        }
    }

    @EventHandler
    private void disablePhysicsBlocks(BlockPhysicsEvent event) {
        event.setCancelled(true);
    }

}