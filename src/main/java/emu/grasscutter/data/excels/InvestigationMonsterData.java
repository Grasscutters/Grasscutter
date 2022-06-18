package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.Data;

import java.util.List;

@ResourceType(name = "InvestigationMonsterConfigData.json")
@Data
public class InvestigationMonsterData extends GameResource {
    private int id;
    private int cityId;
    private List<Integer> monsterIdList;
    private List<Integer> groupIdList;
    private int rewardPreviewId;
    private String mapMarkCreateType;
    private String monsterCategory;

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }
}
