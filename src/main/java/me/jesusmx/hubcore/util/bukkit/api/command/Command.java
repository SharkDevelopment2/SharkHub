package me.jesusmx.hubcore.util.bukkit.api.command;

import lombok.SneakyThrows;
import me.jesusmx.hubcore.SharkHub;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

import static org.bukkit.Bukkit.getServer;

public class Command {

    @SneakyThrows
    public static void registerCommand(org.bukkit.command.Command cmd) {
        Field field = getServer().getClass().getDeclaredField("commandMap");
        field.setAccessible(true);
        CommandMap commandMap = (CommandMap) field.get(getServer());
        commandMap.register(SharkHub.getInstance().getName(), cmd);
    }

}
