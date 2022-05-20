package me.jesusmx.hubcore.hotbar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class Hotbar {

    private String name;
    private String displayName;
    private Material material;
    private int data;
    private List<String> lore;
    private int amount;

    private int slot;
    private List<String> actions;

}
