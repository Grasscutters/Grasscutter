package emu.grasscutter.game.managers.forging;

import dev.morphia.annotations.Entity;

@Entity
public class ActiveForgeData {
    private int forgeId;
    private int avatarId;
    private int count;

    private int startTime;
    private int forgeTime;

    private int lastUnfinishedCount;
    private boolean changed;

    public int getFinishedCount(int currentTime) {
        int timeDelta = currentTime - this.startTime;
        int finishedCount = timeDelta / this.forgeTime;

        return Math.min(finishedCount, this.count);
    }

    public int getUnfinishedCount(int currentTime) {
        return this.count - this.getFinishedCount(currentTime);
    }

    public int getNextFinishTimestamp(int currentTime) {
        return (currentTime >= this.getTotalFinishTimestamp())
                ? this.getTotalFinishTimestamp()
                : (this.getFinishedCount(currentTime) * this.forgeTime + this.forgeTime + this.startTime);
    }

    public int getTotalFinishTimestamp() {
        return this.startTime + this.forgeTime * this.count;
    }

    public int getForgeId() {
        return this.forgeId;
    }

    public void setForgeId(int value) {
        this.forgeId = value;
    }

    public int getAvatarId() {
        return this.avatarId;
    }

    public void setAvatarId(int value) {
        this.avatarId = value;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int value) {
        this.count = value;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public void setStartTime(int value) {
        this.startTime = value;
    }

    public int getForgeTime() {
        return this.forgeTime;
    }

    public void setForgeTime(int value) {
        this.forgeTime = value;
    }

    public boolean isChanged() {
        return this.changed;
    }

    public void setChanged(boolean value) {
        this.changed = value;
    }

    public boolean updateChanged(int currentTime) {
        int currentUnfinished = this.getUnfinishedCount(currentTime);

        if (currentUnfinished != this.lastUnfinishedCount) {
            this.changed = true;
            this.lastUnfinishedCount = currentUnfinished;
        }

        return this.changed;
    }
}
