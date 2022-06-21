package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ResourceType(name = "BattlePassRewardExcelConfigData.json")
@Getter
@Setter
public class BattlePassRewardExcelConfigData extends GameResource {
    private int indexId;
    private int level;
    private List<Integer> freeRewardIdList;
    private List<Integer> paidRewardIdList;

    @Override
    public int getId() {
        return this.level;
    }

    @Override
    public void onLoad() {
    }
}
