package emu.grasscutter.data.def;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.Data;

import java.util.List;

@ResourceType(name = "InvestigationMonsterConfigData.json")
@Data
public class InvestigationMonsterData extends GameResource {
    private int Id;
    private int CityId;
    private List<Integer> MonsterIdList;
    private List<Integer> GroupIdList;
    private int RewardPreviewId;
    private String MapMarkCreateType;
    private String MonsterCategory;

    @Override
    public int getId() {
        return this.Id;
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }
}
