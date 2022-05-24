package me.jesusmx.hubcore.commands.media;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class DiscordCommand extends Command {
    private ConfigFile config = SharkHub.getInstance().getMessagesConfig();

    public DiscordCommand() {
        super("discord");
        this.setAliases(Arrays.asList("dc"));
        this.setUsage(ChatColor.RED + "Usage: /discord");
    }

    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        config.getStringList("media.discord").stream().map(CC::translate).forEach(sender::sendMessage);
        return false;
    }
}