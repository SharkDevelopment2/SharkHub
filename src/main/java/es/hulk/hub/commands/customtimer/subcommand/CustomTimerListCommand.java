package es.hulk.hub.commands.customtimer.subcommand;

import es.hulk.hub.SharkHub;
import es.hulk.hub.managers.customtimer.CustomTimer;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.command.BaseCommand;
import es.hulk.hub.util.command.Command;
import es.hulk.hub.util.command.CommandArgs;
import org.bukkit.command.CommandSender;

public class CustomTimerListCommand extends BaseCommand {

    @Command(name = "customtimer.list", permission = "sharkhub.command.customtimer", aliases = {"customt.list", "ctimer.list", "ct.list"}, inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        sender.sendMessage(CC.translate("&7&m----------------------------------------------------"));
        sender.sendMessage(CC.translate("&6&lCustom Timer List"));
        sender.sendMessage(CC.translate(""));

        if (SharkHub.getInstance().getCustomTimerManager().getCustomTimers().isEmpty()) {
            sender.sendMessage(CC.translate("&cNone"));
        } else {
            for (CustomTimer customTimer : SharkHub.getInstance().getCustomTimerManager().getCustomTimers()) {
                sender.sendMessage(CC.translate(" &7- &e" + customTimer.getName()));
            }
        }

        sender.sendMessage(CC.translate("&7&m----------------------------------------------------"));
    }
}
