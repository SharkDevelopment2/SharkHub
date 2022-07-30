package es.hulk.hub.cosmetics.types.hats.button;

import com.cryptomorin.xseries.XMaterial;
import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.ItemBuilder;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.util.menu.Button;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;

@AllArgsConstructor
public class HatButton extends Button {

    private String hatName;
    private final ConfigFile config = SharkHub.getInstance().getHatsConfig();
    private final ConfigFile messages = SharkHub.getInstance().getMessagesConfig();

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if(!player.hasPermission("hats." + hatName)) {
            player.sendMessage(CC.translate(messages.getString("COSMETICS.HATS.NO_PERMISSION")));
            return;
        }
        String path = "HATS_MENU.HATS." + hatName + ".";
        ItemStack item;
        if(config.getBoolean(path + "SKULL.ENABLE")) {
            if (ServerUtil.getServerVersion().equalsIgnoreCase("v1_7_R4")) {
                item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            } else {
                item = new ItemStack(XMaterial.CREEPER_HEAD.parseMaterial(), (short) 3);
            }
            SkullMeta skull = (SkullMeta) item.getItemMeta();
            skull.setOwner(config.getString(path + "SKULL.OWNER"));
            skull.setDisplayName(CC.translate(config.getString(path + "NAME")));
            item.setItemMeta(skull);
        } else {
            item = getButtonItem(player);
            ItemMeta meta = item.getItemMeta();
            item.setItemMeta(meta);
        }
        player.getInventory().setHelmet(item);
        player.updateInventory();
        player.setMetadata("HAT", new FixedMetadataValue(SharkHub.getInstance(), hatName));
        player.sendMessage(CC.translate(messages.getString("COSMETICS.HATS.EQUIPPED").replace("%hat%", hatName)));
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        String path = "HATS_MENU.HATS." + hatName + ".";
        if(config.getBoolean(path + "SKULL.ENABLE")) {
            ItemStack item;
            if (ServerUtil.getServerVersion().equalsIgnoreCase("v1_7_R4")) {
                item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            } else {
                item = new ItemStack(XMaterial.CREEPER_HEAD.parseMaterial(), (short) 3);
            }
            SkullMeta skull = (SkullMeta) item.getItemMeta();
            skull.setOwner(config.getString(path + "SKULL.OWNER"));
            skull.setDisplayName(CC.translate(config.getString(path + "NAME")));
            skull.setLore(config.getStringList(path + "LORE"));
            item.setItemMeta(skull);
            return item;
        } else {
            if (ServerUtil.getServerVersion().equalsIgnoreCase("v1_7_R4")) {
                return new ItemBuilder(Material.valueOf(config.getString(path + "ITEM")))
                        .name(config.getString(path + "NAME"))
                        .lore(config.getStringList(path + "LORE"))
                        .data(config.getInt(path + "DATA"))
                        .build();
            }
            return new ItemBuilder(XMaterial.matchXMaterial(Material.valueOf(config.getString(path + "ITEM"))).parseMaterial())
                    .name(config.getString(path + "NAME"))
                    .lore(config.getStringList(path + "LORE"))
                    .data(config.getInt(path + "DATA"))
                    .build();
        }
    }
}
