package emu.grasscutter.data.excels.dungeon;

import emu.grasscutter.data.*;
import emu.grasscutter.game.dungeons.enums.DungeonPassConditionType;
import emu.grasscutter.game.quest.enums.LogicType;
import java.util.List;
import lombok.*;

@Getter
@ResourceType(name = "DungeonPassExcelConfigData.json")
public class DungeonPassConfigData extends GameResource {
    private int id;
    private LogicType logicType;
    @Setter private List<DungeonPassCondition> conds;

    @Getter
    public static class DungeonPassCondition {
        private DungeonPassConditionType condType;
        int[] param;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        conds = conds.stream().filter(condition -> condition.getCondType() != null).toList();
    }
}
