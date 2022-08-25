package es.hulk.tablist;

import es.hulk.tablist.skin.Skin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;

/**
 * Created By LeandroSSJ
 * Created on 19/05/2022
 */
@Getter
@Setter
@AllArgsConstructor
public class TabEntry {

    private final String name;
    private final TabColumn column;
    private final OfflinePlayer player;
    private final int rawSlot;

    private String text;
    private int slot, ping;
    private Skin skin;

}