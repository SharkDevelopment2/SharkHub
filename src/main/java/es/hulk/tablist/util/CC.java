package es.hulk.tablist.util;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created By LeandroSSJ
 * Created on 19/05/2022
 */
public class CC {

    public static String translate(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static List<String> translate(List<String> input) {
        return input.stream().map(CC::translate).collect(Collectors.toList());
    }

}