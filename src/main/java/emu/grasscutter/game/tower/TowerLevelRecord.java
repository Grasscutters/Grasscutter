package emu.grasscutter.game.tower;

import dev.morphia.annotations.Entity;

import java.util.HashMap;
import java.util.Map;

@Entity
public class TowerLevelRecord {
    /**
     * floorId in config
     */
    private int floorId;
    /**
     * LevelId - Stars
     */
    private Map<Integer, Integer> passedLevelMap;

    private int floorStarRewardProgress;

    public TowerLevelRecord setLevelStars(int levelId, int stars) {
        this.passedLevelMap.put(levelId, stars);
        return this;
    }

    public int getStarCount() {
        return this.passedLevelMap.values().stream().mapToInt(Integer::intValue).sum();
    }

    public TowerLevelRecord() {

    }

    public TowerLevelRecord(int floorId) {
        this.floorId = floorId;
        this.passedLevelMap = new HashMap<>();
        this.floorStarRewardProgress = 0;
    }

    public int getFloorId() {
        return this.floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public Map<Integer, Integer> getPassedLevelMap() {
        return this.passedLevelMap;
    }

    public void setPassedLevelMap(Map<Integer, Integer> passedLevelMap) {
        this.passedLevelMap = passedLevelMap;
    }

    public int getFloorStarRewardProgress() {
        return this.floorStarRewardProgress;
    }

    public void setFloorStarRewardProgress(int floorStarRewardProgress) {
        this.floorStarRewardProgress = floorStarRewardProgress;
    }

}
