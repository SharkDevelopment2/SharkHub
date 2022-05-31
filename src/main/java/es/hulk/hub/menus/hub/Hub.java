package es.hulk.hub.menus.hub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class Hub {

    private String name;
    private String displayName;
    private Material material;
    private int data;
    private int slot;
    private List<String> lore;

    private boolean queue;
    private String serverName;

}
