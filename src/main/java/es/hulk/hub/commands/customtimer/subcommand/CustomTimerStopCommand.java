package es.hulk.hub.commands.customtimer.subcommand;

import es.hulk.hub.SharkHub;
import es.hulk.hub.managers.customtimer.CustomTimer;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.command.BaseCommand;
import es.hulk.hub.util.command.Command;
import es.hulk.hub.util.command.CommandArgs;
import org.apache.commons.lang.WordUtils;
import org.bukkit.command.CommandSender;

public class CustomTimerStopCommand extends BaseCommand {

    @Command(name = "customtimer.stop",permission = "sharkhub.command.customtimer", aliases = {"customt.stop", "ctimer.stop", "ct.stop"}, inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 1) {
            sender.sendMessage(CC.translate("&cUsage: /customtimer stop <timer>"));
            return;
        }

        String timer = WordUtils.capitalize(args[0]);
        CustomTimer customTimer = SharkHub.getInstance().getCustomTimerManager().getCustomTimer(timer);

        if (customTimer == null) {
            sender.sendMessage(CC.translate("&cTimer '" + timer + "' is not activated."));
            return;
        }

        customTimer.cancel();
        sender.sendMessage(CC.translate("&cCustom Timer '&f" + timer + "&c' has been stopped."));
    }
}
