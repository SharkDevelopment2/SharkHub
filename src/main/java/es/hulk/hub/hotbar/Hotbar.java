package es.hulk.hub.hotbar;

import com.cryptomorin.xseries.XMaterial;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class Hotbar {

    private boolean enabled;
    private String name;
    private String displayName;
    private XMaterial material;
    private int data;
    private List<String> lore;
    private int amount;

    private int slot;
    private List<String> actions;

}
