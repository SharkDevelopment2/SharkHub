package es.hulk.hub.menus.subselector.button;

import es.hulk.hub.SharkHub;
import es.hulk.hub.menus.server.menu.ServerSelectorMenu;
import es.hulk.hub.util.ItemMaker;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class BackButton extends Button {

    private final ConfigFile config = SharkHub.getInstance().getSubselectorConfig();

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        new ServerSelectorMenu().openMenu(player);
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemMaker(Material.valueOf(config.getString("SUB_SELECTOR.BACK_BUTTON.ITEM")), 1, (short) config.getInt("SUB_SELECTOR.BACK_BUTTON.DATA"))
                .displayName(config.getString("SUB_SELECTOR.BACK_BUTTON.NAME"))
                .lore(config.getStringList("SUB_SELECTOR.BACK_BUTTON.LORE"))
                .build();
    }
}
