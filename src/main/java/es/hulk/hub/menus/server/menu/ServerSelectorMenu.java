package es.hulk.hub.menus.server.menu;

import es.hulk.hub.SharkHub;
import es.hulk.hub.menus.server.Server;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.menu.Button;
import es.hulk.hub.util.menu.Menu;
import es.hulk.hub.util.menu.buttons.FillButton;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ServerSelectorMenu extends Menu {

    private final ConfigFile config = SharkHub.getInstance().getSelectorConfig();

    @Override
    public String getTitle(Player player) {
        return CC.translate(config.getString("SERVER_SELECTOR.TITLE"));
    }

    @Override
    public int getSize() {
        return config.getInt("SERVER_SELECTOR.SIZE") * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        for (Server server : SharkHub.getInstance().getServerManager().getServerList()) {
            buttons.put(server.getSlot(), new ServerButton(server));
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
