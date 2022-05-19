package me.jesusmx.hubcore.listeners.items;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.files.ConfigFile;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class HidePlayersListener implements Listener {

    public static ConfigFile toggle = SharkHub.getInstance().getTogglesConfig();
    private final ConfigFile messages = SharkHub.getInstance().getMessagesConfig();

    @EventHandler
    public void HideJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        new BukkitRunnable() {
            @Override
            public void run() {
                    hideJoin(player);
                }
        } .runTaskLater(SharkHub.getInstance(), 0);
    }

    @EventHandler
    public void playerJoinEvent(PlayerInteractEvent event) {
        if(!toggle.getBoolean("normal.hide-players.enabled")) return;
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getPlayer().getItemInHand().isSimilar(showItem())) {
                event.getPlayer().setMetadata("HIDE_ALL", new FixedMetadataValue(SharkHub.getInstance(), true));
                event.getPlayer().getInventory().setItem(SharkHub.getInstance().getHotbarConfig().getInt("hide-player.slot"), null);
                event.getPlayer().updateInventory();
                event.getPlayer().getInventory().setItem(SharkHub.getInstance().getHotbarConfig().getInt("hide-player.slot"), hideItem());
                event.getPlayer().updateInventory();
                SharkHub.getInstance().getOnlinePlayers().forEach(event.getPlayer()::hidePlayer);
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("player-visibility.hide-message")));
            }else if(event.getPlayer().getItemInHand().isSimilar(hideItem())) {
                event.getPlayer().removeMetadata("HIDE_ALL", SharkHub.getInstance());
                SharkHub.getInstance().getOnlinePlayers().forEach(event.getPlayer()::showPlayer);
                event.getPlayer().getInventory().setItem(SharkHub.getInstance().getHotbarConfig().getInt("hide-player.slot"), null);
                event.getPlayer().updateInventory();
                event.getPlayer().getInventory().setItem(SharkHub.getInstance().getHotbarConfig().getInt("hide-player.slot"), showItem());
                event.getPlayer().updateInventory();
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("player-visibility.show-message")));
            }
        }
    }

    public static ItemStack hideItem() {
        ItemStack itemStack = new ItemStack(Material.valueOf(SharkHub.getInstance().getHotbarConfig().getString("hide-player.hide.material")));
        itemStack.setDurability((short) SharkHub.getInstance().getHotbarConfig().getInt("hide-player.hide.data"));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', SharkHub.getInstance().getHotbarConfig().getString("hide-player.hide.name")));
        List<String> lore = new ArrayList<>();
        for(String loreString : SharkHub.getInstance().getHotbarConfig().getStringList("hide-player.hide.lore")){
            lore.add(ChatColor.translateAlternateColorCodes('&', loreString));
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack showItem(){
        ItemStack itemStack = new ItemStack(Material.valueOf(SharkHub.getInstance().getHotbarConfig().getString("hide-player.show.material")));
        itemStack.setDurability((short) SharkHub.getInstance().getHotbarConfig().getInt("hide-player.show.data"));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', SharkHub.getInstance().getHotbarConfig().getString("hide-player.show.name")));
        List<String> lore = new ArrayList<>();
        for(String loreString : SharkHub.getInstance().getHotbarConfig().getStringList("hide-player.show.lore")){
            lore.add(ChatColor.translateAlternateColorCodes('&', loreString));
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static void hideJoin(Player player) {
        if(!toggle.getBoolean("normal.hide-players.enabled")) return;
        if(!player.hasMetadata("HIDE_ALL")) {
            player.getInventory().setItem(SharkHub.getInstance().getHotbarConfig().getInt("hide-player.slot"), showItem());
        } else {
            player.getInventory().setItem(SharkHub.getInstance().getHotbarConfig().getInt("hide-player.slot"), hideItem());
        }
    }

    @EventHandler
    public void HideAll(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (SharkHub.getInstance().getTogglesConfig().getBoolean("world.all-hide-players")) {
            for (Player players : SharkHub.getInstance().getOnlinePlayers()) {
                player.hidePlayer(players);
                players.hidePlayer(player);
            }
        }
    }
}
