package emu.grasscutter.game.managers.cooking;

import dev.morphia.annotations.Entity;
import lombok.Getter;

@Entity
public class ActiveCookCompoundData {
    private final int costTime;
    @Getter private final int compoundId;
    @Getter private int totalCount;
    private int startTime;

    public ActiveCookCompoundData(int compoundId, int processTime, int count, int startTime) {
        this.compoundId = compoundId;
        this.costTime = processTime;
        this.totalCount = count;
        this.startTime = startTime;
    }

    public int getOutputCount(int currentTime) {
        int cnt = (currentTime - startTime) / costTime;
        if (cnt > totalCount) return totalCount;
        else return cnt;
    }

    public int getWaitCount(int currentTime) {
        return totalCount - getOutputCount(currentTime);
    }

    /** Get the timestamp of next output. If all finished,return 0 */
    public int getOutputTime(int currentTime) {
        int cnt = getOutputCount(currentTime);
        if (cnt == totalCount) return 0;
        else return startTime + (cnt + 1) * costTime;
    }

    public void addCompound(int count, int currentTime) {
        if (getOutputCount(currentTime) == totalCount) startTime = currentTime - totalCount * costTime;
        totalCount += count;
    }

    /**
     * Take away all finished compound.
     *
     * @return The number of finished items.
     */
    public int takeCompound(int currentTime) {
        int count = getOutputCount(currentTime);
        startTime += costTime * count;
        totalCount -= count;
        return count;
    }
}
