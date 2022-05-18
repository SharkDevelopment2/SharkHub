package me.jesusmx.hubcore.cosmetics.types.gadgets.button;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.cosmetics.types.gadgets.menu.GadgetsMenu;
import me.jesusmx.hubcore.util.bukkit.ItemBuilder;
import me.jesusmx.hubcore.util.buttons.Button;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class GadgetsMenuButton extends Button {

    private final ConfigFile config = SharkHub.getInstance().getCosmeticsConfig();

    @Override
    public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
        new GadgetsMenu().openMenu(player);
    }

    @Override
    public ItemStack getItem(Player player) {
        String s = "cosmetics.gadgets.";
        return new ItemBuilder(Material.valueOf(config.getString(s + "item")))
                .name(config.getString(s + "name"))
                .lore(config.getStringList( s + "lore"))
                .data(config.getInt(s + "data"))
                .build();
    }
}