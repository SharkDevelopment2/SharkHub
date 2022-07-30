package es.hulk.hub.managers.queue.custom.listener;

import es.hulk.hub.managers.queue.custom.QueueData;
import es.hulk.hub.managers.queue.custom.QueueHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QueueListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        for(QueueData queue : QueueHandler.queues) {
            if (queue.getPlayers().contains(player)) {
                queue.removeEntry(player);
            }
        }

    }
}
