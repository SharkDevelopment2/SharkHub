package es.hulk.hub.util.menu.buttons;

import es.hulk.hub.util.ItemBuilder;
import es.hulk.hub.util.menu.Button;
import es.hulk.hub.util.menu.Menu;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class BackButton extends Button {

    private final Menu back;

    @Override
    public ItemStack getButtonItem(Player player) {
        return back.getPlaceholderButton().getButtonItem(player);
    }

    @Override
    public void clicked(Player player, int i, ClickType clickType, int hb) {
        playNeutral(player);
        this.back.openMenu(player);
    }
}
