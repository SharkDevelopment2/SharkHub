package es.hulk.tablist.module;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created By LeandroSSJ
 * Created on 19/05/2022
 */
public interface Module {

    default void onEnable(JavaPlugin plugin) {
    }

    default void onDisable(JavaPlugin plugin) {
    }
}
