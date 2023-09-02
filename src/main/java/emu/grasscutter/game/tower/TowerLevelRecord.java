package emu.grasscutter.game.tower;

import dev.morphia.annotations.Entity;
import java.util.*;

@Entity
public class TowerLevelRecord {
    /** floorId in config */
    private int floorId;
    /** LevelId - Stars */
    private Map<Integer, Integer> passedLevelMap;

    private int floorStarRewardProgress;

    public TowerLevelRecord() {}

    public TowerLevelRecord(int floorId) {
        this.floorId = floorId;
        this.passedLevelMap = new HashMap<>();
        this.floorStarRewardProgress = 0;
    }

    public TowerLevelRecord setLevelStars(int levelId, int stars) {
        passedLevelMap.put(levelId, stars);
        return this;
    }

    public int getStarCount() {
        return passedLevelMap.values().stream().mapToInt(Integer::intValue).sum();
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public Map<Integer, Integer> getPassedLevelMap() {
        return passedLevelMap;
    }

    public void setPassedLevelMap(Map<Integer, Integer> passedLevelMap) {
        this.passedLevelMap = passedLevelMap;
    }

    public int getFloorStarRewardProgress() {
        return floorStarRewardProgress;
    }

    public void setFloorStarRewardProgress(int floorStarRewardProgress) {
        this.floorStarRewardProgress = floorStarRewardProgress;
    }
}
