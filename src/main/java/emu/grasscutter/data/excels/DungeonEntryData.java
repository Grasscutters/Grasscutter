package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.Getter;
import lombok.Setter;

@ResourceType(name = "DungeonEntryExcelConfigData.json")
@Getter
@Setter
public class DungeonEntryData extends GameResource {
    private int dungeonEntryId;
    private int sceneId;
    private int id;

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void onLoad() {

    }
}
