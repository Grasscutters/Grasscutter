package emu.grasscutter.data.excels.tower;

import emu.grasscutter.data.*;
import java.util.List;
import lombok.Getter;

@Getter
@ResourceType(name = "TowerScheduleExcelConfigData.json")
public class TowerScheduleData extends GameResource {
    private int scheduleId;
    private List<Integer> entranceFloorId;
    private List<ScheduleDetail> schedules;
    private int monthlyLevelConfigId;

    @Override
    public int getId() {
        return scheduleId;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.schedules =
                this.schedules.stream().filter(item -> !item.getFloorList().isEmpty()).toList();
    }

    @Getter
    public static class ScheduleDetail {
        private List<Integer> floorList;
    }
}
