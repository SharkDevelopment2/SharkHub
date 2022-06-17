package es.hulk.hub.commands.customtimer;

import es.hulk.hub.commands.customtimer.subcommand.CustomTimerListCommand;
import es.hulk.hub.commands.customtimer.subcommand.CustomTimerStartCommand;
import es.hulk.hub.commands.customtimer.subcommand.CustomTimerStopCommand;
import es.hulk.hub.util.CC;
import es.hulk.hub.util.command.BaseCommand;
import es.hulk.hub.util.command.Command;
import es.hulk.hub.util.command.CommandArgs;
import org.bukkit.command.CommandSender;

public class CustomTimerCommand extends BaseCommand {

    public CustomTimerCommand() {
        new CustomTimerStartCommand();
        new CustomTimerStopCommand();
        new CustomTimerListCommand();
    }

    @Command(name = "customtimer", permission = "sharkhub.command.customtimer", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        sender.sendMessage(CC.translate("&7&m-----------------------------------------------------"));
        sender.sendMessage(CC.translate("&b&lCustom Timer Help"));
        sender.sendMessage(CC.translate(" "));
        sender.sendMessage( CC.translate(" &e/" + command.getLabel() + " start <timer> <time> <prefix> &7- &fStart a custom timer."));
        sender.sendMessage(CC.translate(" &e/" + command.getLabel() + " stop <timer> &7- &fStop a custom timer."));
        sender.sendMessage( CC.translate(" &e/" + command.getLabel() + " list &7- &fSee all custom timers."));
        sender.sendMessage(CC.translate("&7&m-----------------------------------------------------"));
    }
}
