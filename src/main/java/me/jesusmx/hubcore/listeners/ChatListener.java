package me.jesusmx.hubcore.listeners;

import com.lunarclient.bukkitapi.LunarClientAPI;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final ConfigFile config = SharkHub.getInstance().getMainConfig();
    private final ConfigFile toggle = SharkHub.getInstance().getTogglesConfig();

    private boolean isLunarClient(Player player) {
        return LunarClientAPI.getInstance().isRunningLunarClient(player);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (toggle.getBoolean("normal.chat.muted")) {
            if(event.getPlayer().hasPermission("hub.chat-bypass")) return;
            event.getPlayer().sendMessage(CC.translate(config.getString("chat-format.muted.message")));
            event.setCancelled(true);
        } else if (toggle.getBoolean("normal.chat.normal")) {
            String msg = ChatColor.translateAlternateColorCodes('&',
                    config.getString("chat-format.normal.message")
                            .replace("%player%", player.getName())
                            .replace("%message%", event.getMessage())
                            .replace("%suffix%", SharkHub.chat.getPlayerSuffix(player))
                            .replace("%prefix%", SharkHub.chat.getPlayerPrefix(player)));

            if(Bukkit.getPluginManager().getPlugin("LunarClient-API") != null && Bukkit.getPluginManager().getPlugin("LunarClient-API").isEnabled()) {
                msg = ChatColor.translateAlternateColorCodes('&',
                        config.getString("chat-format.normal.message")
                                .replace("%player%", player.getName())
                                .replace("%lunar%", CC.translate(isLunarClient(player) ? config.getString("chat-format.normal.prefix-lunar") : ""))
                                .replace("%message%", event.getMessage())
                                .replace("%suffix%", SharkHub.chat.getPlayerSuffix(player))
                                .replace("%prefix%", SharkHub.chat.getPlayerPrefix(player)));
            }
            event.setFormat(msg);
        }
    }
}
