package me.jesusmx.hubcore.commands.media;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;

public class DiscordCommand extends Command {
    private final ConfigFile config = SharkHub.getInstance().getMessagesConfig();

    public DiscordCommand() {
        super("discord");
        this.setAliases(Collections.singletonList("dc"));
        this.setUsage(ChatColor.RED + "Usage: /discord");
    }

    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        config.getStringList("MEDIA.DISCORD").stream().map(CC::translate).forEach(sender::sendMessage);
        return false;
    }
}