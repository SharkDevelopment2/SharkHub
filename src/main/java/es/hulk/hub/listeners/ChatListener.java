package es.hulk.hub.listeners;

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

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (config.getBoolean("CHAT_FORMAT.MUTED.ENABLE")) {
            player.sendMessage(CC.translate(config.getString("CHAT_FORMAT.MUTED.MESSAGE")));
            event.setCancelled(true);
            return;
        }

        if (config.getBoolean("CHAT_FORMAT.NORMAL.ENABLE")) {
            String path = config.getString("CHAT_FORMAT.NORMAL.MESSAGE");
            String playerName = player.getName();
            String prefix = SharkHub.getInstance().getRankManager().getRank().getPrefix(player.getUniqueId());

            event.setMessage(player.hasPermission("sharkhub.vip") || player.isOp() ? CC.translate(event.getMessage()) : event.getMessage());

            if (event.getMessage().contains("%")) {
                event.setMessage(event.getMessage().replace("%", "%%"));
            }

            String replace = path.replaceAll("%player%", playerName).replaceAll("%prefix%", prefix).replace("%message%", event.getMessage());
            replace = replace.replace('§', '&');
            replace = ServerUtil.replaceText(player, replace);

            event.setFormat(CC.translate(player, replace, true));
        }
    }
}
