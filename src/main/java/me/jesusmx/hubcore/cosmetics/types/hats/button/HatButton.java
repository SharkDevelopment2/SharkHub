package me.jesusmx.hubcore.cosmetics.types.hats.button;

import com.cryptomorin.xseries.XMaterial;
import lombok.AllArgsConstructor;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.bukkit.ItemBuilder;
import me.jesusmx.hubcore.util.buttons.Button;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;

@AllArgsConstructor
public class HatButton extends Button {

    private String s;
    private final ConfigFile config = SharkHub.getInstance().getHatsConfig();
    private final ConfigFile messages = SharkHub.getInstance().getMessagesConfig();

    @Override
    public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
        if(!player.hasPermission("hats." + s)) {
            player.sendMessage(CC.translate(messages.getString("cosmetics.hats.no-permission")));
            return;
        }
        String path = "menu.hats." + s + ".";
        ItemStack item;
        if(config.getBoolean(path + "skull.enabled")) {
            item = new ItemStack(XMaterial.CREEPER_HEAD.parseMaterial(), (short) 3);
            SkullMeta skull = (SkullMeta) item.getItemMeta();
            skull.setOwner(config.getString(path + "skull.owner"));
            skull.setDisplayName(CC.translate(config.getString(path + "name")));
            item.setItemMeta(skull);
        } else {
            item = getItem(player);
            ItemMeta meta = item.getItemMeta();
            item.setItemMeta(meta);
        }
        player.getInventory().setHelmet(item);
        player.updateInventory();
        player.setMetadata("HAT", new FixedMetadataValue(SharkHub.getInstance(), s));
        player.sendMessage(CC.translate(messages.getString("cosmetics.hats.equipped").replace("%hat%", s)));
    }

    @Override
    public ItemStack getItem(Player player) {
        String path = "menu.hats." + s + ".";
        //System.out.println(path);
        if(config.getBoolean(path + "skull.enabled")) {
            ItemStack item = new ItemStack(XMaterial.CREEPER_HEAD.parseMaterial(), 1, (short) 3);
            SkullMeta skull = (SkullMeta) item.getItemMeta();
            skull.setOwner(config.getString(path + "skull.owner"));
            skull.setDisplayName(CC.translate(config.getString(path + "name")));
            skull.setLore(config.getStringList(path + "lore"));
            item.setItemMeta(skull);
            return item;
        } else {
            return new ItemBuilder(XMaterial.matchXMaterial(Material.valueOf(config.getString(path + "item"))).parseMaterial())
                    .name(config.getString(path + "name"))
                    .lore(config.getStringList(path + "lore"))
                    .data(config.getInt(path + "data"))
                    .build();
        }
    }
}
