package me.jesusmx.hubcore.cosmetics.base.command;

import me.jesusmx.hubcore.cosmetics.base.menu.CosmeticsMenu;
import me.jesusmx.hubcore.util.CC;
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
