package es.hulk.hub.listeners;

import com.lunarclient.bukkitapi.LunarClientAPI;
import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    public ChatListener() {
        Bukkit.getPluginManager().registerEvents(this, SharkHub.getInstance());
    }

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

            event.setMessage(player.hasPermission("sharkhub.vip") || player.isOp() ? CC.translate(event.getMessage()) : event.getMessage());
            String replace = path.replaceAll("%PLAYER%", playerName).replaceAll("%RANK%", prefix).replace("%MESSAGE%", event.getMessage());
            replace = replace.replace('§', '&');

            if (Bukkit.getPluginManager().getPlugin("LunarClient-API") != null && Bukkit.getPluginManager().getPlugin("LunarClient-API").isEnabled()) {
                if (event.getFormat().contains("%LUNAR%") && isLunarClient(player)) {
                    String lunarPath = CC.translate(config.getString("CHAT_FORMAT.FORMAT-LUNAR"));
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        onlinePlayer.sendMessage(CC.translate(ServerUtil.replaceText(player, lunarPath + replace)));
                        event.setCancelled(true);
                    }
                    return;
                }
            }

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendMessage(CC.translate(player, replace, true));
                event.setCancelled(true);
            }
        }
    }
}
