package es.hulk.hub.cosmetics.types.gadgets.items;

import com.cryptomorin.xseries.XMaterial;
import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.bukkit.ItemBuilder;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;


public class SnowBallButton extends Button {

    private final ConfigFile config = SharkHub.getInstance().getGadgetsConfig();
    private final ConfigFile messages = SharkHub.getInstance().getMessagesConfig();

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if(!player.hasPermission("gadget.snowball")) {
            player.sendMessage(CC.translate(messages.getString("COSMETICS.GADGETS.NO_PERMISSION")));
            return;
        }
        player.getInventory().addItem(new ItemBuilder(getButtonItem(player)).setAmount(64).build());
        player.sendMessage(CC.translate(messages.getString("COSMETICS.GADGETS.EQUIPPED").replace("%gadget%", config.getString("GADGETS_MENU.SNOWBALL_HIDER.NAME"))));
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(XMaterial.SNOWBALL.parseMaterial())
                .name(config.getString("GADGETS_MENU.SNOWBALL_HIDER.NAME"))
                .lore(config.getStringList("GADGETS_MENU.SNOWBALL_HIDER.LORE"))
                .build();
    }
}