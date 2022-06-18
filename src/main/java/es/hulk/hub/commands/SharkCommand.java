package es.hulk.hub.commands;

import es.hulk.hub.SharkHub;
import es.hulk.hub.hotbar.HotbarManager;
import es.hulk.hub.menus.hub.HubManager;
import es.hulk.hub.menus.server.ServerManager;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.bukkit.SharkLicenses;
import es.hulk.hub.util.command.BaseCommand;
import es.hulk.hub.util.command.Command;
import es.hulk.hub.util.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SharkCommand extends BaseCommand {

    @Command(name = "sharkhub")
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        if (args.length == 0) {
            sender.sendMessage(CC.translate(""));
            sender.sendMessage(CC.translate("&bSharkHub &7- &f2.0"));
            sender.sendMessage(CC.translate(""));
            sender.sendMessage(CC.translate("&7/sharkhub reload"));
            sender.sendMessage(CC.translate("&7/sharkhub version"));
            sender.sendMessage(CC.translate(""));
            if (player.getUniqueId().equals(UUID.fromString("b0c1d4f4-0fd1-4a93-ab5e-88b1ff885c29"))) {
                player.sendMessage(CC.translate("&7/sharkhub info"));
            }
            return;
        }

        if (args[0].equalsIgnoreCase("info")) {
            if (player.getUniqueId().equals(UUID.fromString("b0c1d4f4-0fd1-4a93-ab5e-88b1ff885c29"))) {
                player.sendMessage(" ");
                player.sendMessage(CC.translate("&bThis server is using SharkHub"));
                player.sendMessage(CC.translate("&fVersion&7: &f" + SharkHub.getInstance().getDescription().getVersion()));
                player.sendMessage(CC.translate("&fLicense is: &b" + SharkLicenses.productKey));
                player.sendMessage(" ");
                player.sendMessage(CC.translate("&eUser's id:&b %%__USER__%%"));
                player.sendMessage(CC.translate("&eUser's name:&b %%__USERNAME__%%"));
                player.sendMessage(CC.translate("&eResource id:&b %%__RESOURCE__%%"));
                player.sendMessage(CC.translate("&eResource version:&b %%__VERSION__%%"));
                player.sendMessage(CC.translate("&eDownload timestamp:&b %%__TIMESTAMP__%%"));
                player.sendMessage(CC.translate("&eDownload file hash:&b %%__FILEHASH__%%"));
                player.sendMessage(CC.translate("&eDownload numerical representation:&b %%__NONCE__%%"));
                player.sendMessage(" ");
            }
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
                SharkHub.getInstance().getServerManager().load();
                SharkHub.getInstance().getHubManager().load();

                for (Player online : Bukkit.getOnlinePlayers()) {
                    HotbarManager.setHotbarItems(online);
                }

                sender.sendMessage(CC.translate("&aSuccessfully all SharkHub files reloaded!"));
            }
        }
    }
}
