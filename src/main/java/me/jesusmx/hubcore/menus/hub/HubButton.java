package me.jesusmx.hubcore.menus.hub;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.bukkit.ItemBuilder;
import me.jesusmx.hubcore.util.buttons.Button;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class HubButton extends Button {

    private final ConfigFile config = SharkHub.getInstance().getHubselectorConfig();
    private final String server;

    public HubButton(String server) {
        this.server = server;
    }

    @Override


    public ItemStack getItem(Player player) {
        return new ItemBuilder(Material.valueOf(config.getString(getSection("ITEM"))))
                .name(PlaceholderAPI.setPlaceholders(player, config.getString(getSection("NAME"))))
                .lore(PlaceholderAPI.setPlaceholders(player, config.getStringList(getSection("LORE"))))
                .data(config.getInt(getSection("DATA")))
                .build();
    }

    @Override
    public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
        SharkHub.getInstance().getQueueManager().getSystem().sendPlayer(player, server);
    }

    private String getSection(String section) {
        return "HUB_SELECTOR.ITEMS." + server + "." + section;
    }
}
