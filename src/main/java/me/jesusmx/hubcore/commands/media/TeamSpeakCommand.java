package me.jesusmx.hubcore.commands.media;

import me.jesusmx.hubcore.SharkHub;
import me.jesusmx.hubcore.util.CC;
import me.jesusmx.hubcore.util.files.ConfigFile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class TeamSpeakCommand extends Command {

    private ConfigFile toggle = SharkHub.getInstance().getTogglesConfig();
    private ConfigFile config = SharkHub.getInstance().getMessagesConfig();

    public TeamSpeakCommand() {
        super("teamspeak");
        this.setAliases(Arrays.asList("ts", "ts3"));
        this.setUsage(ChatColor.RED + "Usage: /teamspeak");
    }

    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (toggle.getBoolean("commands.teamspeak")) {
            config.getStringList("media.teamspeak").stream().map(CC::translate).forEach(sender::sendMessage);
        }
        return false;
    }
}
