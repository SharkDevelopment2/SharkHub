package me.jesusmx.hubcore.commands;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.hotbar.HotbarManager;
import me.jesusmx.hubcore.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SharkCommand extends Command {

    public SharkCommand() {
        super("sharkhub");
        this.setUsage(CC.translate("&cUsage: /sharkhub reload"));
    }

    public boolean execute(CommandSender sender, String string, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(CC.translate(""));
            sender.sendMessage(CC.translate("&b&lSharkHub &7- &f2.0"));
            sender.sendMessage(CC.translate(""));
            sender.sendMessage(CC.translate("/sharkhub reload"));
            sender.sendMessage(CC.translate("/sharkhub version"));
            sender.sendMessage(CC.translate(""));
            return false;
        }

        if (args[0].equalsIgnoreCase("version")) {
            sender.sendMessage("&bSharkHub");
            sender.sendMessage("");
            sender.sendMessage("Version: " + SharkHub.getInstance().getDescription().getVersion());
            sender.sendMessage("Author: " + SharkHub.getInstance().getDescription().getAuthors());
            sender.sendMessage("");
            return false;
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
                //SharkHub.getInstance().getParticlesConfig().reload();

                SharkHub.getInstance().getHotbarManager().load();
                for (Player online : Bukkit.getOnlinePlayers()) {
                    HotbarManager.setHotbarItems(online);
                }

                sender.sendMessage(CC.translate("&aSuccessfully all SharkHub files reloaded!"));
            }
        }
        return true;
    }
}
