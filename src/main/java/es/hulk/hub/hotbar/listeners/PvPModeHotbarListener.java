package es.hulk.hub.hotbar.listeners;

import es.hulk.hub.SharkHub;
import es.hulk.hub.hotbar.Hotbar;
import es.hulk.hub.hotbar.HotbarManager;
import es.hulk.hub.pvpmode.PvPModeHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PvPModeHotbarListener implements Listener {

    public PvPModeHotbarListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    @EventHandler
    public void onHubSelectorInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Hotbar pvpMode = HotbarManager.getItemByAction("JOIN_PVP_MODE");

        if (pvpMode == null) return;
        ItemStack item = HotbarManager.getHotbarItemStack(pvpMode);

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getItemInHand().isSimilar(item)) {
                PvPModeHandler.togglePvPMode(event.getPlayer());
                event.setCancelled(true);
            }
        }
    }
}
