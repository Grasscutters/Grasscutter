package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.Getter;
import lombok.Setter;

@ResourceType(name = "DungeonEntryExcelConfigData.json")
@Getter
@Setter  // TODO: remove this next API break
public class DungeonEntryData extends GameResource {
    @Getter(onMethod = @__(@Override))
    private int id;
    private int dungeonEntryId;
    private int sceneId;
}
