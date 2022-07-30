package es.hulk.hub.commands.media;

import es.hulk.hub.SharkHub;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.command.BaseCommand;
import es.hulk.hub.util.command.Command;
import es.hulk.hub.util.command.CommandArgs;
import es.hulk.hub.util.files.ConfigFile;
import org.bukkit.entity.Player;

public class DiscordCommand extends BaseCommand {
    private final ConfigFile config = SharkHub.getInstance().getMessagesConfig();

    @Command(name = "discord")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        for (String str : config.getStringList("MEDIA.DISCORD")) {
            player.sendMessage(CC.translate(player, ServerUtil.replaceText(player, str), true));
        }
    }
}