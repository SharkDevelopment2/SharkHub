package es.hulk.hub.commands.features.queue;

import es.hulk.hub.SharkHub;
import es.hulk.hub.managers.queue.custom.QueueData;
import es.hulk.hub.managers.queue.custom.QueueHandler;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class JoinQueueCommand extends Command {

	private final ConfigFile config = SharkHub.getInstance().getQueueConfig();

	public JoinQueueCommand() {
		super("JoinQueue");
		this.setAliases(Arrays.asList("join", "queuejoin", "joinqueue", "joinq", "play"));
		this.setUsage(CC.translate("&cUsage: /joinqueue <server>"));
	}

	public boolean execute(CommandSender sender, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(CC.translate("&cOnly players.."));
			return false;
		}
		Player player = (Player) sender;
		if(args.length != 1) {
			sender.sendMessage(this.usageMessage);
		} else {
			QueueData queue = QueueHandler.getQueue(args[0]);
			if(queue == null) {
				player.sendMessage(CC.translate("&cThe queue " + args[0] + " not exists"));
			} else {
				if(queue.isPaused()) {
					player.sendMessage(CC.translate(config.getString("QUEUE.PAUSED")
							.replace("%SERVER%", queue.getServer())));
				} else {
					queue.addEntry(player);
				}
			}
		}
		return true;
	}
}
