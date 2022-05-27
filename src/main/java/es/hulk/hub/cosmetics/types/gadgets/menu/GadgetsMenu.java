package es.hulk.hub.cosmetics.types.gadgets.menu;

import com.cryptomorin.xseries.XMaterial;
import es.hulk.hub.SharkHub;
import es.hulk.hub.cosmetics.types.gadgets.items.SnowBallButton;
import es.hulk.hub.cosmetics.types.gadgets.items.TeleportBowButton;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.bukkit.ItemBuilder;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.menu.Button;
import es.hulk.hub.util.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class GadgetsMenu extends Menu {

    private final ConfigFile config = SharkHub.getInstance().getGadgetsConfig();

    @Override
    public String getTitle(Player player) {
        return CC.translate(config.getString("GADGETS_MENU.TITLE"));
    }

    @Override
    public int getSize() {
        return config.getInt("GADGETS_MENU.SIZE") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        //11, 15
        Map<Integer, Button> buttons = new HashMap<>();
        buttons.put(config.getInt("GADGETS_MENU.TELEPORT_BOW.SLOT"), new TeleportBowButton());
        buttons.put(config.getInt("GADGETS_MENU.SNOWBALL_HIDER.SLOT"), new SnowBallButton());
        return buttons;
    }

    @Override
    public boolean usePlaceholder() {
        return config.getBoolean("GADGETS_MENU.REFILL_GLASS.ENABLE");
    }

    @Override
    public ItemStack getPlaceholderItem(Player player) {
        return new ItemBuilder(XMaterial.GLASS_PANE.parseMaterial()).name(" ").data(config.getInt("GADGETS_MENU.REFILL_GLASS.GLASS_DATA")).build();
    }
}