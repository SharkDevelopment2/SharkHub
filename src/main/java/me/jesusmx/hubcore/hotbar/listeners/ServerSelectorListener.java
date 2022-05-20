package me.jesusmx.hubcore.hotbar.listeners;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.hotbar.Hotbar;
import me.jesusmx.hubcore.menus.server.menu.ServerSelectorMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ServerSelectorListener implements Listener {

    public ServerSelectorListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    @EventHandler
    public void onServerSelectorInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            for (Hotbar hotbar : SharkHub.getInstance().getHotbarManager().getHotbarItems()) {
                if (hotbar.getActions().contains("OPEN_SERVER_SELECTOR")) {
                    if (player.getItemInHand().getType().equals(hotbar.getMaterial())) {
                        new ServerSelectorMenu().openMenu(event.getPlayer());
                    }
                }
            }
        }
    }
}
