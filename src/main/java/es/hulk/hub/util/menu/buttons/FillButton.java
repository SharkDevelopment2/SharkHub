package es.hulk.hub.util.menu.buttons;

import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.ItemMaker;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FillButton extends Button {

    @Override
    public ItemStack getButtonItem(Player player) {
        ConfigFile config = SharkHub.getInstance().getMainConfig();
        return new ItemMaker(Material.getMaterial(config.getString("FILL_BUTTON.ICON.MATERIAL")),
                1,
                config.getInt("FILL_BUTTON.ICON.DATA"))
                .displayName(CC.translate(player, config.getString("FILL_BUTTON.ICON.DISPLAY_NAME"), true))
                .lore(CC.translate(config.getStringList("FILL_BUTTON.ICON.LORE")))
                .build();
    }
}
