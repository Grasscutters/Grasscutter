package emu.grasscutter.game.tower;

import dev.morphia.annotations.Entity;
import java.util.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

    public int getLevelStars(int levelId) {
        return passedLevelMap.get(levelId);
    }

    public int getStarCount() {
        return passedLevelMap.values().stream().mapToInt(Integer::intValue).sum();
    }
}
