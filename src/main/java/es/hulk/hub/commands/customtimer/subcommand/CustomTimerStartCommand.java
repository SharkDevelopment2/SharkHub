package es.hulk.hub.commands.customtimer.subcommand;

import es.hulk.hub.SharkHub;
import es.hulk.hub.managers.customtimer.CustomTimer;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.JavaUtils;
import es.hulk.hub.util.command.BaseCommand;
import es.hulk.hub.util.command.Command;
import es.hulk.hub.util.command.CommandArgs;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.command.CommandSender;

public class CustomTimerStartCommand extends BaseCommand {

    @Command(name = "customtimer.start", permission = "sharkhub.command.customtimer", aliases = {"customt.start", "ctimer.start", "ct.start"}, inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 3) {
            sender.sendMessage(CC.translate("&cUsage: /customtimer start <timer> <time> <prefix>"));
            return;
        }

        String timer = WordUtils.capitalize(args[0]);

        if (SharkHub.getInstance().getCustomTimerManager().getCustomTimer(timer) != null) {
            sender.sendMessage(CC.translate("&cTimer '" + timer + "' already exist."));
            return;
        }

        long duration = JavaUtils.formatLong(args[1]);

        if (duration == -1L) {
            sender.sendMessage(CC.translate("&c" + args[1] + " is an invalid duration."));
            return;
        }

        if (duration < 1000L) {
            sender.sendMessage(CC.translate("&cTimer must last for at least 20 ticks."));
            return;
        }

        String prefix = StringUtils.join(args, ' ', 2, args.length);

        SharkHub.getInstance().getCustomTimerManager().addTimer(new CustomTimer(timer, prefix, System.currentTimeMillis(), System.currentTimeMillis() + duration));
        sender.sendMessage(CC.translate("&aCustom Timer '&f" + timer + "&a' has been created."));
    }
}
