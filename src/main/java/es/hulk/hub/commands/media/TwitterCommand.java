package es.hulk.hub.commands.media;

import es.hulk.hub.util.CC;
import es.hulk.hub.util.files.ConfigFile;
import es.hulk.hub.SharkHub;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Collections;

public class TwitterCommand extends Command {

    private final ConfigFile config = SharkHub.getInstance().getMessagesConfig();

    public TwitterCommand() {
        super("twitter");
        this.setAliases(Collections.singletonList("tw"));
        this.setUsage(ChatColor.RED + "Usage: /twitter");
    }

    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        config.getStringList("MEDIA.TWITTER").stream().map(CC::translate).forEach(sender::sendMessage);
        return false;
    }
}
