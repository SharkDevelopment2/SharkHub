package es.hulk.hub.cosmetics.base.menu;

import es.hulk.hub.SharkHub;
import es.hulk.hub.cosmetics.types.armor.button.ArmorMenuButton;
import es.hulk.hub.cosmetics.types.gadgets.button.GadgetsMenuButton;
import es.hulk.hub.cosmetics.types.hats.button.HatMenuButton;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.menu.Button;
import es.hulk.hub.util.menu.Menu;
import es.hulk.hub.util.menu.buttons.FillButton;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CosmeticsMenu extends Menu {

    private final ConfigFile config = SharkHub.getInstance().getCosmeticsConfig();

    @Override
    public String getTitle(Player player) {
        return CC.translate(config.getString("COSMETICS_MENU.TITLE"));
    }

    @Override
    public int getSize() {
        return config.getInt("COSMETICS_MENU.SIZE") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        buttons.put(config.getInt("COSMETICS_MENU.HATS.SLOT"), new HatMenuButton());
        buttons.put(config.getInt("COSMETICS_MENU.ARMOR.SLOT"), new ArmorMenuButton());
        buttons.put(config.getInt("COSMETICS_MENU.GADGETS.SLOT"), new GadgetsMenuButton());
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
