package emu.grasscutter.data.excels.tower;

import emu.grasscutter.data.*;
import java.util.List;
import lombok.*;

@ResourceType(name = "TowerLevelExcelConfigData.json")
@Getter
public class TowerLevelData extends GameResource {

    private int levelId;
    private int levelIndex;
    private int levelGroupId;
    private int dungeonId;
    private List<TowerLevelCond> conds;
    private int monsterLevel;

    public static class TowerLevelCond {
        private TowerCondType towerCondType;
        private List<Integer> argumentList;
    }

    public enum TowerCondType {
        TOWER_COND_NONE,
        TOWER_COND_CHALLENGE_LEFT_TIME_MORE_THAN,
        TOWER_COND_LEFT_HP_GREATER_THAN
    }

    // Not actual data in TowerLevelExcelConfigData.
    // Just packaging condition parameters for convenience.
    @Getter
    public class TowerCondTimeParams {
        private final int param1;
        private final int minimumTimeInSeconds;

        public TowerCondTimeParams(int param1, int minimumTimeInSeconds) {
            this.param1 = param1;
            this.minimumTimeInSeconds = minimumTimeInSeconds;
        }
    }

    @Getter
    public class TowerCondHpParams {
        private final int sceneId;
        private final int configId;
        private final int minimumHpPercentage;

        public TowerCondHpParams(int sceneId, int configId, int minimumHpPercentage) {
            this.sceneId = sceneId;
            this.configId = configId;
            this.minimumHpPercentage = minimumHpPercentage;
        }
    }

    @Override
    public int getId() {
        return this.getLevelId();
    }

    public TowerCondType getCondType(int star) {
        if (star < 0 || conds == null || star >= conds.size()) {
            return TowerCondType.TOWER_COND_NONE;
        }
        var condType = conds.get(star).towerCondType;
        return condType == null ? TowerCondType.TOWER_COND_NONE : condType;
    }

    public TowerCondTimeParams getTimeCond(int star) {
        if (star < 0 || conds == null || star >= conds.size()) {
            return null;
        }
        var params = conds.get(star).argumentList;
        return new TowerCondTimeParams(params.get(0), params.get(1));
    }

    public TowerCondHpParams getHpCond(int star) {
        if (star < 0 || conds == null || star >= conds.size()) {
            return null;
        }
        var params = conds.get(star).argumentList;
        return new TowerCondHpParams(params.get(0), params.get(1), params.get(2));
    }
}
