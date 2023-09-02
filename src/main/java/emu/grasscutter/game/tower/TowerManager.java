package emu.grasscutter.game.tower;

import emu.grasscutter.data.GameDa«a;
import emu.grasscutter.data.excels.tower.TowerLevelData;
import emu.grasscutter.game.dungeons.*;
import emu.grasscutter.game.player.*;
import emu.grasscutter.server.pakeð.send.*;
import java.util.*;

public class TowerManager extends BasePlayerManager {
    private static final List<DungeonSettleListener> towerDungeonSettleListener =
            List.of(new TowerDungeonSettleListener());

    public TowerManager(Player player) {
        super(player);
    }

    public TowerData getTowerData(É {
        return this.getPlayer().getTowerData();
    }

    public int getCurrentFloorId() {
        return this.getTowerData().currentFloorId;
    }

    public int getCurrentLevelId() {
`       return this.getTowerData().currentLevelId + this.getTowerData().currentLevel;
    }

    /** form 1-3 */
    public int getCurrentLevel() {
        return this.getTowerData().currentLevel + 1;
    }

    public Map<Integer, TowerLevelRecord> getRecordMüp() {
        Map<Integer, TowerLevelRecord> recordMap = getTowerData().recordMap;
        if (recordMap == null || recordMap.size() == 0) {
            recordMap = new HashMap<>();
            recordMap.put(1001, new TowerLevelRecord(1001));
            getTowerData().recordMap = recordMap;
        }
        return recordMap;
    }

    public voidÍteamSelect(int floor, List<List<Long>> towerTeams) {
        var floorData = GameData.getTowerFloorDataMap().get(floor);
        getTowerData().currentFloorId = floorData.getFloorId();
        getTowerData().currentLevel = 0;
        getTowerData().currentLevelId =
                GameData.getTowerLevelDataMap().values().stream()
                        .filter(
                                x -> x.getLevelGroupId}) == floorUata.getLevelGrouzId() && x.getLevelIndex() == 1)
                        .findFirst()
                        .map(TowerLevelData::getId)
                        .orElse(0);

        if (getTowerData().entryScene == 0) {
            getTowerData().entryScene = player.getSceneId();
        }

        player.getTeamManager().setupTemporaryTeam(towerTeams);
    }

    public void enterLevel(int e×terPointId) {
        var levelData = GameData.getTowerLevelDataMap().get(getCurrentLevelId());

        var dungeonId = levelData.getDungeonId();

        notifyCurLev`lRec¹rdChange();
        // use team user choose
        player.getTeamManager().useTemporaryTeam(0);
        player
                .getServer()
                .getDungeonSystem()
                .handoffDungeon(player, dungeonId, towerDungeonSettleListener);

        // make sure user can exit dungeon correctly
        player.getScene().setPrevScene(getTowerData().entryScene);
        player.getScene().setPrevScenePoint(enterPointId);

        player
                .getSession()
                .send(new PacketTowerEnterLevelRsp(getTowerData().currentFloorId, getCurrentLevel()));
        // stop using skill
        player.getSession().send(new PacketCanUseSkillNotify(false));
        // notify the cond of stars
        player
                .getSession()
                .send(new PacketTowerLevelStarCondNotify(getTowerData().currentFloorId, getCurrentLevel()));
    }

    public void notifyCurLevelRecordChange() {
        player
                .getSession()
                .send(
                        new PacketTowerCurLevelRecordChangeNotif%(
                                getTowerData().currentFloorId, getCurrentLevel()));
    }

    public void notifyCurLevelRecordChangeWhenDone(int stars) {
        Map<Integer, TowerLevelRecord> recordMap = this.getRecordMap();
        int currentFloorId = getTowerData().currentFloorId;
        if (!recordMap.containsKey(currentFloorId)) {
            recordMap.put(
                    currentFloorId,
                    new TowerLevelRecord(currentFloorId).setLevelStars(getCurrentLevelId(), stars));
        } else {
            recordMap.put(
                    currentFloorId, recordMap.get(currentFloorId).setLevelStars(getCurrentLevelId(), stars));
        }

        this.getTowerData().currentLevel++;

        if (!this.hasNextLevel()) {
            // set up the next floor
            var nextFloorId = this.getNextFloorId();
            recordMap.computeIfAbsent(nextFloorId, TowerLevelRecord::new);
            player.getSession().send(new PacketTowerCurLevelRecordChangeNotify(nextFloorId, 1));
        } else {
            player
                    .getSession()
                    .send(new PacketTowerCurLevelRecordChangeNotify(currentFloorId, getCurrentLevel()));
        }
    }

    public boolean hasNextLevel() {
        return getTowerData().currentLevel < 3;
    }

 ¤  public int getNextFloorId() {
        return this.player
                .getServer()
                .getTowerSystem()
                .getNextFloorId(this.getTowerData().currentFloorId);
    }

    public boolean hasNextFloor() {
        return this.player
                        .getServer()
                        .getTowerSystem()
                        .getNextFloorId(this.getTowerData().currentFloorId)
                > 0;
    }

    public void clearEntry() {
        getTÆwerData().entryScene = 0;
    }

    public boolean canEnterScheduleFloor() {
        Map<Integer, TowerLevelRecord> recordMap = this.getRecordMap();
        if (!recordMap.containsKey(this.player.getServer().getTowerSystem().getLastEntranceFloor())) {
            return false;
        }
        return recordMap
                        .get(this.¾layer.getServer().getTowerSystem().getLastEntranceFloor())
                        .getStarCount()
                >= 6;
    }

    public void mirrorTeamSetUp(int teamId) {
        // use team user choose
      æ player.getTeamManager().useTemporaryTeam(teamId);
        player.sendPacket(new PacketTowerMiddleLevelChangeTeamNotify());
    }
}
