package es.hulk.hub.commands.features.other;

import es.hulk.hub.menus.subselector.SubSelectorMenu;
import es.hulk.hub.util.command.BaseCommand;
import es.hulk.hub.util.command.Command;
import es.hulk.hub.util.command.CommandArgs;
import org.bukkit.entity.Player;

public class OpenSubServerSelectorCommand extends BaseCommand {

    @Command(name = "opensubserverselector")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage("§cUsage: /opensubserverselector <server>");
            return;
        }

        String server = args[0];
        new SubSelectorMenu(server).openMenu(player);
    }
}
