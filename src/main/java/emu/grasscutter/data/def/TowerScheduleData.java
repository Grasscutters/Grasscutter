package emu.grasscutter.data.def;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

import java.util.List;

@ResourceType(name = "TowerScheduleExcelConfigData.json")
public class TowerScheduleData extends GameResource {
    private int ScheduleId;
    private List<Integer> EntranceFloorId;
    private List<ScheduleDetail> Schedules;
    private int MonthlyLevelConfigId;
    @Override
    public int getId() {
        return ScheduleId;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.Schedules = this.Schedules.stream()
                .filter(item -> item.getFloorList().size() > 0)
                .toList();
    }

    public int getScheduleId() {
        return ScheduleId;
    }

    public void setScheduleId(int scheduleId) {
        ScheduleId = scheduleId;
    }

    public List<Integer> getEntranceFloorId() {
        return EntranceFloorId;
    }

    public void setEntranceFloorId(List<Integer> entranceFloorId) {
        EntranceFloorId = entranceFloorId;
    }

    public List<ScheduleDetail> getSchedules() {
        return Schedules;
    }

    public void setSchedules(List<ScheduleDetail> schedules) {
        Schedules = schedules;
    }

    public int getMonthlyLevelConfigId() {
        return MonthlyLevelConfigId;
    }

    public void setMonthlyLevelConfigId(int monthlyLevelConfigId) {
        MonthlyLevelConfigId = monthlyLevelConfigId;
    }

    public static class ScheduleDetail{
        private List<Integer> FloorList;

        public List<Integer> getFloorList() {
            return FloorList;
        }

        public void setFloorList(List<Integer> floorList) {
            FloorList = floorList;
        }
    }
}
