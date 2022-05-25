package me.jesusmx.hubcore.commands.others;

import com.cryptomorin.xseries.XMaterial;
import me.jesusmx.hubcore.util.CC;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullCommand extends Command {

    public SkullCommand() {
        super("Skull");
        this.setUsage(CC.translate("&cUsage: /skull <player>"));
    }

    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate("&cOnly Players."));
            return false;
        }

        if (!sender.hasPermission("hubcore.command.skull")) {
            sender.sendMessage(CC.translate("&cYou don't have permissions."));
            return true;
        }

        Player player = (Player) sender;
        if (args.length != 1) {
            sender.sendMessage(usageMessage);
            return true;
        }

        player.getInventory().addItem(playerSkullForName(args[0]));
        player.sendMessage(ChatColor.GREEN + "You have received " + args[0] + "'s head.");
        return false;
    }

    private ItemStack playerSkullForName(String name) {
        assert XMaterial.CREEPER_HEAD.parseMaterial() != null;
        ItemStack is = new ItemStack(XMaterial.CREEPER_HEAD.parseMaterial(), 1);
        is.setDurability((short) 3);
        SkullMeta meta = (SkullMeta) is.getItemMeta();
        meta.setOwner(name);
        is.setItemMeta(meta);
        return is;
    }
}
