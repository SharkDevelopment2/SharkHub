package es.hulk.hub.commands.features.pvpmode;

import es.hulk.hub.SharkHub;
import es.hulk.hub.pvpmode.PvPModeHandler;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.command.BaseCommand;
import es.hulk.hub.util.command.Command;
import es.hulk.hub.util.command.CommandArgs;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.entity.Player;

public class PvPModeCommand extends BaseCommand {

    private final ConfigFile config = SharkHub.getInstance().getPvpmodeConfig();

    @Command(name = "pvpmode", permission = "sharkhub.command.pvpmode", aliases = {"pvp", "pvp-mode"}, inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length > 0 && player.isOp()) {
            if (args[0].equalsIgnoreCase("setinv")) {
                config.getConfiguration().set("INVENTORY", player.getInventory().getContents());
                config.getConfiguration().set("ARMOR", player.getInventory().getArmorContents());
                config.save();
                config.reload();
                player.sendMessage(CC.translate("&aInventory saved."));
            }
        } else {
            PvPModeHandler.togglePvPMode(player);
        }
    }
}