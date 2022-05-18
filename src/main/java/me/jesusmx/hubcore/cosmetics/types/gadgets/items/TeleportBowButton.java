package me.jesusmx.hubcore.cosmetics.types.gadgets.items;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.bukkit.ItemBuilder;
import me.jesusmx.hubcore.util.buttons.Button;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class TeleportBowButton extends Button {

    private final ConfigFile config = SharkHub.getInstance().getGadgetsConfig();
    private final ConfigFile messages = SharkHub.getInstance().getMessagesConfig();

    @Override
    public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
        if(!player.hasPermission("gadget.teleport-bow")) {
            player.sendMessage(CC.translate(messages.getString("cosmetics.gadgets.no-permission")));
            return;
        }
        player.getInventory().addItem(getItem(player));
        player.getInventory().addItem(new ItemStack(Material.ARROW, 64));
        player.sendMessage(CC.translate(messages.getString("cosmetics.gadgets.equipped").replace("%gadget%", config.getString("menu.bow.name"))));
    }

    @Override
    public ItemStack getItem(Player player) {
        return new ItemBuilder(Material.BOW)
                .name(config.getString("menu.bow.name"))
                .lore(config.getStringList("menu.bow.lore"))
                .build();
    }
}