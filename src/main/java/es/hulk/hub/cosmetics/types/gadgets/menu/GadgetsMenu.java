package es.hulk.hub.cosmetics.types.gadgets.menu;

import es.hulk.hub.SharkHub;
import es.hulk.hub.cosmetics.types.gadgets.items.SnowBallButton;
import es.hulk.hub.cosmetics.types.gadgets.items.TeleportBowButton;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.menu.Button;
import es.hulk.hub.util.menu.Menu;
import es.hulk.hub.util.menu.buttons.FillButton;
import org.bukkit.entity.Player;

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
        Map<Integer, Button> buttons = new HashMap<>();
        buttons.put(config.getInt("GADGETS_MENU.TELEPORT_BOW.SLOT"), new TeleportBowButton());
        buttons.put(config.getInt("GADGETS_MENU.SNOWBALL_HIDER.SLOT"), new SnowBallButton());
        return buttons;
    }

    @Override
    public boolean isPlaceholder() {
        return config.getBoolean("SERVER_SELECTOR.REFILL_GLASS.ENABLE");
    }

    @Override
    public Button getPlaceholderButton() {
        return new FillButton();
    }
}