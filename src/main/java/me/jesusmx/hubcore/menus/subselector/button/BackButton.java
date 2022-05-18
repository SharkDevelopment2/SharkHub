package me.jesusmx.hubcore.menus.subselector.button;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.menus.server.menu.ServerSelectorMenu;
import me.jesusmx.hubcore.util.bukkit.ItemBuilder;
import me.jesusmx.hubcore.util.buttons.Button;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class BackButton extends Button {

    private final ConfigFile config = SharkHub.getInstance().getSubselectorConfig();

    @Override
    public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
        new ServerSelectorMenu().openMenu(player);
    }

    @Override
    public ItemStack getItem(Player player) {
        return new ItemBuilder(Material.valueOf(config.getString("sub-selector.back-button.item")))
                .name(config.getString("sub-selector.back-button.name"))
                .lore(config.getStringList("sub-selector.back-button.lore"))
                .data(config.getInt("sub-selector.back-button.data"))
                .build();
    }
}
