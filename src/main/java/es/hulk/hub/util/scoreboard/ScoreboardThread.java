package es.hulk.hub.util.scoreboard;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;

import java.util.Collections;
import java.util.List;

public class ScoreboardThread extends Thread {

    private final Scoreboard assemble;

    ScoreboardThread(Scoreboard assemble) {
        this.assemble = assemble;
        this.start();
    }

    @Override
    public void run() {
        while(true) {
            //Tick
            try {
                tick();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Thread Sleep
            try {
                sleep(assemble.getTicks() * 50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void tick() {
        for (Player player : this.assemble.getPlugin().getServer().getOnlinePlayers()) {
            try {
                ScoreboardManager board = this.assemble.getBoards().get(player.getUniqueId());

                // This shouldn't happen, but just in case
                if (board == null) {
                    continue;
                }

                org.bukkit.scoreboard.Scoreboard scoreboard = board.getScoreboard();
                Objective objective = board.getObjective();


                if (scoreboard == null || objective == null) {
                    continue;
                }

                // Just make a variable so we don't have to
                // process the same thing twice
                String title = ChatColor.translateAlternateColorCodes('&', this.assemble.getAdapter().getTitle(player));

                // Update the title if needed
                if (!objective.getDisplayName().equals(title)) {
                    objective.setDisplayName(title);
                }

                List<String> newLines = this.assemble.getAdapter().getLines(player);


                // Allow adapter to return null/empty list to display nothing
                if (newLines == null || newLines.isEmpty()) {
                    for (ScoreboardEntry entry : board.getEntries()) entry.remove();
                    board.getEntries().clear();
                }
                else {
                    if (this.assemble.getAdapter().getLines(player).size() > 15) {
                        newLines = this.assemble.getAdapter().getLines(player).subList(0, 15);
                    }

                    // Reverse the lines because scoreboard scores are in descending order
                    if (!this.assemble.getAssembleStyle().isDecending()) {
                        Collections.reverse(newLines);
                    }

                    // Remove excessive amount of board entries
                    if (board.getEntries().size() > newLines.size()) {
                        for (int i = newLines.size(); i < board.getEntries().size(); i++) {
                            ScoreboardEntry entry = board.getEntryAtPosition(i);

                            if (entry != null) {
                                entry.remove();
                            }
                        }
                    }

                    // Update existing entries / add new entries
                    int cache = this.assemble.getAssembleStyle().getStartNumber();
                    for (int i = 0; i < newLines.size(); i++) {
                        ScoreboardEntry entry = board.getEntryAtPosition(i);

                        // Translate any colors
                        String line = ChatColor.translateAlternateColorCodes('&', newLines.get(i));

                        // If the entry is null, just create a new one.
                        // Creating a new ScoreboardEntry instance will add
                        // itself to the provided board's entries list.
                        if (entry == null) {
                            entry = new ScoreboardEntry(board, line, i);
                        }

                        // Update text, setup the team, and update the display values
                        entry.setText(line);
                        entry.setup();
                        entry.send(
                                this.assemble.getAssembleStyle().isDecending() ? cache-- : cache++
                        );
                    }
                }

                if (player.getScoreboard() != scoreboard && !assemble.isHook()) {
                    player.setScoreboard(scoreboard);
                }
            } catch(Exception ignored) {}
        }
    }
}

