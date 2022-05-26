package es.hulk.hub.commands.features.queue;

import es.hulk.hub.SharkHub;
import es.hulk.hub.managers.queue.custom.QueueHandler;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.files.ConfigFile;
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
         player.sendMessage(CC.translate(config.getString("QUEUE.LEAVE"))
                 .replaceAll("%SERVER%", QueueHandler.getQueueName(player)));
         QueueHandler.getQueue(player).removeEntry(player);
      }
      return false;
   }
}
