package es.hulk.hub.commands.media;

import es.hulk.hub.util.CC;
import es.hulk.hub.SharkHub;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class StoreCommand extends Command {
    private final ConfigFile config = SharkHub.getInstance().getMessagesConfig();

    public StoreCommand() {
        super("store");
        this.setUsage(ChatColor.RED + "Usage: /store");
    }

    public boolean execute(CommandSender sender, String s, String[] strings) {
        config.getStringList("MEDIA.STORE").stream().map(CC::translate).forEach(sender::sendMessage);
        return false;
    }
}
