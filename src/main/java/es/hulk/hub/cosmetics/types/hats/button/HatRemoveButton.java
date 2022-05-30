package es.hulk.hub.cosmetics.types.hats.button;

import com.cryptomorin.xseries.XMaterial;
import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.bukkit.ItemBuilder;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class HatRemoveButton extends Button {

    private final ConfigFile config = SharkHub.getInstance().getHatsConfig();
    private final ConfigFile messages = SharkHub.getInstance().getMessagesConfig();

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if(player.hasMetadata("HAT")) {
            String hat = player.getMetadata("HAT").get(0).asString();
            player.getInventory().setHelmet(null);
            player.updateInventory();
            player.removeMetadata("HAT", SharkHub.getInstance());
            player.closeInventory();
            player.sendMessage(CC.translate(messages.getString("COSMETICS.HATS.UN_EQUIPPED").replace("%hat_remove%", hat)));
        }
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        if (ServerUtil.getServerVersion().equalsIgnoreCase("v1_7_R4")) {
            return new ItemBuilder(Material.valueOf(config.getString("HATS_MENU.REMOVE_HAT.ITEM")))
                    .name(config.getString("HATS_MENU.REMOVE_HAT.NAME"))
                    .lore(config.getStringList("HATS_MENU.REMOVE_HAT.LORE"))
                    .data(config.getInt("HATS_MENU.REMOVE_HAT.DATA"))
                    .build();
        }
        return new ItemBuilder(XMaterial.matchXMaterial(Material.valueOf(config.getString("HATS_MENU.REMOVE_HAT.ITEM"))).parseMaterial())
                .name(config.getString("HATS_MENU.REMOVE_HAT.NAME"))
                .lore(config.getStringList("HATS_MENU.REMOVE_HAT.LORE"))
                .data(config.getInt("HATS_MENU.REMOVE_HAT.DATA"))
                .build();
    }
}
