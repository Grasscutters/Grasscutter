package emu.grasscutter.data.excels.dungeon;

import emu.grasscutter.data.*;
import emu.grasscutter.game.dungeons.enums.*;
import java.util.List;
import lombok.*;

@ResourceType(name = "DungeonEntryExcelConfigData.json")
@Getter
@Setter // TODO: remove this next API break
public class DungeonEntryData extends GameResource {
    @Getter(onMethod_ = @Override)
    private int id;

    private int dungeonEntryId;
    private int sceneId;
    private DungunEntryType type;
    private DungeonEntryCondCombType condComb;
    private List<DungeonEntryCondition> satisfiedCond;

    public static class DungeonEntryCondition {
        private DungeonEntrySatisfiedConditionType type;
        private int param1;
    }

    public DungunEntryType getType() {
        if (type == null) {
            return DungunEntryType.DUNGEN_ENTRY_TYPE_NONE;
        }
        return type;
    }

    public DungeonEntryCondCombType getCondComb() {
        if (condComb == null) {
            return DungeonEntryCondCombType.DUNGEON_ENTRY_COND_COMB_NONE;
        }
        return condComb;
    }

    public int getLevelCondition() {
        for (var cond : satisfiedCond) {
            if (cond.type != null
                    && cond.type.equals(DungeonEntrySatisfiedConditionType.DUNGEON_ENTRY_CONDITION_LEVEL)) {
                return cond.param1;
            }
        }
        return 0;
    }

    public int getQuestCondition() {
        for (var cond : satisfiedCond) {
            if (cond.type != null
                    && cond.type.equals(DungeonEntrySatisfiedConditionType.DUNGEON_ENTRY_CONDITION_QUEST)) {
                return cond.param1;
            }
        }
        return 0;
    }
}
