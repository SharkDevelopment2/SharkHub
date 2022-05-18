package me.jesusmx.hubcore.pvpmode.cache;

import lombok.Getter;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.manager.listeners.items.HidePlayersListener;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.bukkit.ItemBuilder;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PvPModeHandler {

    private static Map<UUID, Long> inPvPMode;
    @Getter private static Map<UUID, Integer> kills;
    static ConfigFile messages = SharkHub.getInstance().getMessagesConfig();

    public PvPModeHandler() {
        inPvPMode = new HashMap<>();
        kills = new HashMap<>();
    }

    public static long getTime(Player player) {
        return inPvPMode.get(player.getUniqueId());
    }

    public static boolean isOnPvPMode(Player player) {
        return inPvPMode.containsKey(player.getUniqueId());
    }

    public static void togglePvPMode(Player player) {
        if(SharkHub.getInstance().getPvpmodeConfig().getConfiguration().get("inventory") == null || SharkHub.getInstance().getPvpmodeConfig().getConfiguration().get("armor") == null) {
            player.sendMessage(CC.translate(messages.getString("pvp-mode.not-configured")));
            return;
        }
        if(isOnPvPMode(player)) {
            disablePvPMode(player);
        } else {
            enablePvPMode(player);
        }
    }

    public static void disablePvPMode(Player player) {
        /* Disable */
        inPvPMode.remove(player.getUniqueId());
        kills.remove(player.getUniqueId());
        ConfigFile config = SharkHub.getInstance().getMainConfig();
        ConfigFile hotbar = SharkHub.getInstance().getHotbarConfig();
        ConfigFile toggle = SharkHub.getInstance().getTogglesConfig();
        player.getActivePotionEffects().clear();
        player.getInventory().clear();
        player.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
        player.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
        player.getInventory().setLeggings(new ItemStack(Material.AIR, 1));
        player.getInventory().setBoots(new ItemStack(Material.AIR, 1));

        if (toggle.getBoolean("normal.server-selector.enabled")) {
            ItemStack selector = new ItemBuilder(Material.valueOf(hotbar.getString("server-selector.material")))
                    .name(hotbar.getString("server-selector.name"))
                    .lore(hotbar.getStringList("server-selector.lore"))
                    .data(hotbar.getInt("server-selector.data"))
                    .build();
            player.getInventory().setItem(hotbar.getInt("server-selector.slot"), selector);
        }

        if (toggle.getBoolean("normal.cosmetics.enabled")) {
            ItemStack cosmetics = new ItemBuilder(Material.valueOf(hotbar.getString("cosmetics.material")))
                    .name(hotbar.getString("cosmetics.name"))
                    .lore(hotbar.getStringList("cosmetics.lore"))
                    .data(hotbar.getInt("cosmetics.data"))
                    .build();
            player.getInventory().setItem(hotbar.getInt("cosmetics.slot"), cosmetics);
        }

        if (toggle.getBoolean("normal.pvp-mode.enabled")) {
            ItemStack pvpmode = new ItemBuilder(Material.valueOf(hotbar.getString("pvp-mode.material")))
                    .name(hotbar.getString("pvp-mode.name"))
                    .lore(hotbar.getStringList("pvp-mode.lore"))
                    .data(hotbar.getInt("pvp-mode.data"))
                    .build();
            player.getInventory().setItem(hotbar.getInt("pvp-mode.slot"), pvpmode);
        }

        if (toggle.getBoolean("normal.butt.enabled")) {
            ItemStack enderbutt = new ItemBuilder(Material.valueOf(hotbar.getString("butt.material")))
                    .name(hotbar.getString("butt.name"))
                    .setAmount(hotbar.getInt("butt.amount"))
                    .lore(hotbar.getStringList("butt.lore"))
                    .build();
            player.getInventory().setItem(hotbar.getInt("butt.slot"), enderbutt);
        }

        HidePlayersListener.hideJoin(player);
        if (toggle.getBoolean("normal.join-speed")) {
            player.setWalkSpeed((float) config.getDouble("join-speed.velocity"));
        } else {
            player.setWalkSpeed(0.2F);
        }
        player.updateInventory();
        //Teleport to spawn when disabled
        player.teleport(player.getWorld().getSpawnLocation());
        player.sendMessage(CC.translate(messages.getString("pvp-mode.disabled")));
    }

    public static void enablePvPMode(Player player) {
        ItemStack[] contents = ((List<ItemStack>)SharkHub.getInstance().getPvpmodeConfig().getConfiguration().get("inventory")).stream().toArray(ItemStack[]::new) ;
        ItemStack[] armor = ((List<ItemStack>)SharkHub.getInstance().getPvpmodeConfig().getConfiguration().get("armor")).stream().toArray(ItemStack[]::new) ;
        /* Enabled */
        inPvPMode.put(player.getUniqueId(), System.currentTimeMillis());
        kills.put(player.getUniqueId(), 0);
        player.setWalkSpeed(0.2F);
        player.setAllowFlight(false);
        //4*9=36
        player.getInventory().setContents(new ItemStack[36]);
        player.getInventory().setArmorContents(null);
        player.updateInventory();
        player.getInventory().setContents(contents);
        player.getInventory().setArmorContents(armor);
        player.updateInventory();
        player.sendMessage(CC.translate(messages.getString("pvp-mode.activated")));
    }
}