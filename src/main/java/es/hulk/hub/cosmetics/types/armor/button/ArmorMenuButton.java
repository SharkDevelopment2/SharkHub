package es.hulk.hub.cosmetics.types.armor.button;

import com.cryptomorin.xseries.XMaterial;
import es.hulk.hub.SharkHub;
import es.hulk.hub.cosmetics.types.armor.menu.ArmorsMenu;
import es.hulk.hub.util.ItemBuilder;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ArmorMenuButton extends Button {

    private final ConfigFile config = SharkHub.getInstance().getCosmeticsConfig();

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        new ArmorsMenu().openMenu(player);
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        String path = "COSMETICS_MENU.ARMOR.";
        if (ServerUtil.getServerVersion().equalsIgnoreCase("v1_7_R4")) {
            return new ItemBuilder(Material.valueOf(config.getString(path + "ITEM")))
                    .name(config.getString(path + "NAME"))
                    .lore(config.getStringList( path + "LORE"))
                    .data(config.getInt(path + "DATA"))
                    .build();
        }
        return new ItemBuilder(XMaterial.matchXMaterial(Material.valueOf(config.getString(path + "ITEM"))).parseMaterial())
                .name(config.getString(path + "NAME"))
                .lore(config.getStringList( path + "LORE"))
                .data(config.getInt(path + "DATA"))
                .build();
    }
}
