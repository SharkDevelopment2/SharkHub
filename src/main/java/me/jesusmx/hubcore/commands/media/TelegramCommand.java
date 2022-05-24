package me.jesusmx.hubcore.commands.media;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class TelegramCommand extends Command {
    private final ConfigFile config = SharkHub.getInstance().getMessagesConfig();

    public TelegramCommand() {
        super("telegram");
        this.setAliases(Arrays.asList("tl", "tgm"));
        this.setUsage(ChatColor.RED + "Usage: /telegram");
    }

    public boolean execute(CommandSender sender, String s, String[] strings) {
        config.getStringList("MEDIA.TELEGRAM").stream().map(CC::translate).forEach(sender::sendMessage);
        return false;
    }
}
