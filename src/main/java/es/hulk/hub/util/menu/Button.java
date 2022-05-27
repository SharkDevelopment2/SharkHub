package es.hulk.hub.util.menu;

import es.hulk.hub.util.bukkit.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class Button {
    public static Button placeholder(final Material material, final short data, final String title) {
        return new Button() {
            @Override
            public ItemStack getButtonItem(final Player player) {
                return new ItemBuilder(material).data(data).name(title).build();
            }
        };
    }

    public static void playFail(final Player player) {
        player.playSound(player.getLocation(), Sound.DIG_GRASS, 20.0f, 0.1f);
    }

    public static void playSuccess(final Player player) {
        player.playSound(player.getLocation(), Sound.CAT_MEOW, 20.0f, 15.0f);
    }

    public static void playNeutral(final Player player) {
        player.playSound(player.getLocation(), Sound.CLICK, 20.0f, 1.0f);
    }

    public abstract ItemStack getButtonItem(final Player p0);

    public void clicked(final Player player, final int slot, final ClickType clickType, final int hotbarButton) {
    }

    public boolean shouldUpdate(final Player player, final int slot, final ClickType clickType) {
        return false;
    }

    public boolean shouldCancel(final Player player, final int slot, final ClickType clickType) {
        return true;
    }

    public boolean shouldShift(final Player player, final int slot, final ClickType clickType) {
        return true;
    }
}

