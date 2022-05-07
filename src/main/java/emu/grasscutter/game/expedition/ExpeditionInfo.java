package emu.grasscutter.game.expedition;

import dev.morphia.annotations.Entity;

@Entity
public class ExpeditionInfo {

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getExpId() {
        return expId;
    }

    public void setExpId(int expId) {
        this.expId = expId;
    }

    public int getHourTime() {
        return hourTime;
    }

    public void setHourTime(int hourTime) {
        this.hourTime = hourTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    private int state;
    private int expId;
    private int hourTime;
    private int startTime;
}
