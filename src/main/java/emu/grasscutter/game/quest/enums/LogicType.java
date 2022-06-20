package emu.grasscutter.game.quest.enums;

import java.util.Arrays;

public enum LogicType {
    LOGIC_NONE(0),
    LOGIC_AND(1),
    LOGIC_OR(2),
    LOGIC_NOT(3),
    LOGIC_A_AND_ETCOR(4),
    LOGIC_A_AND_B_AND_ETCOR(5),
    LOGIC_A_OR_ETCAND(6),
    LOGIC_A_OR_B_OR_ETCAND(7),
    LOGIC_A_AND_B_OR_ETCAND(8);

    private final int value;

    LogicType(int id) {
        this.value = id;
    }

    public int getValue() {
        return this.value;
    }

    public static boolean calculate(LogicType logicType, int[] progress) {
        if (logicType == null) {
            return progress[0] == 1;
        }

        switch (logicType) {
            case LOGIC_AND -> {
                return Arrays.stream(progress).allMatch(i -> i == 1);
            }
            case LOGIC_OR -> {
                return Arrays.stream(progress).anyMatch(i -> i == 1);
            }
            default -> {
                return Arrays.stream(progress).anyMatch(i -> i == 1);
            }
        }
    }
}
