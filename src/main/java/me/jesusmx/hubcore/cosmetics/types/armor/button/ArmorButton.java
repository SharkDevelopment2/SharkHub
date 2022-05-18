package me.jesusmx.hubcore.cosmetics.types.armor.button;

import lombok.Getter;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.bukkit.cosmetics.ArmorBuilder;
import me.jesusmx.hubcore.util.bukkit.ItemBuilder;
import me.jesusmx.hubcore.util.buttons.Button;
import me.jesusmx.hubcore.util.files.ConfigFile;
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
        this.path = "menu.armors." + armor + ".";
    }

    @Override
    public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
        if(!player.hasPermission(config.getString(path + "permission"))) {
            player.sendMessage(CC.translate(messages.getString("cosmetics.armor.no-permission")));
            return;
        }
        player.getInventory().setHelmet(
                new ArmorBuilder(this, "helmet")
                        .build()
        );
        player.getInventory().setChestplate(
                new ArmorBuilder(this, "chestplate")
                        .build()
        );
        player.getInventory().setLeggings(
                new ArmorBuilder(this, "leggings")
                        .build()
        );
        player.getInventory().setBoots(
                new ArmorBuilder(this, "boots")
                        .build()
        );
        player.updateInventory();
        player.setMetadata("ARMOR", new FixedMetadataValue(SharkHub.getInstance(), armor));
        player.sendMessage(CC.translate(messages.getString("cosmetics.armor.equipped").replace("%armor%", armor)));
    }

    @Override
    public ItemStack getItem(Player player) {
        ItemBuilder builder = new ItemBuilder(Material.LEATHER_HELMET)
                .data(config.getInt(path + "icon.data"))
                .name(config.getString(path + "icon.name"));
        if(player.hasPermission(config.getString(path + "permission"))) {
            builder.lore(config.getStringList(path + "icon.lore.with_permissions"));
        } else {
            builder.lore(config.getStringList(path + "icon.lore.without_permissions"));
        }
        ItemStack stack = builder.build();
        LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
        meta.setColor(Color.fromBGR(
                config.getInt(path + "icon.color.b"),
                config.getInt(path + "icon.color.g"),
                config.getInt(path + "icon.color.r")
        ));
        stack.setItemMeta(meta);
        return stack;
    }
}
