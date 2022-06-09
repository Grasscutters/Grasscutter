package emu.grasscutter.game.tower;

import java.util.Map;

import dev.morphia.annotations.Transient;

public class TowerData{
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
    Map<Integer, TowerLevelRecord> recordMap = new HashMap<>();

    @Transient
    int entryScene;
}