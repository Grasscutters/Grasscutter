package emu.grasscutter.data.excels;

import java.util.ArrayList;
import java.util.List;

import dev.morphia.annotations.Transient;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.FightPropData;
import emu.grasscutter.data.common.ItemParamData;
import lombok.Getter;

@ResourceType(name = "ProudSkillExcelConfigData.json")
public class ProudSkillData extends GameResource {
    private int proudSkillId;
    @Getter private int proudSkillGroupId;
    @Getter private int level;
    @Getter private int coinCost;
    @Getter private int breakLevel;
    @Getter private int proudSkillType;
    @Getter private String openConfig;
    @Getter private List<ItemParamData> costItems;
    @Getter private List<String> filterConds;
    @Getter private List<String> lifeEffectParams;
    @Getter private FightPropData[] addProps;
    @Getter private float[] paramList;
    @Getter private long[] paramDescList;
    @Getter private long nameTextMapHash;
    @Transient private Iterable<ItemParamData> totalCostItems;

    @Override
    public int getId() {
        return proudSkillId;
    }

    public Iterable<ItemParamData> getTotalCostItems() {
        if (this.totalCostItems == null) {
            ArrayList<ItemParamData> total = (this.costItems != null) ? new ArrayList<>(this.costItems) : new ArrayList<>(1);
            if (this.coinCost > 0)
                total.add(new ItemParamData(202, this.coinCost));
            this.totalCostItems = total;
        }
        return this.totalCostItems;
    }

    @Override
    public void onLoad() {
        // Fight props
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
