package es.hulk.hub.commands.features.queue;

import es.hulk.hub.SharkHub;
import es.hulk.hub.managers.queue.custom.QueueHandler;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.command.BaseCommand;
import es.hulk.hub.util.command.Command;
import es.hulk.hub.util.command.CommandArgs;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class LeaveQueueCommand extends BaseCommand {

    private final ConfigFile config = SharkHub.getInstance().getQueueConfig();

    @Command(name = "leavequeue")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        if (QueueHandler.getQueue(player) == null) {
            player.sendMessage(CC.translate("&cYou aren't in the queue!"));
        } else {
            player.sendMessage(CC.translate(config.getString("QUEUE.LEAVE"))
                    .replaceAll("%SERVER%", QueueHandler.getQueueName(player)));
            QueueHandler.getQueue(player).removeEntry(player);
        }
    }
}
