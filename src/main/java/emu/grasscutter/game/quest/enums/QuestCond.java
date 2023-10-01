package emu.grasscutter.game.quest.enums;

import it.unimi.dsi.fastutil.ints.*;
import java.util.*;
import java.util.stream.Stream;

public enum QuestCond implements QuestTrigger {
    QUEST_COND_NONE(0),
    QUEST_COND_STATE_EQUAL(1),
    QUEST_COND_STATE_NOT_EQUAL(2),
    QUEST_COND_PACK_HAVE_ITEM(3),
    QUEST_COND_AVATAR_ELEMENT_EQUAL(4), // missing, currently unused
    QUEST_COND_AVATAR_ELEMENT_NOT_EQUAL(5), // missing, only NPC groups
    QUEST_COND_AVATAR_CAN_CHANGE_ELEMENT(6), // missing, only NPC groups
    QUEST_COND_CITY_LEVEL_EQUAL_GREATER(7), // missing, currently unused
    QUEST_COND_ITEM_NUM_LESS_THAN(8),
    QUEST_COND_DAILY_TASK_START(9), // missing
    QUEST_COND_OPEN_STATE_EQUAL(10),
    QUEST_COND_DAILY_TASK_OPEN(11), // missing, only NPC groups
    QUEST_COND_DAILY_TASK_REWARD_CAN_GET(12), // missing, only NPC groups/talks
    QUEST_COND_DAILY_TASK_REWARD_RECEIVED(13), // missing, only NPC groups/talks
    QUEST_COND_PLAYER_LEVEL_REWARD_CAN_GET(14), // missing, only NPC groups/talks
    QUEST_COND_EXPLORATION_REWARD_CAN_GET(15), // missing, only NPC groups/talks
    QUEST_COND_IS_WORLD_OWNER(16), // missing, only NPC groups/talks
    QUEST_COND_PLAYER_LEVEL_EQUAL_GREATER(17),
    QUEST_COND_SCENE_AREA_UNLOCKED(18), // missing, only NPC groups/talks
    QUEST_COND_ITEM_GIVING_ACTIVED(19), // missing
    QUEST_COND_ITEM_GIVING_FINISHED(20),
    QUEST_COND_IS_DAYTIME(21), // only NPC groups
    QUEST_COND_CURRENT_AVATAR(22), // missing
    QUEST_COND_CURRENT_AREA(23), // missing
    QUEST_COND_QUEST_VAR_EQUAL(24),
    QUEST_COND_QUEST_VAR_GREATER(25),
    QUEST_COND_QUEST_VAR_LESS(26),
    QUEST_COND_FORGE_HAVE_FINISH(27), // missing, only NPC groups
    QUEST_COND_DAILY_TASK_IN_PROGRESS(28), // missing
    QUEST_COND_DAILY_TASK_FINISHED(29), // missing, currently unused
    QUEST_COND_ACTIVITY_COND(30),
    QUEST_COND_ACTIVITY_OPEN(31),
    QUEST_COND_DAILY_TASK_VAR_GT(32), // missing
    QUEST_COND_DAILY_TASK_VAR_EQ(33), // missing
    QUEST_COND_DAILY_TASK_VAR_LT(34), // missing
    QUEST_COND_BARGAIN_ITEM_GT(35), // missing, currently unused
    QUEST_COND_BARGAIN_ITEM_EQ(36), // missing, currently unused
    QUEST_COND_BARGAIN_ITEM_LT(37), // missing, currently unused
    QUEST_COND_COMPLETE_TALK(38),
    QUEST_COND_NOT_HAVE_BLOSSOM_TALK(39), // missing, only NPC groups
    QUEST_COND_IS_CUR_BLOSSOM_TALK(40), // missing, only Blossom groups
    QUEST_COND_QUEST_NOT_RECEIVE(41), // missing
    QUEST_COND_QUEST_SERVER_COND_VALID(42), // missing, only NPC groups
    QUEST_COND_ACTIVITY_CLIENT_COND(43), // missing, only NPC and Activity groups
    QUEST_COND_QUEST_GLOBAL_VAR_EQUAL(44),
    QUEST_COND_QUEST_GLOBAL_VAR_GREATER(45),
    QUEST_COND_QUEST_GLOBAL_VAR_LESS(46),
    QUEST_COND_PERSONAL_LINE_UNLOCK(47),
    QUEST_COND_CITY_REPUTATION_REQUEST(48), // missing
    QUEST_COND_MAIN_COOP_START(49),
    QUEST_COND_MAIN_COOP_ENTER_SAVE_POINT(50), // missing
    QUEST_COND_CITY_REPUTATION_LEVEL(51), // missing, only NPC groups
    QUEST_COND_CITY_REPUTATION_UNLOCK(52), // missing, currently unused
    QUEST_COND_LUA_NOTIFY(53),
    QUEST_COND_CUR_CLIMATE(54),
    QUEST_COND_ACTIVITY_END(55),
    QUEST_COND_COOP_POINT_RUNNING(56), // missing, currently unused
    QUEST_COND_GADGET_TALK_STATE_EQUAL(57), // missing, only Gadget groups
    QUEST_COND_AVATAR_FETTER_GT(58), // missing, only NPC groups/talks
    QUEST_COND_AVATAR_FETTER_EQ(59), // missing, only talks
    QUEST_COND_AVATAR_FETTER_LT(60), // missing, only talks
    QUEST_COND_NEW_HOMEWORLD_MOUDLE_UNLOCK(61), // missing, only Gadget groups
    QUEST_COND_NEW_HOMEWORLD_LEVEL_REWARD(62), // missing, only Gadget groups
    QUEST_COND_NEW_HOMEWORLD_MAKE_FINISH(63), // missing, only Gadget groups
    QUEST_COND_HOMEWORLD_NPC_EVENT(64), // missing, only NPC groups
    QUEST_COND_TIME_VAR_GT_EQ(65),
    QUEST_COND_TIME_VAR_PASS_DAY(66),
    QUEST_COND_HOMEWORLD_NPC_NEW_TALK(67), // missing, only NPC groups
    QUEST_COND_PLAYER_CHOOSE_MALE(68), // missing, only talks
    QUEST_COND_HISTORY_GOT_ANY_ITEM(69),
    QUEST_COND_LEARNED_RECIPE(70), // missing, currently unused
    QUEST_COND_LUNARITE_REGION_UNLOCKED(71), // missing, only NPC groups
    QUEST_COND_LUNARITE_HAS_REGION_HINT_COUNT(72), // missing, only NPC groups
    QUEST_COND_LUNARITE_COLLECT_FINISH(73), // missing, only NPC groups
    QUEST_COND_LUNARITE_MARK_ALL_FINISH(74), // missing, only NPC groups
    QUEST_COND_NEW_HOMEWORLD_SHOP_ITEM(75), // missing, only Gadget groups
    QUEST_COND_SCENE_POINT_UNLOCK(76), // missing, only NPC groups
    QUEST_COND_SCENE_LEVEL_TAG_EQ(77), // missing
    QUEST_COND_PLAYER_ENTER_REGION(78), // missing
    QUEST_COND_UNKNOWN(9999);

    private final int value;

    QuestCond(int id) {
        this.value = id;
    }

    public int getValue() {
        return value;
    }

    private static final Int2ObjectMap<QuestCond> contentMap = new Int2ObjectOpenHashMap<>();
    private static final Map<String, QuestCond> contentStringMap = new HashMap<>();

    static {
        Stream.of(values())
                .forEach(
                        e -> {
                            contentMap.put(e.getValue(), e);
                            contentStringMap.put(e.name(), e);
                        });
    }

    public static QuestCond getContentTriggerByValue(int value) {
        return contentMap.getOrDefault(value, QUEST_COND_NONE);
    }

    public static QuestCond getContentTriggerByName(String name) {
        return contentStringMap.getOrDefault(name, QUEST_COND_NONE);
    }
}
