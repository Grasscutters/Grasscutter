package emu.grasscutter.data.excels.avatar;

import emu.grasscutter.data.*;
import emu.grasscutter.data.common.*;
import java.util.ArrayList;
import lombok.Getter;

@ResourceType(name = "AvatarPromoteExcelConfigData.json")
public class AvatarPromoteData extends GameResource {

    @Getter private int avatarPromoteId;
    @Getter private int promoteLevel;
    private int scoinCost;
    @Getter private ItemParamData[] costItems;
    @Getter private int unlockMaxLevel;
    @Getter private FightPropData[] addProps;
    @Getter private int requiredPlayerLevel;

    @Override
    public int getId() {
        return (avatarPromoteId << 8) + promoteLevel;
    }

    public int getCoinCost() {
        return scoinCost;
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
        // Trim fight prop data (just in case)
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
