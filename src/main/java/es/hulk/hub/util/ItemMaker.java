package es.hulk.hub.util;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static es.hulk.hub.util.CC.translate;

public class ItemMaker {

    private final ItemStack itemStack;

    public static ItemMaker of(Material material) {
        if (Bukkit.getVersion().contains("1.7")) {
            return new ItemMaker(material, 1, (short) 0);
        }else{
            return new ItemMaker(XMaterial.matchXMaterial(material).parseMaterial(), 1, (short) 0);
        }
    }

    public static ItemMaker of(Material material, int amount) {
        if (Bukkit.getVersion().contains("1.7")) {
            return new ItemMaker(material, amount, (short) 0);
        }else{
            return new ItemMaker(XMaterial.matchXMaterial(material).parseMaterial(), amount, (short) 0);
        }
    }

    public static ItemMaker of(Material material, int amount, int data) {
        if (Bukkit.getVersion().contains("1.7")) {
            return new ItemMaker(material, amount, (short) data);
        }else{
            return new ItemMaker(XMaterial.matchXMaterial(material).parseMaterial(), amount, (short) data);
        }
    }

    public ItemMaker(Material material, int amount, short data) {
        if (Bukkit.getVersion().contains("1.7")) {
            this.itemStack = new ItemStack(material, amount, data);
        } else {
            this.itemStack = new ItemStack(XMaterial.matchXMaterial(material).parseMaterial(), amount, data);
        }
    }

    public ItemMaker amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemMaker data(short data) {
        itemStack.setDurability(data);
        return this;
    }

    public ItemMaker data(int data) {
        itemStack.setDurability((short)data);
        return this;
    }


    public ItemMaker displayName(String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(translate(name));

        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemMaker lore(String... lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(Arrays.stream(lore).map(CC::translate).collect(Collectors.toList()));

        itemStack.setItemMeta(itemMeta);

        return this;
    }
    public ItemMaker addLore(String lores) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        List<String> lore = itemMeta.getLore();
        if (lore == null) {
            lore = Lists.newArrayList();
        }
        lore.add(translate(lores));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }


    public ItemMaker lore(List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore.stream().map(CC::translate).collect(Collectors.toList()));

        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemMaker owner(String owner) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta instanceof SkullMeta) {
            SkullMeta skullMeta = (SkullMeta) itemMeta;
            skullMeta.setOwner(owner);

            itemStack.setItemMeta(itemMeta);
        }

        return this;
    }

    public ItemMaker color(Color color) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta instanceof LeatherArmorMeta) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
            leatherArmorMeta.setColor(color);

            itemStack.setItemMeta(itemMeta);
        }

        return this;
    }

    public ItemMaker enchant(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);

        return this;
    }

    public ItemMaker removeEnchantment(Enchantment enchantment) {
        itemStack.removeEnchantment(enchantment);

        return this;
    }

    public ItemStack build() {
        return itemStack.clone();
    }

}
