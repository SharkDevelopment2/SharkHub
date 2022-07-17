package es.hulk.hub.menus.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class Server {

    private String name;
    private String displayName;
    private Material material;
    private int data;

    private boolean headEnabled;
    private String headName;

    private boolean customHead;
    private String customHeadValue;

    private boolean queue;
    private String serverName;

    private int slot;
    private List<String> lore;

    private boolean subServer;
    private int amount;

    private boolean commandsEnabled;
    private List<String> commands;

}
