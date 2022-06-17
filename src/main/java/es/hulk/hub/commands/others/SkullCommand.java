package es.hulk.hub.commands.others;

import com.cryptomorin.xseries.XMaterial;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.command.BaseCommand;
import es.hulk.hub.util.command.Command;
import es.hulk.hub.util.command.CommandArgs;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullCommand extends BaseCommand {

    @Command(name = "skull", permission = "hubcore.command.skull")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length != 1) {
            player.sendMessage(CC.translate("&cUsage: /skull <player>"));
        }

        player.getInventory().addItem(playerSkullForName(args[0]));
        player.sendMessage(ChatColor.GREEN + "You have received " + args[0] + "'s head.");
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
