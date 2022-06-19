package emu.grasscutter.game.expedition;

import dev.morphia.annotations.Entity;

@Entity
public class ExpeditionInfo {

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getExpId() {
        return this.expId;
    }

    public void setExpId(int expId) {
        this.expId = expId;
    }

    public int getHourTime() {
        return this.hourTime;
    }

    public void setHourTime(int hourTime) {
        this.hourTime = hourTime;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    private int state;
    private int expId;
    private int hourTime;
    private int startTime;
}
