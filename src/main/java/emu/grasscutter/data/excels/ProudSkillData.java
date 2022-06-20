package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.FightPropData;
import emu.grasscutter.data.common.ItemParamData;

import java.util.ArrayList;
import java.util.List;

@ResourceType(name = "ProudSkillExcelConfigData.json")
public class ProudSkillData extends GameResource {

    private int proudSkillId;
    private int proudSkillGroupId;
    private int level;
    private int coinCost;
    private int breakLevel;
    private int proudSkillType;
    private String openConfig;
    private List<ItemParamData> costItems;
    private List<String> filterConds;
    private List<String> lifeEffectParams;
    private FightPropData[] addProps;
    private float[] paramList;
    private long[] paramDescList;
    private long nameTextMapHash;

    @Override
    public int getId() {
        return this.proudSkillId;
    }

    public int getProudSkillGroupId() {
        return this.proudSkillGroupId;
    }

    public int getLevel() {
        return this.level;
    }

    public int getCoinCost() {
        return this.coinCost;
    }

    public int getBreakLevel() {
        return this.breakLevel;
    }

    public int getProudSkillType() {
        return this.proudSkillType;
    }

    public String getOpenConfig() {
        return this.openConfig;
    }

    public List<ItemParamData> getCostItems() {
        return this.costItems;
    }

    public List<String> getFilterConds() {
        return this.filterConds;
    }

    public List<String> getLifeEffectParams() {
        return this.lifeEffectParams;
    }

    public FightPropData[] getAddProps() {
        return this.addProps;
    }

    public float[] getParamList() {
        return this.paramList;
    }

    public long[] getParamDescList() {
        return this.paramDescList;
    }

    public long getNameTextMapHash() {
        return this.nameTextMapHash;
    }

    @Override
    public void onLoad() {
        if (this.getOpenConfig() != null & this.getOpenConfig().length() > 0) {
            this.openConfig = "Avatar_" + this.getOpenConfig();
        }
        // Fight props
        ArrayList<FightPropData> parsed = new ArrayList<FightPropData>(this.getAddProps().length);
        for (FightPropData prop : this.getAddProps()) {
            if (prop.getPropType() != null && prop.getValue() != 0f) {
                prop.onLoad();
                parsed.add(prop);
            }
        }
        this.addProps = parsed.toArray(new FightPropData[parsed.size()]);
    }
}
