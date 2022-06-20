package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.FightPropData;
import emu.grasscutter.data.common.ItemParamData;

import java.util.ArrayList;

@ResourceType(name = "AvatarPromoteExcelConfigData.json")
public class AvatarPromoteData extends GameResource {

    private int avatarPromoteId;
    private int promoteLevel;
    private int scoinCost;
    private ItemParamData[] costItems;
    private int unlockMaxLevel;
    private FightPropData[] addProps;
    private int requiredPlayerLevel;

    @Override
    public int getId() {
        return (this.avatarPromoteId << 8) + this.promoteLevel;
    }

    public int getAvatarPromoteId() {
        return this.avatarPromoteId;
    }

    public int getPromoteLevel() {
        return this.promoteLevel;
    }

    public ItemParamData[] getCostItems() {
        return this.costItems;
    }

    public int getCoinCost() {
        return this.scoinCost;
    }

    public FightPropData[] getAddProps() {
        return this.addProps;
    }

    public int getUnlockMaxLevel() {
        return this.unlockMaxLevel;
    }

    public int getRequiredPlayerLevel() {
        return this.requiredPlayerLevel;
    }

    @Override
    public void onLoad() {
        // Trim item params
        ArrayList<ItemParamData> trim = new ArrayList<>(this.getAddProps().length);
        for (ItemParamData itemParam : this.getCostItems()) {
            if (itemParam.getId() == 0) {
                continue;
            }
            trim.add(itemParam);
        }
        this.costItems = trim.toArray(new ItemParamData[trim.size()]);
        // Trim fight prop data (just in case)
        ArrayList<FightPropData> parsed = new ArrayList<>(this.getAddProps().length);
        for (FightPropData prop : this.getAddProps()) {
            if (prop.getPropType() != null && prop.getValue() != 0f) {
                prop.onLoad();
                parsed.add(prop);
            }
        }
        this.addProps = parsed.toArray(new FightPropData[parsed.size()]);
    }
}
