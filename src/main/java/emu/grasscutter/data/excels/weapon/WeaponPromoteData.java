package emu.grasscutter.data.excels.weapon;

import emu.grasscutter.data.*;
import emu.grasscutter.data.common.*;
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
        return (weaponPromoteId << 8) + promoteLevel;
    }

    public int getWeaponPromoteId() {
        return weaponPromoteId;
    }

    public int getPromoteLevel() {
        return promoteLevel;
    }

    public ItemParamData[] getCostItems() {
        return costItems;
    }

    public int getCoinCost() {
        return coinCost;
    }

    public FightPropData[] getAddProps() {
        return addProps;
    }

    public int getUnlockMaxLevel() {
        return unlockMaxLevel;
    }

    public int getRequiredPlayerLevel() {
        return requiredPlayerLevel;
    }

    @Override
    public void onLoad() {
        // Trim item params
        ArrayList<ItemParamData> trim = new ArrayList<>(getAddProps().length);
        for (ItemParamData itemParam : getCostItems()) {
            if (itemParam.getId() == 0) {
                continue;
            }
            trim.add(itemParam);
        }
        this.costItems = trim.toArray(new ItemParamData[trim.size()]);
        // Trim fight prop data
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
