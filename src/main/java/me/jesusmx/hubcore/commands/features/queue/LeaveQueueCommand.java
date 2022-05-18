package me.jesusmx.hubcore.commands.features.queue;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.hooks.queue.custom.QueueHandler;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class LeaveQueueCommand extends Command {

   private final ConfigFile config = SharkHub.getInstance().getQueueConfig();

   public LeaveQueueCommand() {
      super("LeaveQueue");
      this.setAliases(Arrays.asList("leavequeue", "lq", "queueleave"));
   }

   public boolean execute(CommandSender sender, String label, String[] args) {
      if(!(sender instanceof Player)) {
         sender.sendMessage(CC.translate("&cOnly Players"));
         return false;
      }
      Player player = (Player)sender;
      if (QueueHandler.getQueue(player) == null) {
         player.sendMessage(CC.translate("&cYou aren't in the queue!"));
      } else {
         player.sendMessage(CC.translate(config.getString("queue.leave"))
                 .replaceAll("%server%", QueueHandler.getQueueName(player)));
         QueueHandler.getQueue(player).removeEntry(player);
      }
      return false;
   }
}
