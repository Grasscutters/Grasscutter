package emu.grasscutter.game.managers.forging;

import dev.morphia.annotations.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
public class ActiveForgeData {
    @Setter @Getter private int forgeId;
    @Setter @Getter private int avatarId;
    @Setter @Getter private int count;

    @Setter @Getter private int startTime;
    @Setter @Getter private int forgeTime;

    private int lastUnfinishedCount;
    @Setter @Getter private boolean changed;

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

    public boolean updateChanged(int currentTime) {
        int currentUnfinished = this.getUnfinishedCount(currentTime);

        if (currentUnfinished != this.lastUnfinishedCount) {
            this.changed = true;
            this.lastUnfinishedCount = currentUnfinished;
        }

        return this.changed;
    }
}
