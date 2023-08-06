package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@ResourceType(
        name = {"CombineBonusExcelConfigData.json"},
        loadPriority = LoadPriority.LOW)
public class CombineBonusData extends GameResource {
    @Getter @Setter private int avatarId;
    @Getter @Setter private int combineType;
    @Getter @Setter private String bonusType;
    @Getter @Setter private List<Double> paramVec;

    @Override
    public int getId() {
        return this.avatarId;
    }
}
