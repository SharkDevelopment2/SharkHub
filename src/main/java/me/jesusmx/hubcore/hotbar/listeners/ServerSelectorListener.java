package me.jesusmx.hubcore.hotbar.listeners;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.hotbar.Hotbar;
import me.jesusmx.hubcore.hotbar.HotbarManager;
import me.jesusmx.hubcore.menus.server.ServerSelectorMenu;
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
        assert serverSelector != null;
        ItemStack item = HotbarManager.getHotbarItemStack(serverSelector);

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getItemInHand().isSimilar(item)) {
                new ServerSelectorMenu().openMenu(event.getPlayer());
            }
        }
    }
}
