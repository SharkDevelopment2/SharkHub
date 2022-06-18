package es.hulk.hub.cosmetics.types.gadgets.items;

import com.cryptomorin.xseries.XMaterial;
import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.ItemBuilder;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.menu.Button;
import org.bukkit.Material;
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
        if (ServerUtil.getServerVersion().equalsIgnoreCase("v1_7_R4")) {
            player.getInventory().addItem(new ItemStack(Material.ARROW, 64));
        } else {
            player.getInventory().addItem(new ItemStack(XMaterial.ARROW.parseMaterial(), 64));
        }
        player.sendMessage(CC.translate(messages.getString("COSMETICS.GADGETS.EQUIPPED").replace("%gadget%", config.getString("GADGETS_MENU.TELEPORT_BOW.NAME"))));
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        if (ServerUtil.getServerVersion().equalsIgnoreCase("v1_7_R4")) {
            return new ItemBuilder(Material.BOW)
                    .name(config.getString("GADGETS_MENU.TELEPORT_BOW.NAME"))
                    .lore(config.getStringList("GADGETS_MENU.TELEPORT_BOW.LORE"))
                    .build();
        }
        return new ItemBuilder(XMaterial.BOW.parseMaterial())
                .name(config.getString("GADGETS_MENU.TELEPORT_BOW.NAME"))
                .lore(config.getStringList("GADGETS_MENU.TELEPORT_BOW.LORE"))
                .build();
    }
}