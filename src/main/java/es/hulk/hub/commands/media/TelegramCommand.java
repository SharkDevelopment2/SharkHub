package es.hulk.hub.commands.media;

import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.command.BaseCommand;
import es.hulk.hub.util.command.Command;
import es.hulk.hub.util.command.CommandArgs;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class TelegramCommand extends BaseCommand {
    private final ConfigFile config = SharkHub.getInstance().getMessagesConfig();

    @Command(name = "telegram")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        for (String str : config.getStringList("MEDIA.TELEGRAM")) {
            player.sendMessage(CC.translate(player, ServerUtil.replaceText(player, str), true));
        }
    }
}
