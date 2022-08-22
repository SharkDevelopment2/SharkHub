package es.hulk.hub.pvpmode.commands.user;

import es.hulk.hub.SharkHub;
import es.hulk.hub.pvpmode.menu.PvPMenu;
import es.hulk.hub.util.command.BaseCommand;
import es.hulk.hub.util.command.Command;
import es.hulk.hub.util.command.CommandArgs;

public class PvPMenuCommand extends BaseCommand {

    private final SharkHub plugin;

    public PvPMenuCommand(SharkHub plugin) {
        this.plugin = plugin;
    }

    @Command(name = "pvpmenu")
    @Override
    public void onCommand(CommandArgs command) {
        new PvPMenu(plugin).openMenu(command.getPlayer());
    }
}
