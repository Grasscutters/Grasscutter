package emu.grasscutter.game.talk;

import it.unimi.dsi.fastutil.ints.*;
import java.util.*;
import java.util.stream.Stream;
import lombok.*;

@AllArgsConstructor
public enum TalkExec {
    TALK_EXEC_NONE(0),
    TALK_EXEC_SET_GADGET_STATE(1),
    TALK_EXEC_SET_GAME_TIME(2),
    TALK_EXEC_NOTIFY_GROUP_LUA(3),
    TALK_EXEC_SET_DAILY_TASK_VAR(4),
    TALK_EXEC_INC_DAILY_TASK_VAR(5),
    TALK_EXEC_DEC_DAILY_TASK_VAR(6),
    TALK_EXEC_SET_QUEST_VAR(7),
    TALK_EXEC_INC_QUEST_VAR(8),
    TALK_EXEC_DEC_QUEST_VAR(9),
    TALK_EXEC_SET_QUEST_GLOBAL_VAR(10),
    TALK_EXEC_INC_QUEST_GLOBAL_VAR(11),
    TALK_EXEC_DEC_QUEST_GLOBAL_VAR(12),
    TALK_EXEC_TRANS_SCENE_DUMMY_POINT(13),
    TALK_EXEC_SAVE_TALK_ID(14);

    private static final Int2ObjectMap<TalkExec> execMap = new Int2ObjectOpenHashMap<>();
    private static final Map<String, TalkExec> execStringMap = new HashMap<>();

    static {
        Stream.of(TalkExec.values())
                .filter(e -> e.name().startsWith("TALK_EXEC_"))
                .forEach(
                        entry -> {
                            execMap.put(entry.getValue(), entry);
                            execStringMap.put(entry.name(), entry);
                        });
    }

    /**
     * Gets the talk execution condition by its value.
     *
     * @param value The integer value of the condition.
     * @return The corresponding enum value.
     */
    public static TalkExec getExecByValue(int value) {
        return execMap.getOrDefault(value, TALK_EXEC_NONE);
    }

    /**
     * Gets the talk execution by its name.
     *
     * @param name The string name of the condition.
     * @return The corresponding enum value.
     */
    public static TalkExec getExecByName(String name) {
        return execStringMap.getOrDefault(name, TALK_EXEC_NONE);
    }

    @Getter private final int value;
}
