package emu.grasscutter.data.excels.tower;

import emu.grasscutter.data.*;
import java.util.List;

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
                this.schedules.stream().filter(item -> item.getFloorList().size() > 0).toList();
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public List<Integer> getEntranceFloorId() {
        return entranceFloorId;
    }

    public List<ScheduleDetail> getSchedules() {
        return schedules;
    }

    public int getMonthlyLevelConfigId() {
        return monthlyLevelConfigId;
    }

    public static class ScheduleDetail {
        private List<Integer> floorList;

        public List<Integer> getFloorList() {
            return floorList;
        }
    }
}
