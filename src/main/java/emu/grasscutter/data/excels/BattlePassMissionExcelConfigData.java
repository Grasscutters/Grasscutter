package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@ResourceType(name = {"BattlePassMissionExcelConfigData.json"})
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class BattlePassMissionExcelConfigData extends GameResource {

    private int addPoint;
    private int id;
    private int progress;
    private String refreshType;

    @Override
    public void onLoad() {
    }

    @Override
    public int getId() {
        return this.id;
    }
}
