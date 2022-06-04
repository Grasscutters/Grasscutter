package emu.grasscutter.game.managers.ForgingManager;

import dev.morphia.annotations.Entity;

@Entity
public class ActiveForgeData {
    private int queueId;
    private int forgeId;
    private int avatarId;

    private int startTime;
    private int forgeTime;
    // private int finishedCount;
    // private int unfinishedCount;
    // private int nextFinishTimestamp;
    // private int totalFinishTimestamp;

    public int getQueueId() {
        return this.queueId;
    }
    public void setQueueId(int value) {
        this.queueId = value;
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
}
