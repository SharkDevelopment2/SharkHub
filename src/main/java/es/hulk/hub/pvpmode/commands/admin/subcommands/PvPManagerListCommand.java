package es.hulk.hub.pvpmode.commands.admin.subcommands;

import es.hulk.hub.SharkHub;
import es.hulk.hub.pvpmode.PvPMode;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.command.BaseCommand;
import es.hulk.hub.util.command.Command;
import es.hulk.hub.util.command.CommandArgs;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class PvPManagerListCommand extends BaseCommand {

    private final SharkHub plugin;

    public PvPManagerListCommand(SharkHub plugin) {
        this.plugin = plugin;
    }

    @Command(name = "pvpmanager.list", aliases = "pvpm.list", permission = "sharkhub.command.pvpmanager")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        player.sendMessage(CC.translate(Arrays.toString(plugin.getPvpmodeManager().getPvpmodes().toArray())));
    }
}
