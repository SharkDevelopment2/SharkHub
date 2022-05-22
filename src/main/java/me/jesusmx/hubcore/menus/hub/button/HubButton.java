package me.jesusmx.hubcore.menus.hub.button;

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
        return new ItemBuilder(Material.valueOf(config.getString(d("item"))))
                .name(PlaceholderAPI.setPlaceholders(player, config.getString(d("name"))))
                .lore(PlaceholderAPI.setPlaceholders(player, config.getStringList(d("lore"))))
                .data(config.getInt(d("data")))
                .build();
    }

    @Override
    public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
        SharkHub.getInstance().getQueueManager().getSystem().sendPlayer(player, server);
    }

    private String d(String a) {
        return "hub-selector.items." + server + "." + a;
    }
}
