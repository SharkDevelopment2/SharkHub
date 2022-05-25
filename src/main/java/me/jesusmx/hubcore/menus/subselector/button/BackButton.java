package me.jesusmx.hubcore.menus.subselector.button;

import com.cryptomorin.xseries.XMaterial;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.menus.server.ServerSelectorMenu;
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
        return new ItemBuilder(XMaterial.matchXMaterial(Material.valueOf(config.getString("SUB_SELECTOR.BACK_BUTTON.ITEM"))).parseMaterial())
                .name(config.getString("SUB_SELECTOR.BACK_BUTTON.NAME"))
                .lore(config.getStringList("SUB_SELECTOR.BACK_BUTTON.LORE"))
                .data(config.getInt("SUB_SELECTOR.BACK_BUTTON.DATA"))
                .build();
    }
}
