package es.hulk.hub.util.scoreboard;
import lombok.Getter;

@Getter
public enum ScoreboardStyle {

    KOHI(true, 15),
    VIPER(true, -1),
    MODERN(false, 1);

    private final boolean decending;
    private final int startNumber;

    ScoreboardStyle(boolean decending, int startNumber) {
        this.decending = decending;
        this.startNumber = startNumber;
    }

    public boolean isDecending() {
        return this.decending;
    }
    
    public int getStartNumber() {
        return this.startNumber;
    }
}
