package emu.grasscutter.game.quest.enums;

import it.unimi.dsi.fastutil.ints.*;
import java.util.*;
import java.util.stream.Stream;

public enum QuestExec implements QuestTrigger {
    QUEST_EXEC_NONE(0),
    QUEST_EXEC_DEL_PACK_ITEM(1),
    QUEST_EXEC_UNLOCK_POINT(2),
    QUEST_EXEC_UNLOCK_AREA(3),
    QUEST_EXEC_UNLOCK_FORCE(4), // missing, currently unused
    QUEST_EXEC_LOCK_FORCE(5), // missing, currently unused
    QUEST_EXEC_CHANGE_AVATAR_ELEMET(6),
    QUEST_EXEC_REFRESH_GROUP_MONSTER(7),
    QUEST_EXEC_SET_IS_FLYABLE(8),
    QUEST_EXEC_SET_IS_WEATHER_LOCKED(9), // missing
    QUEST_EXEC_SET_IS_GAME_TIME_LOCKED(10),
    QUEST_EXEC_SET_IS_TRANSFERABLE(11), // missing, currently unused
    QUEST_EXEC_GRANT_TRIAL_AVATAR(12),
    QUEST_EXEC_OPEN_BORED(13), // missing, currently unused
    QUEST_EXEC_ROLLBACK_QUEST(14),
    QUEST_EXEC_NOTIFY_GROUP_LUA(15),
    QUEST_EXEC_SET_OPEN_STATE(16),
    QUEST_EXEC_LOCK_POINT(17), // missing
    QUEST_EXEC_DEL_PACK_ITEM_BATCH(18),
    QUEST_EXEC_REFRESH_GROUP_SUITE(19),
    QUEST_EXEC_REMOVE_TRIAL_AVATAR(20),
    QUEST_EXEC_SET_GAME_TIME(21), // missing
    QUEST_EXEC_SET_WEATHER_GADGET(22), // missing
    QUEST_EXEC_ADD_QUEST_PROGRESS(23),
    QUEST_EXEC_NOTIFY_DAILY_TASK(24), // missing
    QUEST_EXEC_CREATE_PATTERN_GROUP(25), // missing, used for random quests
    QUEST_EXEC_REMOVE_PATTERN_GROUP(26), // missing, used for random quests
    QUEST_EXEC_REFRESH_GROUP_SUITE_RANDOM(27), // missing
    QUEST_EXEC_ACTIVE_ITEM_GIVING(28),
    QUEST_EXEC_DEL_ALL_SPECIFIC_PACK_ITEM(29), // missing
    QUEST_EXEC_ROLLBACK_PARENT_QUEST(30),
    QUEST_EXEC_LOCK_AVATAR_TEAM(31), // missing
    QUEST_EXEC_UNLOCK_AVATAR_TEAM(32), // missing
    QUEST_EXEC_UPDATE_PARENT_QUEST_REWARD_INDEX(33), // missing
    QUEST_EXEC_SET_DAILY_TASK_VAR(34), // missing
    QUEST_EXEC_INC_DAILY_TASK_VAR(35), // missing
    QUEST_EXEC_DEC_DAILY_TASK_VAR(36), // missing, currently unused
    QUEST_EXEC_ACTIVE_ACTIVITY_COND_STATE(37), // missing
    QUEST_EXEC_INACTIVE_ACTIVITY_COND_STATE(38), // missing
    QUEST_EXEC_ADD_CUR_AVATAR_ENERGY(39),
    QUEST_EXEC_START_BARGAIN(41),
    QUEST_EXEC_STOP_BARGAIN(42),
    QUEST_EXEC_SET_QUEST_GLOBAL_VAR(43),
    QUEST_EXEC_INC_QUEST_GLOBAL_VAR(44),
    QUEST_EXEC_DEC_QUEST_GLOBAL_VAR(45),
    QUEST_EXEC_REGISTER_DYNAMIC_GROUP(
            46), // test, maybe the dynamic should be saved on a list and when you enter the view range
    // this loads it again
    QUEST_EXEC_UNREGISTER_DYNAMIC_GROUP(47), // test, same for this
    QUEST_EXEC_SET_QUEST_VAR(48),
    QUEST_EXEC_INC_QUEST_VAR(49),
    QUEST_EXEC_DEC_QUEST_VAR(50),
    QUEST_EXEC_RANDOM_QUEST_VAR(51),
    QUEST_EXEC_ACTIVATE_SCANNING_PIC(52), // missing, currently unused
    QUEST_EXEC_RELOAD_SCENE_TAG(53), // missing
    QUEST_EXEC_REGISTER_DYNAMIC_GROUP_ONLY(54), // missing
    QUEST_EXEC_CHANGE_SKILL_DEPOT(55), // missing
    QUEST_EXEC_ADD_SCENE_TAG(56), // missing
    QUEST_EXEC_DEL_SCENE_TAG(57), // missing
    QUEST_EXEC_INIT_TIME_VAR(58),
    QUEST_EXEC_CLEAR_TIME_VAR(59),
    QUEST_EXEC_MODIFY_CLIMATE_AREA(60), // missing
    QUEST_EXEC_GRANT_TRIAL_AVATAR_AND_LOCK_TEAM(61), // missing
    QUEST_EXEC_CHANGE_MAP_AREA_STATE(62), // missing
    QUEST_EXEC_DEACTIVE_ITEM_GIVING(63),
    QUEST_EXEC_CHANGE_SCENE_LEVEL_TAG(64), // missing
    QUEST_EXEC_UNLOCK_PLAYER_WORLD_SCENE(65), // missing
    QUEST_EXEC_LOCK_PLAYER_WORLD_SCENE(66), // missing
    QUEST_EXEC_FAIL_MAINCOOP(67), // missing
    QUEST_EXEC_MODIFY_WEATHER_AREA(68), // missing
    QUEST_EXEC_MODIFY_ARANARA_COLLECTION_STATE(69), // missing
    QUEST_EXEC_GRANT_TRIAL_AVATAR_BATCH_AND_LOCK_TEAM(70), // missing
    QUEST_EXEC_UNKNOWN(9999);

    private final int value;

    QuestExec(int id) {
        this.value = id;
    }

    public int getValue() {
        return value;
    }

    private static final Int2ObjectMap<QuestExec> contentMap = new Int2ObjectOpenHashMap<>();
    private static final Map<String, QuestExec> contentStringMap = new HashMap<>();

    static {
        Stream.of(values())
                .filter(e -> e.name().startsWith("QUEST_CONTENT_"))
                .forEach(
                        e -> {
                            contentMap.put(e.getValue(), e);
                            contentStringMap.put(e.name(), e);
                        });
    }

    public static QuestExec getContentTriggerByValue(int value) {
        return contentMap.getOrDefault(value, QUEST_EXEC_NONE);
    }

    public static QuestExec getContentTriggerByName(String name) {
        return contentStringMap.getOrDefault(name, QUEST_EXEC_NONE);
    }
}
