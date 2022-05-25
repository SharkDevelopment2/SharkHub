package me.jesusmx.hubcore.cosmetics.types.armor.button;

import com.cryptomorin.xseries.XMaterial;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.cosmetics.types.armor.menu.ArmorsMenu;
import me.jesusmx.hubcore.util.bukkit.ItemBuilder;
import me.jesusmx.hubcore.util.buttons.Button;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ArmorMenuButton extends Button {

    private final ConfigFile config = SharkHub.getInstance().getCosmeticsConfig();

    @Override
    public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
        new ArmorsMenu().openMenu(player);
    }

    @Override
    public ItemStack getItem(Player player) {
        String s = "cosmetics.armor.";
        return new ItemBuilder(XMaterial.matchXMaterial(Material.valueOf(config.getString(s + "item"))).parseMaterial())
                .name(config.getString(s + "name"))
                .lore(config.getStringList( s + "lore"))
                .data(config.getInt(s + "data"))
                .build();
    }
}
