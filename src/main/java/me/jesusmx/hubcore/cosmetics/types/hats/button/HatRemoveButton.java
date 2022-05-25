package me.jesusmx.hubcore.cosmetics.types.hats.button;

import com.cryptomorin.xseries.XMaterial;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.bukkit.ItemBuilder;
import me.jesusmx.hubcore.util.buttons.Button;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class HatRemoveButton extends Button {

    private final ConfigFile config = SharkHub.getInstance().getHatsConfig();
    private final ConfigFile messages = SharkHub.getInstance().getMessagesConfig();

    @Override
    public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
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
    public ItemStack getItem(Player player) {
        return new ItemBuilder(XMaterial.matchXMaterial(Material.valueOf(config.getString("HATS_MENU.REMOVE_HAT.ITEM"))).parseMaterial())
                .name(config.getString("HATS_MENU.REMOVE_HAT.NAME"))
                .lore(config.getStringList("HATS_MENU.REMOVE_HAT.LORE"))
                .data(config.getInt("HATS_MENU.REMOVE_HAT.DATA"))
                .build();
    }
}
