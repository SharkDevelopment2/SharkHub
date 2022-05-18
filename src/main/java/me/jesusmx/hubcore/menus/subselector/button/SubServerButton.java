package me.jesusmx.hubcore.menus.subselector.button;

import lombok.AllArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.bukkit.ItemBuilder;
import me.jesusmx.hubcore.util.buttons.Button;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class SubServerButton extends Button {

    private String mServer;
    private String server;
    private final ConfigFile config = SharkHub.getInstance().getSubselectorConfig();

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
        if(config.getBoolean("sub-selector.items." + server + ".command.enabled")) {
            Bukkit.dispatchCommand(player, config.getString("sub-selector.items." + server + ".command.command"));
        } else {
            SharkHub.getInstance().getQueueManager().sendPlayer(player, server);
        }
    }

    private String d(String a) {
        return "sub-selector." + mServer + "." + server + "." + a;
    }
}
