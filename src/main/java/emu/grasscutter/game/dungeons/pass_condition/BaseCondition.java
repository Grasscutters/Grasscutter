package emu.grasscutter.game.dungeons.pass_condition;

import emu.grasscutter.data.excels.dungeon.DungeonPassConfigData;
import emu.grasscutter.game.dungeons.DungeonValue;
import emu.grasscutter.game.dungeons.enums.DungeonPassConditionType;
import emu.grasscutter.game.dungeons.handlers.DungeonBaseHandler;

@DungeonValue(DungeonPassConditionType.DUNGEON_COND_NONE)
public class BaseCondition extends DungeonBaseHandler {

    @Override
    public boolean execute(DungeonPassConfigData.DungeonPassCondition condition, int... params) {
        // TODO Auto-generated method stub
        return false;
    }
}
