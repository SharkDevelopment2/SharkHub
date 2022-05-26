package es.hulk.hub.cosmetics.base.command;

import es.hulk.hub.cosmetics.base.menu.CosmeticsMenu;
import es.hulk.hub.util.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CosmeticsCommand extends Command {

    public CosmeticsCommand() {
        super("cosmetics");
        this.setAliases(Arrays.asList("cos", "cosmetic", "cosmetics"));
        this.usageMessage = "/cosmetics";
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(CC.translate("&cOnly Players"));
            return false;
        }
        new CosmeticsMenu().openMenu((Player) sender);
        return false;
    }
}
