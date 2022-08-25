package es.hulk.tablist;

import es.hulk.tablist.skin.Skin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created By LeandroSSJ
 * Created on 19/05/2022
 */
@Getter @Setter
@Accessors(chain = true)
@AllArgsConstructor
public final class TabLayout {

    private TabColumn column;
    private int slot;

    private String text;
    private int ping;
    private Skin skin;

    public TabLayout(TabColumn column, int slot) {
        this(column, slot, "");
    }

    public TabLayout(TabColumn column, int slot, String text) {
        this(column, slot, text, -1);
    }

    public TabLayout(TabColumn column, int slot, String text, int ping) {
        this(column, slot, text, ping, Skin.DEFAULT);
    }

    public TabLayout(TabColumn column, int slot, String text, Skin skin) {
        this(column, slot, text, -1, skin);
    }

    public TabLayout(TabColumn column, int slot, String text, Skin skin, int ping) {
        this(column, slot, text, ping, skin);
    }
}