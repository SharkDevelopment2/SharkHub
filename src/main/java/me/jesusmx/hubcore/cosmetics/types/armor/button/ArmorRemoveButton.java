package me.jesusmx.hubcore.cosmetics.types.armor.button;

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

public class ArmorRemoveButton extends Button {

    private final ConfigFile config = SharkHub.getInstance().getArmorsConfig();
    private final ConfigFile messages = SharkHub.getInstance().getMessagesConfig();

    @Override
    public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
        if(player.hasMetadata("ARMOR")) {
            String s = player.getMetadata("ARMOR").get(0).asString();
            player.getInventory().setArmorContents(null);
            player.removeMetadata("ARMOR", SharkHub.getInstance());
            player.closeInventory();
            player.sendMessage(CC.translate(messages.getString("COSMETICS.ARMOR.UN_EQUIPPED").replace("%armor_remove%", s)));
        }
    }

    @Override
    public ItemStack getItem(Player player) {
        return new ItemBuilder(XMaterial.matchXMaterial(Material.valueOf(config.getString("ARMORS_MENU.REMOVE_ARMOR.ITEM"))).parseMaterial())
                .name(config.getString("ARMORS_MENU.REMOVE_ARMOR.NAME"))
                .lore(config.getStringList("ARMORS_MENU.REMOVE_ARMOR.LORE"))
                .data(config.getInt("ARMORS_MENU.REMOVE_ARMOR.DATA"))
                .build();
    }
}
