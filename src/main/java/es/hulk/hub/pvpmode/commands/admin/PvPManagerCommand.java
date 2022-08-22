package es.hulk.hub.pvpmode.commands.admin;

import es.hulk.hub.SharkHub;
import es.hulk.hub.pvpmode.commands.admin.subcommands.PvPManagerListCommand;
import es.hulk.hub.util.command.BaseCommand;
import es.hulk.hub.util.command.Command;
import es.hulk.hub.util.command.CommandArgs;
import org.bukkit.entity.Player;

public class PvPManagerCommand extends BaseCommand {

    public PvPManagerCommand(SharkHub plugin) {
        new PvPManagerListCommand(plugin);
    }

    @Command(name = "pvpmanager", aliases = {"pvpm", "pvpmanager.help", "pvpm.help"}, permission = "sharkhub.command.pvpmanager")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        player.sendMessage("");
        player.sendMessage("/" + command.getLabel() + " setContent <type>");
        player.sendMessage("/" + command.getLabel() + " setLocation <type>");
        player.sendMessage("/" + command.getLabel() + " list");
        player.sendMessage("/" + command.getLabel() + " listPlayers");
        player.sendMessage("");
    }
}
