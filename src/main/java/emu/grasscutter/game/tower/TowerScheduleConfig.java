package emu.grasscutter.game.tower;

import java.util.Date;

public class TowerScheduleConfig {
    private int scheduleId;

    private Date scheduleStartTime;
    private Date nextScheduleChangeTime;


    public int getScheduleId() {
        return this.scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Date getScheduleStartTime() {
        return this.scheduleStartTime;
    }

    public void setScheduleStartTime(Date scheduleStartTime) {
        this.scheduleStartTime = scheduleStartTime;
    }

    public Date getNextScheduleChangeTime() {
        return this.nextScheduleChangeTime;
    }

    public void setNextScheduleChangeTime(Date nextScheduleChangeTime) {
        this.nextScheduleChangeTime = nextScheduleChangeTime;
    }
}
