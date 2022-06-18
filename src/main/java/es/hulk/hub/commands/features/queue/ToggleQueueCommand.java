package es.hulk.hub.commands.features.queue;

import es.hulk.hub.SharkHub;
import es.hulk.hub.managers.queue.custom.QueueData;
import es.hulk.hub.managers.queue.custom.QueueHandler;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.command.BaseCommand;
import es.hulk.hub.util.command.Command;
import es.hulk.hub.util.command.CommandArgs;
import es.hulk.hub.util.command.CommandManager;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;


public class ToggleQueueCommand extends BaseCommand {

    private final ConfigFile config = SharkHub.getInstance().getQueueConfig();

    @Command(name = "togglequeue", permission = "hub.command.togglequeue")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.translate("&c/togglequeue <server>"));
        } else {
            String server = args[0];
            if (QueueHandler.getQueue(server) == null) {
                player.sendMessage(CC.translate("&cQueue named " + server + " doesn't exists!"));
            }

            QueueData queue = QueueHandler.getQueue(server);
            if (queue == null) return;

            if (queue.isPaused()) {
                player.sendMessage(CC.translate(config.getString("QUEUE.UN_PAUSED"))
                        .replaceAll("%server%", queue.getServer()));
            } else {
                player.sendMessage(CC.translate(config.getString("QUEUE.PAUSED"))
                        .replaceAll("%server%", queue.getServer()));
            }

            queue.setPaused(!queue.isPaused());
        }
    }
}
