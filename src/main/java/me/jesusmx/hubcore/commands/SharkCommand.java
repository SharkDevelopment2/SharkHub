package me.jesusmx.hubcore.commands;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class SharkCommand extends Command {

    public SharkCommand() {
        super("SharkCommand");
        this.setAliases(Arrays.asList("sharkhub", "hubcore", "hub", "shark", "hubcore"));
        this.setUsage(CC.translate("&cUsage: /sharkhub reload"));
    }

    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender.hasPermission("hubcore.command.reload"))
            if (strings.length == 0) {
                getUsage();
            } else if(strings.length == 1) {
                if(strings[0].equalsIgnoreCase("reload")) {
                    SharkHub.getInstance().getSettingsConfig().reload();
                    SharkHub.getInstance().getMainConfig().reload();
                    SharkHub.getInstance().getTogglesConfig().reload();

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
                    SharkHub.getInstance().getParticlesConfig().reload();
                    commandSender.sendMessage(CC.translate("&aSuccessfully all SharkHub files reloaded!"));
                }
            }
        return true;
    }
}
