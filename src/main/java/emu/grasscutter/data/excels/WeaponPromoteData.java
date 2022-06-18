package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.FightPropData;
import emu.grasscutter.data.common.ItemParamData;

import java.util.ArrayList;

@ResourceType(name = "WeaponPromoteExcelConfigData.json")
public class WeaponPromoteData extends GameResource {

    private int weaponPromoteId;
    private int promoteLevel;
    private ItemParamData[] costItems;
    private int coinCost;
    private FightPropData[] addProps;
    private int unlockMaxLevel;
    private int requiredPlayerLevel;

    @Override
    public int getId() {
        return (this.weaponPromoteId << 8) + this.promoteLevel;
    }

    public int getWeaponPromoteId() {
        return this.weaponPromoteId;
    }

    public int getPromoteLevel() {
        return this.promoteLevel;
    }

    public ItemParamData[] getCostItems() {
        return this.costItems;
    }

    public int getCoinCost() {
        return this.coinCost;
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
        // Trim fight prop data
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
