package es.hulk.tablist;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created By LeandroSSJ
 * Created on 19/05/2022
 */
public enum TabColumn {

    LEFT(-2, 1, 3),
    MIDDLE(-1, 21, 3),
    RIGHT(0, 41, 3),
    FAR_RIGHT(60, 61, 1);

    private final int startNumber;

    @Getter
    private final List<Integer> slotList = new ArrayList<>();

    TabColumn(int rawStart, int startNumber, int incrementBy) {
        this.startNumber = startNumber;

        for (int i = 1; i <= 20; i++) {
            slotList.add(rawStart + (i * incrementBy));
        }
    }

    public static TabColumn getColumn(int ordinal) {
        return Arrays.stream(values()).filter(column -> column.ordinal() == ordinal).findFirst().orElse(null);
    }

    private static boolean isBetween(int input, int min, int max) {
        return input >= min && input <= max;
    }

    public static TabColumn getColumn(boolean legacy, int slot) {
        if (legacy) {
            return Arrays.stream(values()).filter(column -> column.getSlotList().contains(slot)).findFirst().orElse(null);
        } else {
            if (isBetween(slot, 1, 20)) return LEFT;
            else if (isBetween(slot, 21, 40)) return MIDDLE;
            else if (isBetween(slot, 41, 60)) return RIGHT;
            else if (isBetween(slot, 61, 80)) return FAR_RIGHT;
            else return null;
        }
    }

    public int getSlot(boolean legacy, int raw) {
        if (!legacy) return raw - startNumber + 1;

        int result = 0;

        for (int slot : slotList) {
            result++;
            if (slot == raw) return result;
        }

        return result;
    }
}