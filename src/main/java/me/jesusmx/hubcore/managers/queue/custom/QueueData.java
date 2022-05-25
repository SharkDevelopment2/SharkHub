package me.jesusmx.hubcore.managers.queue.custom;

import lombok.Getter;
import lombok.Setter;
import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.bungee.BungeeUtils;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes", "UnstableApiUsage"})
@Getter
@Setter
public class QueueData {
   public static QueueData instance;
   private final ConfigFile config = SharkHub.getInstance().getQueueConfig();
   private String server;
   private List<Player> players = new ArrayList();
   private boolean paused;
   private int limit;

   public QueueData(final String server) {
      this.server = server;
      this.paused = false;
      this.limit = 1000;

      (new BukkitRunnable() {
         public void run() {
            for(Player player : players) {
               if(player.isOnline()) {
                  CC.translate(config.getStringList("QUEUE.IN_QUEUE"))
                          .stream()
                          .map(s -> s.replace("%POSITION%", String.valueOf(players.indexOf(player) + 1)))
                          .map(s -> s.replace("%SERVER%", server))
                          .forEach(player::sendMessage);
               } else {
                  players.remove(player);
               }
            }
         }
      }).runTaskTimer(SharkHub.getInstance(), 300L, 300L);
   }

   public int getPosition(Player player) {
      return players.indexOf(player) + 1;
   }

   public void addEntry(Player player) {
      if (player != null) {
         if (!this.players.contains(player)) {
            if (QueueHandler.getPriority(player) == 0) {
               this.sendDirect(player);
               player.sendMessage(CC.translate("&aYou have been sent to " + this.server + "."));
            } else {
               this.players.add(player);
               for(Player user : this.players) {
                  int pos = this.players.indexOf(user);
                  if(user != player && QueueHandler.getPriority(player) < QueueHandler.getPriority(user)) {
                     if(this.players.get(pos).isOnline()) {
                        this.players.get(pos).sendMessage(CC.translate(config.getString("QUEUE.HIGHER")));
                     }
                     Collections.swap(this.players, pos, this.players.size() - 1);
                  }
               }
            }
         }
      }
   }

   public void removeEntry(Player player) {
      this.players.remove(player);
   }

   public Player getPlayerAt(int player) {
      return this.players.get(player);
   }

   public void sendFirst() {
      if (!this.players.isEmpty()) {
         Player player = this.players.get(0);
         BungeeUtils.sendToServer(player, server);
      }
   }

   public void sendDirect(Player player) {
      BungeeUtils.sendToServer(player, server);
   }

   public static QueueData getInstance() {
      return instance;
   }
}
