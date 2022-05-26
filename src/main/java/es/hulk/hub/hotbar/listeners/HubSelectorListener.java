package es.hulk.hub.hotbar.listeners;

import es.hulk.hub.SharkHub;
import es.hulk.hub.hotbar.Hotbar;
import es.hulk.hub.hotbar.HotbarManager;
import es.hulk.hub.menus.hub.HubSelectorMenu;
import es.hulk.hub.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class HubSelectorListener implements Listener {

    public HubSelectorListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    @EventHandler
    public void onHubSelectorInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Hotbar hubSelector = HotbarManager.getItemByAction("OPEN_HUB_SELECTOR");

        if (hubSelector == null) {
            CC.sendConsole("&cCould not find hotbar item with action 'OPEN_HUB_SELECTOR'");
            return;
        }

        ItemStack item = HotbarManager.getHotbarItemStack(hubSelector);

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getItemInHand().isSimilar(item)) {
                new HubSelectorMenu().openMenu(event.getPlayer());
            }
        }
    }
}
