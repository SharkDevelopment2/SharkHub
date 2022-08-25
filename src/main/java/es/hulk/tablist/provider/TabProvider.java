package es.hulk.tablist.provider;

import es.hulk.tablist.TabLayout;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

/**
 * Created By LeandroSSJ
 * Created on 19/05/2022
 */
public interface TabProvider {

    List<String> getFooter(Player player);

    List<String> getHeader(Player player);

    Set<TabLayout> getProvider(Player player);

}