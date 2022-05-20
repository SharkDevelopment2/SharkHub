package me.jesusmx.hubcore.hotbar.listeners;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.hotbar.Hotbar;
import me.jesusmx.hubcore.hotbar.HotbarManager;
import me.jesusmx.hubcore.pvpmode.cache.PvPModeHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PvPModeListener implements Listener {

    public PvPModeListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    @EventHandler
    public void onHubSelectorInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Hotbar pvpMode = HotbarManager.getItemByAction("JOIN_PVP_MODE");
        assert pvpMode != null;
        ItemStack item = HotbarManager.getHotbarItemStack(pvpMode);

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getItemInHand().isSimilar(item)) {
                PvPModeHandler.togglePvPMode(event.getPlayer());
                event.setCancelled(true);
            }
        }
    }
}
