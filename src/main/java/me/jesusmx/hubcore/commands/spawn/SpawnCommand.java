package me.jesusmx.hubcore.commands.spawn;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SpawnCommand extends Command {

    public SpawnCommand() {
        super("SpawnCommand");
        this.setAliases(Arrays.asList("spawn"));
        this.setUsage(ChatColor.RED + "&cUsage: /spawn");
    }

    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(CC.translate("&cThis is for player use only!"));
            return false;

        } else {
            Player player = (Player) commandSender;
            if (!player.hasPermission("hubcore.command.spawn")) {
                player.sendMessage(CC.translate("&cYou dont have permission to use this command."));
                return false;
            } else {
                SharkHub.getInstance().getSpawnManager().sendToSpawn(player);
                return true;
            }
        }
    }
}
