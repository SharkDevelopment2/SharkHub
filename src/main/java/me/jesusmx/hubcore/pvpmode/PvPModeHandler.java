package me.jesusmx.hubcore.pvpmode;

import lombok.Getter;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.hotbar.HotbarManager;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PvPModeHandler {

    private static Map<UUID, Long> inPvPMode;
    @Getter private static Map<UUID, Integer> kills;
    private static final ConfigFile messages = SharkHub.getInstance().getMessagesConfig();

    public static void init() {
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
        if(SharkHub.getInstance().getPvpmodeConfig().getConfiguration().get("INVENTORY") == null || SharkHub.getInstance().getPvpmodeConfig().getConfiguration().get("ARMOR") == null) {
            player.sendMessage(CC.translate(messages.getString("PVP_MODE.NOT_CONFIGURED")));
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
        player.getActivePotionEffects().clear();
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        HotbarManager.setHotbarItems(player);

        if (config.getBoolean("JOIN_PLAYER.SPEED.ENABLE")) {
            player.setWalkSpeed(Float.parseFloat(config.getString("JOIN_PLAYER.SPEED.VALUE")));
            player.setFlySpeed(Float.parseFloat(config.getString("JOIN_PLAYER.SPEED.VALUE")));
        }

        player.setAllowFlight(true);
        player.updateInventory();
        SharkHub.getInstance().getSpawnManager().sendToSpawn(player);
        player.sendMessage(CC.translate(messages.getString("PVP_MODE.DISABLED")));
    }

    public static void enablePvPMode(Player player) {
        ItemStack[] contents = ((List<ItemStack>)SharkHub.getInstance().getPvpmodeConfig().getConfiguration().get("INVENTORY")).stream().toArray(ItemStack[]::new) ;
        ItemStack[] armor = ((List<ItemStack>)SharkHub.getInstance().getPvpmodeConfig().getConfiguration().get("ARMOR")).stream().toArray(ItemStack[]::new) ;
        /* Enabled */
        inPvPMode.put(player.getUniqueId(), System.currentTimeMillis());
        kills.put(player.getUniqueId(), 0);
        player.setWalkSpeed(0.2F);
        player.setAllowFlight(false);
        player.getInventory().setContents(new ItemStack[36]);
        player.getInventory().setArmorContents(null);
        player.updateInventory();
        player.getInventory().setContents(contents);
        player.getInventory().setArmorContents(armor);
        player.updateInventory();
        player.sendMessage(CC.translate(messages.getString("PVP_MODE.ACTIVATED")));
    }
}