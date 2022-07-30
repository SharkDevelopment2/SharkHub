package es.hulk.hub.menus.hub.menu;

import es.hulk.hub.SharkHub;
import es.hulk.hub.menus.hub.Hub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.menu.Button;
import es.hulk.hub.util.menu.Menu;
import es.hulk.hub.util.menu.buttons.FillButton;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class HubSelectorMenu extends Menu {

    private ConfigFile config = SharkHub.getInstance().getHubselectorConfig();

    @Override
    public String getTitle(Player player) {
        return CC.translate(config.getString("HUB_SELECTOR.TITLE"));
    }

    @Override
    public int getSize() {
        return config.getInt("HUB_SELECTOR.SIZE") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        for (Hub hub : SharkHub.getInstance().getHubManager().getHubList()) {
            buttons.put(hub.getSlot(), new HubButton(hub));
        }

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

