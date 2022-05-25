package me.jesusmx.hubcore.managers.queue.custom.listener;

import me.jesusmx.hubcore.managers.queue.custom.QueueData;
import me.jesusmx.hubcore.managers.queue.custom.QueueHandler;
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
