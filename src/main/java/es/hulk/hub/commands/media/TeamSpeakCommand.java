package es.hulk.hub.commands.media;

import es.hulk.hub.util.CC;
import es.hulk.hub.SharkHub;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class TeamSpeakCommand extends Command {
    private final ConfigFile config = SharkHub.getInstance().getMessagesConfig();

    public TeamSpeakCommand() {
        super("teamspeak");
        this.setAliases(Arrays.asList("ts", "ts3"));
        this.setUsage(ChatColor.RED + "Usage: /teamspeak");
    }

    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        config.getStringList("MEDIA.TEAMSPEAK").stream().map(CC::translate).forEach(sender::sendMessage);
        return false;
    }
}
