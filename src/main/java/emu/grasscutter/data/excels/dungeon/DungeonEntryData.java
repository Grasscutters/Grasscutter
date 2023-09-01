package emu.grasscutter.data.excels.dungeon;

import emu.grasscutter.data.*;
import lombok.*;

@ResourceType(name = "DungeonEntryExcelConfigData.json")
@Getter
@Setter // TODO: remove this next API break
public class DungeonEntryData extends GameResource {
    @Getter(onMethod_ = @Override)
    private int id;

    private int dungeonEntryId;
    private int sceneId;
}
