package es.hulk.hub.commands.spawn;

import es.hulk.hub.SharkHub;
import es.hulk.hub.util.command.BaseCommand;
import es.hulk.hub.util.command.Command;
import es.hulk.hub.util.command.CommandArgs;
import org.bukkit.entity.Player;

public class SpawnCommand extends BaseCommand {

    @Command(name = "spawn")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        SharkHub.getInstance().getSpawnManager().sendToSpawn(player);
    }
}
