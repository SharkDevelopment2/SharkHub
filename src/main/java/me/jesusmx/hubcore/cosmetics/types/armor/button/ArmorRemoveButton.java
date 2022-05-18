package me.jesusmx.hubcore.cosmetics.types.armor.button;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.bukkit.ItemBuilder;
import me.jesusmx.hubcore.util.buttons.Button;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.ChatColor;
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
            player.getInventory().setHelmet(new ItemStack(Material.AIR));
            player.getInventory().setChestplate(new ItemStack(Material.AIR));
            player.getInventory().setLeggings(new ItemStack(Material.AIR));
            player.getInventory().setBoots(new ItemStack(Material.AIR));
            player.removeMetadata("ARMOR", SharkHub.getInstance());
            player.closeInventory();
            player.sendMessage(CC.translate(messages.getString("cosmetics.armor.un-equipped").replace("%armor-remove%", s)));
        } else {

        }
    }

    @Override
    public ItemStack getItem(Player player) {
       /* if(player.hasMetadata("HAT")) {
            //String hat = player.getMetadata("HAT").get(0).asString();
        */
        return new ItemBuilder(Material.valueOf(config.getString("menu.remove_hat.item")))
                .name(config.getString("menu.remove_hat.name"))
                .lore(config.getStringList("menu.remove_hat.lore"))
                .data(config.getInt("menu.remove_hat.data"))
                .build();
    }
}
