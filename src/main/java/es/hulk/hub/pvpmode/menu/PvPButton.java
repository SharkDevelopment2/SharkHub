package es.hulk.hub.pvpmode.menu;

import es.hulk.hub.pvpmode.PvPMode;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PvPButton extends Button {

    private final PvPMode pvpmode;

    public PvPButton(PvPMode pvpmode) {
        this.pvpmode = pvpmode;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        ItemStack item = pvpmode.getItem();
        List<String> lore = item.getItemMeta().getLore();

        for (String line : lore) {
            lore.add(CC.translate(line.replace("<players>", String.valueOf(pvpmode.getPlayers().size()))));
        }

        return item;
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        pvpmode.getPlayers().add(player);
        player.teleport(pvpmode.getLocation());
        player.getInventory().setArmorContents(pvpmode.getArmor());
        player.getInventory().setContents(pvpmode.getInventory());
        player.sendMessage(CC.translate("&aYou are now in &e" + pvpmode.getDisplayName() + "&a mode!"));
    }
}
