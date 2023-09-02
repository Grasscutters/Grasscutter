package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import emu.grasscutter.data.common.FightPropData;
import java.util.ArrayList;

@ResourceType(name = "EquipAffixExcelConfigData.json")
public class EquipAffixData extends GameResource {

    private int affixId;
    private int id;
    private int level;
    private long nameTextMapHash;
    private String openConfig;
    private FightPropData[] addProps;
    private float[] paramList;

    @Override
    public int getId() {
        return affixId;
    }

    public int getMainId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public long getNameTextMapHash() {
        return nameTextMapHash;
    }

    public String getOpenConfig() {
        return openConfig;
    }

    public FightPropData[] getAddProps() {
        return addProps;
    }

    public float[] getParamList() {
        return paramList;
    }

    @Override
    public void onLoad() {
        ArrayList<FightPropData> parsed = new ArrayList<FightPropData>(getAddProps().length);
        for (FightPropData prop : getAddProps()) {
            if (prop.getPropType() != null && prop.getValue() != 0f) {
                prop.onLoad();
                parsed.add(prop);
            }
        }
        this.addProps = parsed.toArray(new FightPropData[parsed.size()]);
    }
}
