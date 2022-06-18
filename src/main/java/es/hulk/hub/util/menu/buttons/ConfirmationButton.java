package es.hulk.hub.util.menu.buttons;

import es.hulk.hub.util.menu.Button;
import es.hulk.hub.util.ItemBuilder;
import es.hulk.hub.util.menu.callback.TypeCallback;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ConfirmationButton extends Button {
    private final boolean confirm;
    private final TypeCallback<Boolean> callback;
    private final boolean closeAfterResponse;

    @Override
    public ItemStack getButtonItem(final Player player) {
        return new ItemBuilder(Material.WOOL).data((short) (this.confirm ? 5 : 14)).name(this.confirm ? "&aConfirm" : "&cCancel").build();
    }

    @Override
    public void clicked(final Player player, final int i, final ClickType clickType, final int hb) {
        if (this.confirm) {
            player.playSound(player.getLocation(), Sound.CLICK, 20.0f, 0.1f);
        } else {
            player.playSound(player.getLocation(), Sound.DIG_GRAVEL, 20.0f, 0.1f);
        }
        if (this.closeAfterResponse) {
            player.closeInventory();
        }
        this.callback.callback(this.confirm);
    }

    public ConfirmationButton(final boolean confirm, final TypeCallback<Boolean> callback, final boolean closeAfterResponse) {
        this.confirm = confirm;
        this.callback = callback;
        this.closeAfterResponse = closeAfterResponse;
    }
}
