package es.hulk.hub.commands;

import es.hulk.hub.SharkHub;
import es.hulk.hub.hotbar.HotbarManager;
import es.hulk.hub.menus.hub.HubManager;
import es.hulk.hub.menus.server.ServerManager;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.command.BaseCommand;
import es.hulk.hub.util.command.Command;
import es.hulk.hub.util.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SharkCommand extends BaseCommand {

    @Command(name = "sharkhub")
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();
        if (args.length == 0) {
            sender.sendMessage(CC.translate(""));
            sender.sendMessage(CC.translate("&bSharkHub &7- &f2.0"));
            sender.sendMessage(CC.translate(""));
            sender.sendMessage(CC.translate("&7/sharkhub reload"));
            sender.sendMessage(CC.translate("&7/sharkhub version"));
            sender.sendMessage(CC.translate(""));
            return;
        }

        if (args[0].equalsIgnoreCase("version")) {
            sender.sendMessage(CC.translate("&bSharkHub"));
            sender.sendMessage(CC.translate(""));
            sender.sendMessage(CC.translate("&eVersion&7: &a" + SharkHub.getInstance().getDescription().getVersion()));
            sender.sendMessage(CC.translate("&eAuthor&7: &a" + SharkHub.getInstance().getDescription().getAuthors()));
            sender.sendMessage(CC.translate(""));
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("sharkhub.reload")) {
                SharkHub.getInstance().getMainConfig().reload();

                SharkHub.getInstance().getSelectorConfig().reload();
                SharkHub.getInstance().getSubselectorConfig().reload();
                SharkHub.getInstance().getHubselectorConfig().reload();

                SharkHub.getInstance().getQueueConfig().reload();
                SharkHub.getInstance().getScoreboardConfig().reload();
                SharkHub.getInstance().getTablistConfig().reload();
                SharkHub.getInstance().getPvpmodeConfig().reload();
                SharkHub.getInstance().getMessagesConfig().reload();
                SharkHub.getInstance().getHcfConfig().reload();

                SharkHub.getInstance().getCosmeticsConfig().reload();
                SharkHub.getInstance().getArmorsConfig().reload();
                SharkHub.getInstance().getHatsConfig().reload();
                SharkHub.getInstance().getGadgetsConfig().reload();
                SharkHub.getInstance().getHotbarConfig().reload();

                SharkHub.getInstance().getHotbarManager().load();
                ServerManager.load();
                HubManager.load();

                for (Player online : Bukkit.getOnlinePlayers()) {
                    HotbarManager.setHotbarItems(online);
                }

                sender.sendMessage(CC.translate("&aSuccessfully all SharkHub files reloaded!"));
            }
        }
    }
}
