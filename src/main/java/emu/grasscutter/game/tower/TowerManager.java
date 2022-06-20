package emu.grasscutter.game.tower;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.TowerLevelData;
import emu.grasscutter.game.dungeons.DungeonSettleListener;
import emu.grasscutter.game.dungeons.TowerDungeonSettleListener;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TowerManager {
    private Player player;
    private TowerData towerData;

    public TowerManager(Player player) {
        this.setPlayer(player);
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.towerData = player.getTowerData();
    }

    public int getCurrentFloorId() {
        return this.towerData.currentFloorId;
    }

    public int getCurrentLevelId() {
        return this.towerData.currentLevelId + this.towerData.currentLevel;
    }

    /**
     * form 1-3
     */
    public int getCurrentLevel() {
        return this.towerData.currentLevel + 1;
    }

    private static final List<DungeonSettleListener> towerDungeonSettleListener = List.of(new TowerDungeonSettleListener());

    public Map<Integer, TowerLevelRecord> getRecordMap() {
        Map<Integer, TowerLevelRecord> recordMap = this.towerData.recordMap;
        if (recordMap == null || recordMap.size() == 0) {
            recordMap = new HashMap<>();
            recordMap.put(1001, new TowerLevelRecord(1001));
            this.towerData.recordMap = recordMap;
        }
        return recordMap;
    }

    public void teamSelect(int floor, List<List<Long>> towerTeams) {
        var floorData = GameData.getTowerFloorDataMap().get(floor);
        this.towerData.currentFloorId = floorData.getFloorId();
        this.towerData.currentLevel = 0;
        this.towerData.currentLevelId = GameData.getTowerLevelDataMap().values().stream()
            .filter(x -> x.getLevelGroupId() == floorData.getLevelGroupId() && x.getLevelIndex() == 1)
            .findFirst()
            .map(TowerLevelData::getId)
            .orElse(0);

        if (this.towerData.entryScene == 0) {
            this.towerData.entryScene = this.player.getSceneId();
        }

        this.player.getTeamManager().setupTemporaryTeam(towerTeams);
    }


    public void enterLevel(int enterPointId) {
        var levelData = GameData.getTowerLevelDataMap().get(this.getCurrentLevelId());

        var dungeonId = levelData.getDungeonId();

        this.notifyCurLevelRecordChange();
        // use team user choose
        this.player.getTeamManager().useTemporaryTeam(0);
        this.player.getServer().getDungeonManager().handoffDungeon(this.player, dungeonId,
            towerDungeonSettleListener);

        // make sure user can exit dungeon correctly
        this.player.getScene().setPrevScene(this.towerData.entryScene);
        this.player.getScene().setPrevScenePoint(enterPointId);

        this.player.getSession().send(new PacketTowerEnterLevelRsp(this.towerData.currentFloorId, this.getCurrentLevel()));
        // stop using skill
        this.player.getSession().send(new PacketCanUseSkillNotify(false));
        // notify the cond of stars
        this.player.getSession().send(new PacketTowerLevelStarCondNotify(this.towerData.currentFloorId, this.getCurrentLevel()));
    }

    public void notifyCurLevelRecordChange() {
        this.player.getSession().send(new PacketTowerCurLevelRecordChangeNotify(this.towerData.currentFloorId, this.getCurrentLevel()));
    }

    public void notifyCurLevelRecordChangeWhenDone(int stars) {
        Map<Integer, TowerLevelRecord> recordMap = this.getRecordMap();
        int currentFloorId = this.towerData.currentFloorId;
        if (!recordMap.containsKey(currentFloorId)) {
            recordMap.put(currentFloorId,
                new TowerLevelRecord(currentFloorId).setLevelStars(this.getCurrentLevelId(), stars));
        } else {
            recordMap.put(currentFloorId,
                recordMap.get(currentFloorId).setLevelStars(this.getCurrentLevelId(), stars));
        }

        this.towerData.currentLevel++;

        if (!this.hasNextLevel()) {
            // set up the next floor
            recordMap.putIfAbsent(this.getNextFloorId(), new TowerLevelRecord(this.getNextFloorId()));
            this.player.getSession().send(new PacketTowerCurLevelRecordChangeNotify(this.getNextFloorId(), 1));
        } else {
            this.player.getSession().send(new PacketTowerCurLevelRecordChangeNotify(currentFloorId, this.getCurrentLevel()));
        }
    }

    public boolean hasNextLevel() {
        return this.towerData.currentLevel < 3;
    }

    public int getNextFloorId() {
        return this.player.getServer().getTowerScheduleManager().getNextFloorId(this.towerData.currentFloorId);
    }

    public boolean hasNextFloor() {
        return this.player.getServer().getTowerScheduleManager().getNextFloorId(this.towerData.currentFloorId) > 0;
    }

    public void clearEntry() {
        this.towerData.entryScene = 0;
    }

    public boolean canEnterScheduleFloor() {
        Map<Integer, TowerLevelRecord> recordMap = this.getRecordMap();
        if (!recordMap.containsKey(this.player.getServer().getTowerScheduleManager().getLastEntranceFloor())) {
            return false;
        }
        return recordMap.get(this.player.getServer().getTowerScheduleManager().getLastEntranceFloor())
            .getStarCount() >= 6;
    }

    public void mirrorTeamSetUp(int teamId) {
        // use team user choose
        this.player.getTeamManager().useTemporaryTeam(teamId);
        this.player.sendPacket(new PacketTowerMiddleLevelChangeTeamNotify());
    }
}
