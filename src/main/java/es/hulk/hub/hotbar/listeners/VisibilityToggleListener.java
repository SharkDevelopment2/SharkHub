package es.hulk.hub.hotbar.listeners;

import es.hulk.hub.SharkHub;
import es.hulk.hub.hotbar.Hotbar;
import es.hulk.hub.hotbar.HotbarManager;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class VisibilityToggleListener implements Listener {

    private final ConfigFile messages = SharkHub.getInstance().getMessagesConfig();

    public VisibilityToggleListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    @EventHandler
    public void onVisibilityToggleInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Hotbar show = HotbarManager.getItemByAction("VISIBILITY_TOGGLE_ON");
        Hotbar hide = HotbarManager.getItemByAction("VISIBILITY_TOGGLE_OFF");

        if (show == null || hide == null) return;

        ItemStack showItem = HotbarManager.getHotbarItemStack(show);
        ItemStack hideItem = HotbarManager.getHotbarItemStack(hide);

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getItemInHand().isSimilar(showItem)) {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    player.hidePlayer(online);
                }

                player.sendMessage(CC.translate(messages.getString("PLAYER_VISIBILITY.SHOW")));
                player.setItemInHand(hideItem);
            } else if (player.getItemInHand().isSimilar(hideItem)) {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    player.showPlayer(online);
                }

                player.sendMessage(CC.translate(messages.getString("PLAYER_VISIBILITY.HIDE")));
                player.setItemInHand(showItem);
            }
        }
    }
}
