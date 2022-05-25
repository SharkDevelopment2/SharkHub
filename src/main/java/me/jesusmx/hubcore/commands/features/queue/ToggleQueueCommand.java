package me.jesusmx.hubcore.commands.features.queue;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.managers.queue.custom.QueueData;
import me.jesusmx.hubcore.managers.queue.custom.QueueHandler;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;


public class ToggleQueueCommand extends Command {

   private final ConfigFile config = SharkHub.getInstance().getQueueConfig();

   public ToggleQueueCommand() {
      super("ToggleQueue");
      this.setUsage(CC.translate("&cUsage: /togglequeue <queue>"));
      this.setAliases(Arrays.asList("tq", "queuetoggle", "togglequeue"));
   }

   public boolean execute(CommandSender sender, String label, String[] args) {
      if (!sender.hasPermission("hubcore.queue.pause")) {
         sender.sendMessage(CC.translate("&cYou don't have permissions."));
         return true;
      }

      if (args.length == 0) {
         this.getUsage();

      } else {
         String server = args[0];
         if (QueueHandler.getQueue(server) == null) {
            sender.sendMessage(CC.translate("&cQueue named " + server + " doesn't exists!"));
            return false;
         }

         QueueData queue = QueueHandler.getQueue(server);
         if (queue == null) return false;

         if (queue.isPaused()) {
            sender.sendMessage(CC.translate(config.getString("QUEUE.UN_PAUSED"))
                    .replaceAll("%SERVER%", queue.getServer()));
         } else {
            sender.sendMessage(CC.translate(config.getString("QUEUE.PAUSED"))
                    .replaceAll("%SERVER%", queue.getServer()));
         }

         queue.setPaused(!queue.isPaused());
      }
      return false;
   }
}
