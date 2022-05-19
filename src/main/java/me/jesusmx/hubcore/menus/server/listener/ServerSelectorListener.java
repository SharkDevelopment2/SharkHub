package me.jesusmx.hubcore.menus.server.listener;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.menus.server.menu.ServerSelectorMenu;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ServerSelectorListener implements Listener {

    /*Para abrir el Selector*/
    private final ConfigFile config = SharkHub.getInstance().getHotbarConfig();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack is = event.getItem();
            if (is == null || is.getType() == Material.AIR) return;
            if (!is.hasItemMeta() || !is.getItemMeta().hasDisplayName()) return;
            if (is.getType() == Material.valueOf(config.getString("server-selector.material")) && is.getItemMeta().getDisplayName().equalsIgnoreCase(CC.translate(config.getString("server-selector.name")))) {
                new ServerSelectorMenu().openMenu(event.getPlayer());
            }
        }
    }
}
