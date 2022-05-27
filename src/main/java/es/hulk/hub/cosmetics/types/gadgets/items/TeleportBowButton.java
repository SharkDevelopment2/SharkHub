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

public class TeleportBowButton extends Button {

    private final ConfigFile config = SharkHub.getInstance().getGadgetsConfig();
    private final ConfigFile messages = SharkHub.getInstance().getMessagesConfig();

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if(!player.hasPermission("gadget.teleport-bow")) {
            player.sendMessage(CC.translate(messages.getString("COSMETICS.GADGETS.NO_PERMISSION")));
            return;
        }
        player.getInventory().addItem(getButtonItem(player));
        player.getInventory().addItem(new ItemStack(XMaterial.ARROW.parseMaterial(), 64));
        player.sendMessage(CC.translate(messages.getString("COSMETICS.GADGETS.EQUIPPED").replace("%gadget%", config.getString("GADGETS_MENU.TELEPORT_BOW.NAME"))));
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(XMaterial.BOW.parseMaterial())
                .name(config.getString("GADGETS_MENU.TELEPORT_BOW.NAME"))
                .lore(config.getStringList("GADGETS_MENU.TELEPORT_BOW.LORE"))
                .build();
    }
}