package es.hulk.hub.util.menu.buttons;

import es.hulk.hub.util.ItemBuilder;
import es.hulk.hub.util.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class AirButton extends Button {
    @Override
    public ItemStack getButtonItem(final Player player) {
        return new ItemBuilder(Material.AIR).build();
    }

    @Override
    public boolean shouldCancel(final Player player, final int slot, final ClickType clickType) {
        return true;
    }
}