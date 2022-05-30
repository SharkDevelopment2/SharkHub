package es.hulk.hub.cosmetics.types.gadgets.button;

import com.cryptomorin.xseries.XMaterial;
import es.hulk.hub.SharkHub;
import es.hulk.hub.cosmetics.types.gadgets.menu.GadgetsMenu;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.bukkit.ItemBuilder;
import es.hulk.hub.util.menu.Button;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class GadgetsMenuButton extends Button {

    private final ConfigFile config = SharkHub.getInstance().getCosmeticsConfig();

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        new GadgetsMenu().openMenu(player);
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        String path = "COSMETICS_MENU.GADGETS.";
        if (ServerUtil.getServerVersion().equalsIgnoreCase("v1_7_R4")) {
            return new ItemBuilder(Material.valueOf(config.getString(path + "ITEM")))
                    .name(config.getString(path + "NAME"))
                    .lore(config.getStringList(path + "LORE"))
                    .data(config.getInt(path + "DATA"))
                    .build();
        } else {
            return new ItemBuilder(XMaterial.matchXMaterial(Material.valueOf(config.getString(path + "ITEM"))).parseMaterial())
                    .name(config.getString(path + "NAME"))
                    .lore(config.getStringList(path + "LORE"))
                    .data(config.getInt(path + "DATA"))
                    .build();
        }
    }
}