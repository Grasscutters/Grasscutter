package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ResourceType(
        name = "InvestigationMonsterConfigData.json",
        loadPriority = ResourceType.LoadPriority.LOW)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvestigationMonsterData extends GameResource {
    @Getter(onMethod_ = @Override)
    int id;

    int cityId;
    List<Integer> monsterIdList;
    List<Integer> groupIdList;
    int rewardPreviewId;
    String mapMarkCreateType;
    String monsterCategory;

    CityData cityData;

    @Override
    public void onLoad() {
        this.cityData = GameData.getCityDataMap().get(cityId);
    }
}
