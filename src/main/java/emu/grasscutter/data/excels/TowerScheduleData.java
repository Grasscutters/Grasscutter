package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

import java.util.List;

@ResourceType(name = "TowerScheduleExcelConfigData.json")
public class TowerScheduleData extends GameResource {
    private int scheduleId;
    private List<Integer> entranceFloorId;
    private List<ScheduleDetail> schedules;
    private int monthlyLevelConfigId;

    @Override
    public int getId() {
        return this.scheduleId;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.schedules = this.schedules.stream()
            .filter(item -> item.getFloorList().size() > 0)
            .toList();
    }

    public int getScheduleId() {
        return this.scheduleId;
    }

    public List<Integer> getEntranceFloorId() {
        return this.entranceFloorId;
    }

    public List<ScheduleDetail> getSchedules() {
        return this.schedules;
    }

    public int getMonthlyLevelConfigId() {
        return this.monthlyLevelConfigId;
    }

    public static class ScheduleDetail {
        private List<Integer> floorList;

        public List<Integer> getFloorList() {
            return this.floorList;
        }
    }
}
