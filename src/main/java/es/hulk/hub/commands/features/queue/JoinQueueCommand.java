package es.hulk.hub.commands.features.queue;

import es.hulk.hub.SharkHub;
import es.hulk.hub.managers.queue.custom.QueueData;
import es.hulk.hub.managers.queue.custom.QueueHandler;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.command.BaseCommand;
import es.hulk.hub.util.command.Command;
import es.hulk.hub.util.command.CommandArgs;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.entity.Player;

public class JoinQueueCommand extends BaseCommand {

    private final ConfigFile config = SharkHub.getInstance().getQueueConfig();

    @Command(name = "joinqueue")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length != 1) {
            player.sendMessage(CC.translate("&c/joinqueue <server>"));
        } else {
            QueueData queue = QueueHandler.getQueue(args[0]);
            if (queue == null) {
                player.sendMessage(CC.translate("&cThe queue " + args[0] + " not exists"));
            } else {
                if (queue.isPaused()) {
                    player.sendMessage(CC.translate(config.getString("QUEUE.PAUSED")
                            .replace("%server%", queue.getServer())));
                } else {
                    queue.addEntry(player);
                }
            }
        }
    }
}
