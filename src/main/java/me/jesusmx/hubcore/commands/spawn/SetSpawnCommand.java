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
         SharkHub.getInstance().getSpawnManager().setLocation(player.getLocation());
         player.sendMessage(CC.translate("&aSuccessfully set the spawn point."));
      }
      return false;
   }
}