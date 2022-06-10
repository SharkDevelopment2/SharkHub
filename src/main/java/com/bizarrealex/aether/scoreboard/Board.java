package com.bizarrealex.aether.scoreboard;

import com.bizarrealex.aether.Aether;
import com.bizarrealex.aether.AetherOptions;
import com.bizarrealex.aether.scoreboard.cooldown.BoardCooldown;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;

public class Board {

  private static Set<Board> boards = new HashSet<>();

  @Getter
  private Scoreboard scoreboard;

  @Getter
  private Player player;

  @Getter
  private Objective objective;

  @Getter
  private Set<String> keys;

  @Getter
  private List<BoardEntry> entries;

  private Set<BoardCooldown> cooldowns;
  private final Aether aether;
  private final AetherOptions options;

  public Board(Player player, Aether aether, AetherOptions options) {
    this.player = player;
    this.aether = aether;
    this.options = options;
    this.keys = new HashSet<>();
    this.cooldowns = new HashSet<>();
    this.entries = new ArrayList<>();

    setup();
  }

  private void setup() {
    if (
      options.hook() &&
      !player.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard())
    ) {
      scoreboard = player.getScoreboard();
    } else {
      scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    }

    objective = scoreboard.registerNewObjective("glaedr_is_shit", "dummy");
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

    if (aether.getAdapter() != null) {
      objective.setDisplayName(
        ChatColor.translateAlternateColorCodes('&', aether.getAdapter().getTitle(player))
      );
    } else {
      objective.setDisplayName("Default Title");
    }

    boards.add(this);
  }

  public String getNewKey(BoardEntry entry) {
    for (ChatColor color : ChatColor.values()) {
      String colorText = color + "" + ChatColor.WHITE;

      if (entry.getText().length() > 16) {
        String sub = entry.getText().substring(0, 16);
        colorText = colorText + ChatColor.getLastColors(sub);
      }

      if (!keys.contains(colorText)) {
        keys.add(colorText);
        return colorText;
      }
    }

    throw new IndexOutOfBoundsException("No more keys available!");
  }

  public List<String> getBoardEntriesFormatted() {
    List<String> toReturn = new ArrayList<>();

    for (BoardEntry entry : new ArrayList<>(entries)) {
      toReturn.add(entry.getText());
    }

    return toReturn;
  }

  public BoardEntry getByPosition(int position) {
    int i = 0;

    for (BoardEntry board : entries) {
      if (i == position) {
        return board;
      }
      i++;
    }

    return null;
  }

  public BoardCooldown getCooldown(String id) {
    for (BoardCooldown cooldown : getCooldowns()) {
      if (cooldown.getId().equals(id)) {
        return cooldown;
      }
    }

    return null;
  }

  public Set<BoardCooldown> getCooldowns() {
    Iterator<BoardCooldown> iterator = cooldowns.iterator();

    while (iterator.hasNext()) {
      BoardCooldown cooldown = iterator.next();
      if (System.currentTimeMillis() >= cooldown.getEnd()) {
        iterator.remove();
      }
    }

    return cooldowns;
  }

  public static Board getByPlayer(Player player) {
    for (Board board : boards) {
      if (board.getPlayer().getName().equals(player.getName())) {
        return board;
      }
    }
    return null;
  }

  public static Set<Board> getBoards() {
    return boards;
  }
}
