package es.hulk.hub.commands.spawn;

import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.command.BaseCommand;
import es.hulk.hub.util.command.Command;
import es.hulk.hub.util.command.CommandArgs;
import org.bukkit.entity.Player;

public class SetSpawnCommand extends BaseCommand {

    @Command(name = "setspawn", permission = "hubcore.command.setspawn")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        SharkHub.getInstance().getSpawnManager().setLocation(player.getLocation());
        SharkHub.getInstance().getSpawnManager().saveLocation();
        player.sendMessage(CC.translate("&aSuccessfully set the spawn point."));
    }
}