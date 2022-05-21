package me.jesusmx.hubcore.util;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jesusmx.hubcore.SharkHub;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CC {
    
    public static String translate(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }
    
    public static List<String> translate(List<String> lines) {
        List<String> toReturn = new ArrayList<String>();
        for (String line : lines) {
            toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        return toReturn;
    }

    public static String translate(Player player, Player target, String text, boolean colorized) {
        return placeholder(player, text, SharkHub.getInstance().isPlaceholderAPI(), colorized);
    }

    public static String placeholder(Player player, String text, boolean isPlaceholderAPI, boolean colorized) {
        if (colorized) {
            return translate(isPlaceholderAPI ? PlaceholderAPI.setPlaceholders(player, text) : text);
        } else {
            return isPlaceholderAPI ? PlaceholderAPI.setPlaceholders(player, text) : text;
        }
    }

    public static String[] CustomMessage(List<String> lines) {
        List<String> toReturn = new ArrayList<String>();
        for (String line : lines) {
            toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        return toReturn.toArray(new String[0]);
    }
}
