package me.jesusmx.hubcore.commands.spawn;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SpawnCommand extends Command {

    private ConfigFile settings = SharkHub.getInstance().getSettingsConfig();

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
                Location location = player.getLocation();
                float yaw = settings.getInt("world.spawn.yaw");
                float pitch = settings.getInt("world.spawn.pitch");
                double x = settings.getDouble("world.spawn.x");
                double y = settings.getDouble("world.spawn.y");
                double z = settings.getDouble("world.spawn.z");
                location.setYaw(yaw);
                location.setPitch(pitch);
                location.setY(y);
                location.setX(x);
                location.setZ(z);
                player.teleport(location);
                return true;
            }
        }
    }
}
