package es.hulk.hub.cosmetics.types.armor.button;

import com.cryptomorin.xseries.XMaterial;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.bukkit.cosmetics.ArmorBuilder;
import lombok.Getter;
import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.bukkit.ItemBuilder;
import es.hulk.hub.util.menu.Button;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.FixedMetadataValue;

@Getter
public class ArmorButton extends Button {

    private final String armor;
    private final String path;
    private final ConfigFile config = SharkHub.getInstance().getArmorsConfig();
    private final ConfigFile messages = SharkHub.getInstance().getMessagesConfig();

    public ArmorButton(String armor) {
        this.armor = armor;
        this.path = "ARMORS_MENU.ARMORS." + armor + ".";
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if(!player.hasPermission(config.getString(path + "PERMISSION"))) {
            player.sendMessage(CC.translate(messages.getString("COSMETICS.ARMOR.NO_PERMISSION")));
            return;
        }

        player.getInventory().setHelmet(new ArmorBuilder(this, "HELMET").build());
        player.getInventory().setChestplate(new ArmorBuilder(this, "CHESTPLATE").build());
        player.getInventory().setLeggings(new ArmorBuilder(this, "LEGGINGS").build());
        player.getInventory().setBoots(new ArmorBuilder(this, "BOOTS").build());

        player.updateInventory();
        player.setMetadata("ARMOR", new FixedMetadataValue(SharkHub.getInstance(), armor));
        player.sendMessage(CC.translate(messages.getString("COSMETICS.ARMOR.EQUIPPED").replace("%armor%", armor)));
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        ItemBuilder builder;
        if (ServerUtil.getServerVersion().equalsIgnoreCase("v1_7_R4")) {
            builder = new ItemBuilder(Material.LEATHER_HELMET)
                    .data(config.getInt(path + "ICON.DATA"))
                    .name(config.getString(path + "ICON.NAME"));
        } else {
            builder = new ItemBuilder(XMaterial.LEATHER_HELMET.parseMaterial())
                    .data(config.getInt(path + "ICON.DATA"))
                    .name(config.getString(path + "ICON.NAME"));
        }
        if(player.hasPermission(config.getString(path + "PERMISSION"))) {
            builder.lore(config.getStringList(path + "ICON.LORE.WITH_PERMISSION"));
        } else {
            builder.lore(config.getStringList(path + "ICON.LORE.WITHOUT_PERMISSION"));
        }
        ItemStack stack = builder.build();
        LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
        meta.setColor(Color.fromBGR(
                config.getInt(path + "ICON.COLOR.B"),
                config.getInt(path + "ICON.COLOR.G"),
                config.getInt(path + "ICON.COLOR.R")
        ));
        stack.setItemMeta(meta);
        return stack;
    }
}
