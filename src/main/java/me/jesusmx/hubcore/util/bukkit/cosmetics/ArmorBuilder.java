package me.jesusmx.hubcore.util.bukkit.cosmetics;

import com.cryptomorin.xseries.XMaterial;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.cosmetics.types.armor.button.ArmorButton;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Collections;
import java.util.Locale;
import java.util.Objects;


public class ArmorBuilder {

    private final ItemStack stack;
    private final LeatherArmorMeta meta;
    private final String part;
    private final ConfigFile config = SharkHub.getInstance().getArmorsConfig();
    private final ArmorButton button;

    public ArmorBuilder(ArmorButton button, String part) {
        this.button = button;
        this.part = part;
        this.stack = new ItemStack(XMaterial.matchXMaterial(Material.valueOf("LEATHER_" + part.toUpperCase(Locale.ROOT))).parseMaterial());
        this.meta = (LeatherArmorMeta) this.stack.getItemMeta();
        if(isPresent(part)) {
            meta.setColor(getColor());
        } else {
            meta.setColor(Color.fromBGR(
                    config.getInt(this.button.getPath() + "color.b"),
                    config.getInt(this.button.getPath() + "color.g"),
                    config.getInt(this.button.getPath() + "color.r")
                    ));
        }
        this.stack.setItemMeta(this.meta);
    }

    public ItemStack build() {
        String s = this.button.getPath() + "." + this.part + ".";
        if(!Objects.equals(config.getString(s + "name"), "ERROR: STRING NOT FOUND")) {
            this.meta.setDisplayName(CC.translate(config.getString(s + "name")));
        } else {
            this.meta.setDisplayName(ChatColor.GREEN + this.button.getArmor() + " armor");
        }
        if(!config.getStringList(s + "lore").contains("ERROR: STRING LIST NOT FOUND!")) {
            this.meta.setLore(CC.translate(config.getStringList(s + "lore")));
        } else {
            this.meta.setLore(Collections.singletonList(ChatColor.GREEN + this.button.getArmor() + " Armor"));
        }
        this.stack.setItemMeta(this.meta);
        return this.stack;
    }

    private boolean isPresent(String iPath) {
        String s = this.button.getPath() + "." + iPath + ".";
        return !Objects.equals(config.getString(s + "r"), "ERROR: STRING NOT FOUND") &&
                !Objects.equals(config.getString(s + "g"), "ERROR: STRING NOT FOUND") &&
                !Objects.equals(config.getString(s + "b"), "ERROR: STRING NOT FOUND");
    }

    private Color getColor() {
        String s = this.button.getPath() + "." + this.part + ".";
        return Color.fromBGR(
                config.getInt(s+ "b"),
                config.getInt(s + "g"),
                config.getInt(s + "r")
                );
    }
}
