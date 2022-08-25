package es.hulk.tablist.nms;

import es.hulk.tablist.TabColumn;
import es.hulk.tablist.TabEntry;
import es.hulk.tablist.Tablist;
import es.hulk.tablist.skin.Skin;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created By LeandroSSJ
 * Created on 19/05/2022
 */
public interface NMS {

    TabEntry createEntry(Tablist tablist, String name, TabColumn column, int slot, int rawSlot, boolean legacy);

    void updateText(Tablist tablist, TabEntry entry, String[] newStrings);

    void updateTexture(Tablist tablist, TabEntry entry, Skin skin);

    void updatePing(Tablist tablist, TabEntry entry, int ping);

    void updateHeaderAndFooter(Player player, List<String> header, List<String> footer);

    Skin getSkin(Player player);

}
