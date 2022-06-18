package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
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
        return this.affixId;
    }

    public int getMainId() {
        return this.id;
    }

    public int getLevel() {
        return this.level;
    }

    public long getNameTextMapHash() {
        return this.nameTextMapHash;
    }

    public String getOpenConfig() {
        return this.openConfig;
    }

    public FightPropData[] getAddProps() {
        return this.addProps;
    }

    public float[] getParamList() {
        return this.paramList;
    }

    @Override
    public void onLoad() {
        ArrayList<FightPropData> parsed = new ArrayList<FightPropData>(this.getAddProps().length);
        for (FightPropData prop : this.getAddProps()) {
            if (prop.getPropType() != null || prop.getValue() == 0f) {
                prop.onLoad();
                parsed.add(prop);
            }
        }
        this.addProps = parsed.toArray(new FightPropData[parsed.size()]);
    }
}
