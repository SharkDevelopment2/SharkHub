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

    private final ConfigFile config = SharkHub.getInstance().getHatsConfig();
    private final ConfigFile messages = SharkHub.getInstance().getMessagesConfig();

    @Override
    public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
        if(player.hasMetadata("ARMOR")) {
            String s = player.getMetadata("ARMOR").get(0).asString();
            player.getInventory().setArmorContents(null);
            player.removeMetadata("ARMOR", SharkHub.getInstance());
            player.closeInventory();
            player.sendMessage(CC.translate(messages.getString("cosmetics.armor.un-equipped").replace("%armor-remove%", s)));
        }
    }

    @Override
    public ItemStack getItem(Player player) {
       /* if(player.hasMetadata("HAT")) {
            //String hat = player.getMetadata("HAT").get(0).asString();
        */
        return new ItemBuilder(XMaterial.matchXMaterial(Material.valueOf(config.getString("menu.remove_hat.item"))).parseMaterial())
                .name(config.getString("menu.remove_hat.name"))
                .lore(config.getStringList("menu.remove_hat.lore"))
                .data(config.getInt("menu.remove_hat.data"))
                .build();
    }
}
