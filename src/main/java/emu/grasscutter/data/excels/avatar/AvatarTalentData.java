package emu.grasscutter.data.excels.avatar;

import emu.grasscutter.data.*;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.FightPropData;
import java.util.ArrayList;
import lombok.Getter;

@ResourceType(name = "AvatarTalentExcelConfigData.json", loadPriority = LoadPriority.HIGHEST)
public class AvatarTalentData extends GameResource {
    private int talentId;
    private int prevTalent;
    @Getter private long nameTextMapHash;
    @Getter private String icon;
    @Getter private int mainCostItemId;
    @Getter private int mainCostItemCount;
    @Getter private String openConfig;
    @Getter private FightPropData[] addProps;
    @Getter private float[] paramList;

    @Override
    public int getId() {
        return this.talentId;
    }

    public int PrevTalent() {
        return prevTalent;
    }

    @Override
    public void onLoad() {
        ArrayList<FightPropData> parsed = new ArrayList<>(getAddProps().length);
        for (FightPropData prop : getAddProps()) {
            if (prop.getPropType() != null || prop.getValue() == 0f) {
                prop.onLoad();
                parsed.add(prop);
            }
        }
        this.addProps = parsed.toArray(new FightPropData[parsed.size()]);
    }
}
