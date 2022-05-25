package me.jesusmx.hubcore.cosmetics.types.gadgets.items;

import com.cryptomorin.xseries.XMaterial;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.bukkit.ItemBuilder;
import me.jesusmx.hubcore.util.buttons.Button;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;


public class SnowBallButton extends Button {

    private final ConfigFile config = SharkHub.getInstance().getGadgetsConfig();
    private final ConfigFile messages = SharkHub.getInstance().getMessagesConfig();

    @Override
    public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
        if(!player.hasPermission("gadget.snowball")) {
            player.sendMessage(CC.translate(messages.getString("COSMETICS.GADGETS.NO_PERMISSION")));
            return;
        }
        player.getInventory().addItem(new ItemBuilder(getItem(player)).setAmount(64).build());
        player.sendMessage(CC.translate(messages.getString("COSMETICS.GADGETS.EQUIPPED").replace("%GADGET%", config.getString("GADGETS_MENU.SNOWBALL_HIDER.NAME"))));
    }

    @Override
    public ItemStack getItem(Player player) {
        return new ItemBuilder(XMaterial.SNOWBALL.parseMaterial())
                .name(config.getString("GADGETS_MENU.SNOWBALL_HIDER.NAME"))
                .lore(config.getStringList("GADGETS_MENU.SNOWBALL_HIDER.LORE"))
                .build();
    }
}