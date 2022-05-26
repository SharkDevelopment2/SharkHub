package es.hulk.hub.hotbar.listeners;

import es.hulk.hub.hotbar.Hotbar;
import es.hulk.hub.hotbar.HotbarManager;
import es.hulk.hub.SharkHub;
import es.hulk.hub.cosmetics.base.menu.CosmeticsMenu;
import es.hulk.hub.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CosmeticHotbarListener implements Listener {

    public CosmeticHotbarListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

    @EventHandler
    public void onHubSelectorInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Hotbar cosmetic = HotbarManager.getItemByAction("OPEN_COSMETICS_MENU");

        if (cosmetic == null) {
            CC.sendConsole("&cCould not find hotbar item with action 'OPEN_COSMETICS_MENU'");
            return;
        }

        ItemStack item = HotbarManager.getHotbarItemStack(cosmetic);

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getItemInHand().isSimilar(item)) {
                new CosmeticsMenu().openMenu(player);
            }
        }
    }
}
