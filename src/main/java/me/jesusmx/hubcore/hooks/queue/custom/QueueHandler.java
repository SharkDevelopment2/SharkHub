package me.jesusmx.hubcore.hooks.queue.custom;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.bungee.BungeeUtils;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"unchecked", "rawtypes"})
public class QueueHandler {

   private final ConfigFile config = SharkHub.getInstance().getQueueConfig();
   public static List<QueueData> queues = new ArrayList();

   public QueueHandler() {
      for(String queue : config.getStringList("QUEUE.SERVERS")) {
         queues.add(new QueueData(queue));
      }
      (new BukkitRunnable() {
         public void run() {
            for(QueueData queue : QueueHandler.queues) {
               if (!queue.isPaused() && !queue.getPlayers().isEmpty() && QueueHandler.this.playerCount() < queue.getLimit()) {
                  queue.sendFirst();
                  queue.removeEntry(queue.getPlayerAt(0));
               }
            }

         }
      }).runTaskTimerAsynchronously(SharkHub.getInstance(), 30L, 30L);
   }

   public int playerCount() {
      for(QueueData queue : queues) {
         for(String queues : config.getStringList("QUEUE.SERVERS")) {
            if (queue.getServer().equalsIgnoreCase(queues)) {
               return BungeeUtils.getServersByName().get(queues).getPlayerCount();
            }
         }
      }
      return 0;
   }

   public static QueueData getQueue(Player player) {
      List<QueueData> queue = queues.stream().filter(q -> q.getPlayers().contains(player)).collect(Collectors.toList());
      if(queue.isEmpty()) return null;
      return queue.get(0);
   }

   public static QueueData getQueue(String server) {
      List<QueueData> queue = queues.stream().filter(q -> q.getServer().equalsIgnoreCase(server)).collect(Collectors.toList());
      if (queue.isEmpty()) {
         return null;
      } else {
         return queue.get(0);
      }
   }

   public static String getQueueName(Player player) {
      return getQueue(player).getServer();
   }

   public static int getPriority(Player player) {
      for(int i = 0; i < 10; i++) {
         if(player.hasPermission("QUEUE.PRIORITY." + i)) {
            return i;
         }
      }
      return 0;
   }

}
