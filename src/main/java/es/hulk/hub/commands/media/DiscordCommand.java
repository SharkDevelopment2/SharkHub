package es.hulk.hub.commands.media;

import es.hulk.hub.util.CC;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.SharkHub;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

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