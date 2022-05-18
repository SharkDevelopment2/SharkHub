package me.jesusmx.hubcore.manager.listeners.items;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.pvpmode.cache.PvPModeHandler;
import me.jesusmx.hubcore.util.bukkit.ItemBuilder;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.spigotmc.event.entity.EntityDismountEvent;

public class EnderButtListener implements Listener {

    private ConfigFile toggle = SharkHub.getInstance().getTogglesConfig();
    private ConfigFile config = SharkHub.getInstance().getMainConfig();

    @EventHandler
    public void RegisterListeners(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (toggle.getBoolean("normal.butt.enabled")) {
            ItemStack enderbutt = new ItemBuilder(Material.valueOf(config.getString("butt.material")))
                    .name(config.getString("butt.name"))
                    .setAmount(config.getInt("butt.amount"))
                    .lore(config.getStringList("butt.lore"))
                    .build();
            player.getInventory().setItem(config.getInt("butt.slot"), enderbutt);
        }
    }

    @EventHandler
    public void onPearl(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(PvPModeHandler.isOnPvPMode(player)) return;
        Action action = event.getAction();
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null && event.getItem().getType() == Material.ENDER_PEARL) {
                event.setCancelled(true);
                if (player.isInsideVehicle()) {
                    player.getVehicle().remove();
                }
                event.setUseItemInHand(org.bukkit.event.Event.Result.DENY);
                event.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
                if(toggle.getBoolean("normal.butt.ride")) {
                    EnderPearl pearl = player.launchProjectile(EnderPearl.class);
                    pearl.setPassenger(player);
                    pearl.setVelocity(player.getLocation().getDirection().normalize().multiply(3F));
                } else {
                    player.setVelocity(player.getLocation().getDirection().multiply(2.5f));
                }
                if (config.getBoolean("butt.sound.enabled")) {
                    player.playSound(player.getLocation(), Sound.valueOf(config.getString("butt.sound.effect")), 2.0f, 1.0f);
                }
                player.spigot().setCollidesWithEntities(false);
                player.updateInventory();
            }
        }
    }

    @EventHandler
    public void onDismount(EntityDismountEvent event) {
        event.getDismounted().remove();
    }

}
