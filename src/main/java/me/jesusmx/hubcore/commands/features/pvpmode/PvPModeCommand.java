package me.jesusmx.hubcore.commands.features.pvpmode;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.pvpmode.cache.PvPModeHandler;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PvPModeCommand extends Command {

    private final ConfigFile config = SharkHub.getInstance().getPvpmodeConfig();

    public PvPModeCommand() {
        super("PvPMode");
        this.setUsage(CC.translate("&cUsage: /pvpmode setinv"));
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(CC.translate("&cOnly Players."));
            return false;
        }

        if (!sender.hasPermission("hubcore.pvpmode.setinv")) {
            sender.sendMessage(CC.translate("&cYou don't have permissions."));
            return true;
        }

        Player player = (Player) sender;
        if(args.length > 0 && player.isOp()) {
            if(args[0].equalsIgnoreCase("setinv")) {
                config.getConfiguration().set("INVENTORY", player.getInventory().getContents());
                config.getConfiguration().set("ARMOR", player.getInventory().getArmorContents());
                config.save();
                config.reload();
                player.sendMessage(getUsage());
            }
        } else {
            PvPModeHandler.togglePvPMode(player);
        }
        return true;
    }
}