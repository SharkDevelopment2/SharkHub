package me.jesusmx.hubcore.hotbar.listeners;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.hotbar.Hotbar;
import me.jesusmx.hubcore.hotbar.HotbarManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OtherHotbarActionImplementation implements Listener {

    public OtherHotbarActionImplementation() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    @EventHandler
    public void otherHotbarImplementation(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            for (Hotbar hotbar : HotbarManager.getItems()) {
                for (String action : hotbar.getActions()) {
                    if (action.contains("[PLAYER]")) {
                        player.performCommand(action.replace("[PLAYER]", ""));
                    } else if (action.contains("[CONSOLE]")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), action.replace("[CONSOLE]", ""));
                    } else if (action.contains("[MESSAGE]")) {
                        player.sendMessage(action.replace("[MESSAGE]", ""));
                    }
                }
            }
        }
    }
}
