package es.hulk.hub.cosmetics.types.hats.button;

import com.cryptomorin.xseries.XMaterial;
import es.hulk.hub.SharkHub;
import es.hulk.hub.cosmetics.types.hats.menu.HatsMenu;
import es.hulk.hub.util.bukkit.ItemBuilder;
import es.hulk.hub.util.buttons.Button;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class HatMenuButton extends Button {

    private final ConfigFile config = SharkHub.getInstance().getCosmeticsConfig();

    @Override
    public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
        new HatsMenu().openMenu(player);
    }

    @Override
    public ItemStack getItem(Player player) {
        String path = "COSMETICS_MENU.HATS.";
        return new ItemBuilder(XMaterial.matchXMaterial(Material.valueOf(config.getString(path + "ITEM"))).parseMaterial())
                .name(config.getString(path + "NAME"))
                .lore(config.getStringList( path + "LORE"))
                .data(config.getInt(path + "DATA"))
                .build();
    }
}
