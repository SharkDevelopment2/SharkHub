package es.hulk.hub.hotbar.listeners;

import es.hulk.hub.SharkHub;
import es.hulk.hub.hotbar.Hotbar;
import es.hulk.hub.hotbar.HotbarManager;
import es.hulk.hub.menus.server.menu.ServerSelectorMenu;
import es.hulk.hub.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ServerSelectorListener implements Listener {

    public ServerSelectorListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    @EventHandler
    public void onServerSelectorInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Hotbar serverSelector = HotbarManager.getItemByAction("OPEN_SERVER_SELECTOR");

        if (serverSelector == null) return;
        ItemStack item = HotbarManager.getHotbarItemStack(serverSelector);

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getItemInHand().isSimilar(item)) {
                new ServerSelectorMenu().openMenu(event.getPlayer());
            }
        }
    }
}
