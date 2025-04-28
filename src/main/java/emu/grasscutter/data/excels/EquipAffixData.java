package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import emu.grasscutter.data.common.FightPropData;
import java.util.ArrayList;
import lombok.Getter;

@ResourceType(name = "EquipAffixExcelConfigData.json")
public class EquipAffixData extends GameResource {

    private int affixId;
    private int id;
    @Getter private int level;
    @Getter private long nameTextMapHash;
    @Getter private String openConfig;
    @Getter private FightPropData[] addProps;
    @Getter private float[] paramList;

    @Override
    public int getId() {
        return affixId;
    }

    public int getMainId() {
        return id;
    }

    @Override
    public void onLoad() {
        ArrayList<FightPropData> parsed = new ArrayList<>(getAddProps().length);
        for (FightPropData prop : getAddProps()) {
            if (prop.getPropType() != null && prop.getValue() != 0f) {
                prop.onLoad();
                parsed.add(prop);
            }
        }
        this.addProps = parsed.toArray(new FightPropData[parsed.size()]);
    }
}
