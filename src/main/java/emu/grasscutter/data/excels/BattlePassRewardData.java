package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ResourceType(name = "BattlePassRewardExcelConfigData.json")
@Getter
public class BattlePassRewardData extends GameResource {
    private int indexId;
    private int level;
    private List<Integer> freeRewardIdList;
    private List<Integer> paidRewardIdList;

    @Override
    public int getId() {
        // Reward ID is a combination of index and level.
        // We do this to get a unique ID.
        return this.indexId * 100 + this.level;
    }

    @Override
    public void onLoad() {
    }
}
