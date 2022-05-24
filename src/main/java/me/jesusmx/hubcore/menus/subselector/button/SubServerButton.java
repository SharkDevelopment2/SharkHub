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
        return new ItemBuilder(Material.valueOf(config.getString(getConfigPath("ITEM"))))
                .name(PlaceholderAPI.setPlaceholders(player, config.getString(getConfigPath("NAME"))))
                .lore(PlaceholderAPI.setPlaceholders(player, config.getStringList(getConfigPath("LORE"))))
                .data(config.getInt(getConfigPath("DATA")))
                .build();
    }

    @Override
    public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
        if(config.getBoolean("SUB_SELECTOR." + server + ".COMMANDS.ENABLE")) {
            Bukkit.dispatchCommand(player, config.getString("SUB_SELECTOR.ITEMS." + server + ".COMMANDS.COMMAND"));
        } else {
            SharkHub.getInstance().getQueueManager().getSystem().sendPlayer(player, server);
        }
    }

    private String getConfigPath(String a) {
        return "SUB_SELECTOR." + mServer + "." + server + "." + a;
    }
}
