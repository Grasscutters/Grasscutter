package emu.grasscutter.game.tower;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Transient;

import java.util.Map;

@Entity
public class TowerData {
    /**
     * the floor players chose
     */
    int currentFloorId;
    int currentLevel;
    @Transient
    int currentLevelId;

    /**
     * floorId - Record
     */
    Map<Integer, TowerLevelRecord> recordMap;

    @Transient
    int entryScene;
}
