package me.jesusmx.hubcore.commands.spawn;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SetSpawnCommand extends Command {

   private final ConfigFile config = SharkHub.getInstance().getSettingsConfig();

   public SetSpawnCommand() {
      super("SetSpawnCommand");
      this.setAliases( Arrays.asList("setspawn"));
      this.setUsage(CC.translate("&cUsage: /setspawn"));
   }

   public boolean execute(CommandSender commandSender, String s, String[] strings) {
      Player player = (Player) commandSender;

      if (!(commandSender instanceof Player)) {
         commandSender.sendMessage(CC.translate("&cThis is for player use only!"));
         return false;
      }

      if (!player.hasPermission("hubcore.command.setspawn")) {
         player.sendMessage(CC.translate("&cYou dont have permission to use this command."));

      } else {
         Location l = player.getLocation();
         String world = l.getWorld().getName();
         double x = l.getX();
         double y = l.getY();
         double z = l.getZ();
         float yaw = l.getYaw();
         float pitch = l.getPitch();
         config.getConfiguration().set("world.spawn.world", world);
         config.getConfiguration().set("world.spawn.x", x);
         config.getConfiguration().set("world.spawn.y", y);
         config.getConfiguration().set("world.spawn.z", z);
         config.getConfiguration().set("world.spawn.yaw", yaw);
         config.getConfiguration().set("world.spawn.pitch", pitch);
         config.save();
         config.reload();
         player.sendMessage(CC.translate("&aSuccessfully set the spawn point."));
      }
      return false;
   }
}