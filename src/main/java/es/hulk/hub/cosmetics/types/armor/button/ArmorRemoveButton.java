package es.hulk.hub.cosmetics.types.armor.button;

import com.cryptomorin.xseries.XMaterial;
import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.ItemBuilder;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ArmorRemoveButton extends Button {

    private final ConfigFile config = SharkHub.getInstance().getArmorsConfig();
    private final ConfigFile messages = SharkHub.getInstance().getMessagesConfig();

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if(player.hasMetadata("ARMOR")) {
            String s = player.getMetadata("ARMOR").get(0).asString();
            player.getInventory().setArmorContents(null);
            player.removeMetadata("ARMOR", SharkHub.getInstance());
            player.closeInventory();
            player.sendMessage(CC.translate(messages.getString("COSMETICS.ARMOR.UN_EQUIPPED").replace("%armor_remove%", s)));
        }
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        if (ServerUtil.getServerVersion().equalsIgnoreCase("v1_7_R4")) {
            return new ItemBuilder(Material.valueOf(config.getString("ARMORS_MENU.REMOVE_ARMOR.ITEM")))
                    .name(config.getString("ARMORS_MENU.REMOVE_ARMOR.NAME"))
                    .lore(config.getStringList("ARMORS_MENU.REMOVE_ARMOR.LORE"))
                    .data(config.getInt("ARMORS_MENU.REMOVE_ARMOR.DATA"))
                    .build();
        } else {
            return new ItemBuilder(XMaterial.matchXMaterial(Material.valueOf(config.getString("ARMORS_MENU.REMOVE_ARMOR.ITEM"))).parseMaterial())
                    .name(config.getString("ARMORS_MENU.REMOVE_ARMOR.NAME"))
                    .lore(config.getStringList("ARMORS_MENU.REMOVE_ARMOR.LORE"))
                    .data(config.getInt("ARMORS_MENU.REMOVE_ARMOR.DATA"))
                    .build();
        }
    }
}
