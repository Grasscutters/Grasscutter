package emu.grasscutter.game.quest.enums;

import emu.grasscutter.Grasscutter;
import java.util.*;
import java.util.function.BooleanSupplier;
import org.jetbrains.annotations.NotNull;

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

    public static boolean calculate(LogicType logicType, int[] progress) {
        if (progress.length == 0) {
            return true;
        }

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
            case LOGIC_NOT -> {
                return Arrays.stream(progress).noneMatch(i -> i == 1);
            }
            case LOGIC_A_AND_ETCOR -> {
                return progress[0] == 1 && Arrays.stream(progress).skip(1).anyMatch(i -> i == 1);
            }
            case LOGIC_A_AND_B_AND_ETCOR -> {
                return progress[0] == 1
                        && progress[1] == 1
                        && Arrays.stream(progress).skip(2).anyMatch(i -> i == 1);
            }
            case LOGIC_A_OR_ETCAND -> {
                return progress[0] == 1 || Arrays.stream(progress).skip(1).allMatch(i -> i == 1);
            }
            case LOGIC_A_OR_B_OR_ETCAND -> {
                return progress[0] == 1
                        || progress[1] == 1
                        || Arrays.stream(progress).skip(2).allMatch(i -> i == 1);
            }
            case LOGIC_A_AND_B_OR_ETCAND -> {
                return progress[0] == 1 && progress[1] == 1
                        || Arrays.stream(progress).skip(2).allMatch(i -> i == 1);
            }
            default -> {
                return Arrays.stream(progress).anyMatch(i -> i == 1);
            }
        }
    }

    /**
     * Apply logic type to all predicates
     *
     * @param logicType type of logic that should be applied to predicates
     * @param predicates list of predicates for which logicType will be applied
     * @return result of applying logicType to predicates
     */
    public static boolean calculate(@NotNull LogicType logicType, List<BooleanSupplier> predicates) {
        switch (logicType) {
            case LOGIC_AND -> {
                return predicates.stream().allMatch(BooleanSupplier::getAsBoolean);
            }
            case LOGIC_OR -> {
                return predicates.stream().anyMatch(BooleanSupplier::getAsBoolean);
            }
            default -> {
                Grasscutter.getLogger().error("Unimplemented logic operation was called");
                return false;
            }
        }
    }

    public int getValue() {
        return value;
    }
}
