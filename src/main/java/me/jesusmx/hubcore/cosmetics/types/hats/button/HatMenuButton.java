package me.jesusmx.hubcore.cosmetics.types.hats.button;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.cosmetics.types.hats.menu.HatsMenu;
import me.jesusmx.hubcore.util.bukkit.ItemBuilder;
import me.jesusmx.hubcore.util.buttons.Button;
import me.jesusmx.hubcore.util.files.ConfigFile;
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
        String s = "cosmetics.hats.";
        return new ItemBuilder(Material.valueOf(config.getString(s + "item")))
                .name(config.getString(s + "name"))
                .lore(config.getStringList( s + "lore"))
                .data(config.getInt(s + "data"))
                .build();
    }
}
