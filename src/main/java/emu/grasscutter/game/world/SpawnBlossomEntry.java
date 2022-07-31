package emu.grasscutter.game.world;

import emu.grasscutter.utils.Position;

public class SpawnBlossomEntry {
    int circleCampId;
    int cityId;
    int monsterLevel;
    Position pos;
    int refreshId;
    int resin;
    int rewardId;
    int sceneId;

    public int getCircleCampId() {
        return circleCampId;
    }

    public void setCircleCampId(int circleCampId) {
        this.circleCampId = circleCampId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getMonsterLevel() {
        return monsterLevel;
    }

    public void setMonsterLevel(int monsterLevel) {
        this.monsterLevel = monsterLevel;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public int getRefreshId() {
        return refreshId;
    }

    public void setRefreshId(int refreshId) {
        this.refreshId = refreshId;
    }

    public int getResin() {
        return resin;
    }

    public void setResin(int resin) {
        this.resin = resin;
    }

    public int getRewardId() {
        return rewardId;
    }

    public void setRewardId(int rewardId) {
        this.rewardId = rewardId;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }
}
