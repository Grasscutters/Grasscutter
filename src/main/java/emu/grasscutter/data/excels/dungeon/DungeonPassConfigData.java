package emu.grasscutter.data.excels.dungeon;

import emu.grasscutter.data.*;
import emu.grasscutter.game.dungeons.enums.DungeonPassConditionType;
import emu.grasscutter.game.quest.enums.LogicType;
import java.util.List;
import lombok.*;

@ResourceType(name = "DungeonPassExcelConfigData.json")
public class DungeonPassConfigData extends GameResource {
    @Getter private int id;
    @Getter private LogicType logicType;
    @Getter @Setter private List<DungeonPassCondition> conds;

    public static class DungeonPassCondition {
        @Getter private DungeonPassConditionType condType;
        @Getter int[] param;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        conds = conds.stream().filter(condition -> condition.getCondType() != null).toList();
    }
}
