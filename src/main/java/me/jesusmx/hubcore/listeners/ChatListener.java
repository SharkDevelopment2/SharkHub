package me.jesusmx.hubcore.listeners;

import com.lunarclient.bukkitapi.LunarClientAPI;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.ServerUtil;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final ConfigFile config = SharkHub.getInstance().getMainConfig();

    private boolean isLunarClient(Player player) {
        return LunarClientAPI.getInstance().isRunningLunarClient(player);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (config.getBoolean("CHAT_FORMAT.MUTED.ENABLE")) {
            player.sendMessage(CC.translate(config.getString("CHAT_FORMAT.MUTED.MESSAGE")));
            event.setCancelled(true);
        }

        if (config.getBoolean("CHAT_FORMAT.NORMAL.ENABLE")) {
            String path = config.getString("CHAT_FORMAT.NORMAL.MESSAGE");
            String playerName = player.getName();
            String prefix = SharkHub.getInstance().getRankManager().getRank().getPrefix(player.getUniqueId());
            String replace = path.replaceAll("%PLAYER%", playerName).replaceAll("%RANK%", prefix).replace("%MESSAGE%", event.getMessage());

            event.setMessage(player.hasPermission("chub.vip") ? CC.translate(event.getMessage()) : event.getMessage());

            if (Bukkit.getPluginManager().getPlugin("LunarClient-API") != null && Bukkit.getPluginManager().getPlugin("LunarClient-API").isEnabled()) {
                if (event.getFormat().contains("%LUNAR%") && isLunarClient(player)) {
                    String lunarPath = CC.translate(config.getString("CHAT_FORMAT.FORMAT-LUNAR"));
                    event.setFormat(ServerUtil.replaceText(player, lunarPath + replace));
                    return;
                }
            }
            event.setFormat(ServerUtil.replaceText(player, replace));
        }
    }
}
