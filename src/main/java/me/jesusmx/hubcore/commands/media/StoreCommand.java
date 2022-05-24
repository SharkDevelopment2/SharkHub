package me.jesusmx.hubcore.commands.media;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class StoreCommand extends Command {
    private ConfigFile config = SharkHub.getInstance().getMessagesConfig();

    public StoreCommand() {
        super("store");
        this.setUsage(ChatColor.RED + "Usage: /store");
    }

    public boolean execute(CommandSender sender, String s, String[] strings) {
        config.getStringList("media.store").stream().map(CC::translate).forEach(sender::sendMessage);
        return false;
    }
}
