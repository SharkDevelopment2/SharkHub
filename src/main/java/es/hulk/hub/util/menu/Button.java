package es.hulk.hub.util.menu;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class Button {

    public static Button placeholder(ItemStack itemStack) {
        return (new Button() {
            public ItemStack getButtonItem(Player player) {
                return itemStack;
            }
        });
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

    public abstract ItemStack getButtonItem(Player player);

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {}

    public boolean shouldUpdate(Player player, int slot, ClickType clickType) {
        return false;
    }

    public boolean shouldCancel(Player player, int slot, ClickType clickType) {
        return true;
    }

    public boolean shouldShift(Player player, int slot, ClickType clickType) {
        return true;
    }

    public void close(Player player) {
        player.closeInventory();
    }
}