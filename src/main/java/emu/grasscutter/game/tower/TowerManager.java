package emu.grasscutter.game.tower;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.tower.TowerLevelData;
import emu.grasscutter.game.dungeons.*;
import emu.grasscutter.game.player.*;
import emu.grasscutter.server.packet.send.*;
import java.util.*;
import lombok.*;

public class TowerManager extends BasePlayerManager {
    private static final List<DungeonSettleListener> towerDungeonSettleListener =
            List.of(new TowerDungeonSettleListener());

    private int currentPossibleStars = 0;
    @Getter private boolean inProgress;
    @Getter private int currentTimeLimit;

    public TowerManager(Player player) {
        super(player);
    }

    public TowerData getTowerData() {
        return this.getPlayer().getTowerData();
    }

    public int getCurrentFloorId() {
        return this.getTowerData().currentFloorId;
    }

    /** floor number: 1 - 12 * */
    public int getCurrentFloorNumber() {
        return GameData.getTowerFloorDataMap().get(getCurrentFloorId()).getFloorIndex();
    }

    public int getCurrentLevelId() {
        return this.getTowerData().currentLevelId + this.getTowerData().currentLevel;
    }

    /** form 1-3 */
    public int getCurrentLevel() {
        return this.getTowerData().currentLevel + 1;
    }

    public void onTick() {
        var challenge = player.getScene().getChallenge();
        if (!inProgress || challenge == null || !challenge.inProgress()) return;

        // Check star conditions and notify client if any failed.
        int stars = getCurLevelStars();
        while (stars < currentPossibleStars) {
            player
                    .getSession()
                    .send(
                            new PacketTowerLevelStarCondNotify(
                                    getTowerData().currentFloorId, getCurrentLevel(), currentPossibleStars));
            currentPossibleStars--;
        }
    }

    public void onBegin() {
        var challenge = player.getScene().getChallenge();
        inProgress = true;
        currentTimeLimit = challenge.getTimeLimit();
    }

    public void onEnd() {
        inProgress = false;
    }

    public Map<Integer, TowerLevelRecord> getRecordMap() {
        Map<Integer, TowerLevelRecord> recordMap = getTowerData().recordMap;
        if (recordMap == null || recordMap.isEmpty()) {
            recordMap = new HashMap<>();
            recordMap.put(1001, new TowerLevelRecord(1001));
            getTowerData().recordMap = recordMap;
        }
        return recordMap;
    }

    public void teamSelect(int floor, List<List<Long>> towerTeams) {
        var floorData = GameData.getTowerFloorDataMap().get(floor);
        getTowerData().currentFloorId = floorData.getFloorId();
        getTowerData().currentLevel = 0;
        getTowerData().currentLevelId =
                GameData.getTowerLevelDataMap().values().stream()
                        .filter(
                                x -> x.getLevelGroupId() == floorData.getLevelGroupId() && x.getLevelIndex() == 1)
                        .findFirst()
                        .map(TowerLevelData::getId)
                        .orElse(0);

        if (getTowerData().entryScene == 0) {
            getTowerData().entryScene = player.getSceneId();
        }

        player.getTeamManager().setupTemporaryTeam(towerTeams);
    }

    public TowerLevelData getCurrentTowerLevelDataMap() {
        return GameData.getTowerLevelDataMap().get(getCurrentLevelId());
    }

    public int getCurrentMonsterLevel() {
        // monsterLevel given in TowerLevelExcelConfigData.json is off by one.
        return getCurrentTowerLevelDataMap().getMonsterLevel() + 1;
    }

    public void enterLevel(int enterPointId) {
        var levelData = getCurrentTowerLevelDataMap();

        var dungeonId = levelData.getDungeonId();

        notifyCurLevelRecordChange();
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
        currentPossibleStars = 3;
        player
                .getSession()
                .send(
                        new PacketTowerLevelStarCondNotify(
                                getTowerData().currentFloorId, getCurrentLevel(), currentPossibleStars + 1));
    }

    public void notifyCurLevelRecordChange() {
        player
                .getSession()
                .send(
                        new PacketTowerCurLevelRecordChangeNotify(
                                getTowerData().currentFloorId, getCurrentLevel()));
    }

    public int getCurLevelStars() {
        var scene = player.getScene();
        var challenge = scene.getChallenge();
        if (challenge == null) {
            Grasscutter.getLogger().error("getCurLevelStars: no challenge registered!");
            return 0;
        }

        var levelData = getCurrentTowerLevelDataMap();
        // 0-based indexing. "star" = 0 means checking for 1-star conditions.
        int star;
        for (star = 2; star >= 0; star--) {
            var cond = levelData.getCondType(star);
            if (cond == TowerLevelData.TowerCondType.TOWER_COND_CHALLENGE_LEFT_TIME_MORE_THAN) {
                var params = levelData.getTimeCond(star);
                var timeRemaining =
                        challenge.getTimeLimit() - (scene.getSceneTimeSeconds() - challenge.getStartedAt());
                if (timeRemaining >= params.getMinimumTimeInSeconds()) {
                    break;
                }
            } else if (cond == TowerLevelData.TowerCondType.TOWER_COND_LEFT_HP_GREATER_THAN) {
                var params = levelData.getHpCond(star);
                var hpPercent = challenge.getGuardEntityHpPercent();
                if (hpPercent >= params.getMinimumHpPercentage()) {
                    break;
                }
            } else {
                Grasscutter.getLogger()
                        .error(
                                "getCurLevelStars: Tower level {} has no or unknown condition defined for {} stars",
                                getCurrentLevelId(),
                                star + 1);
                continue;
            }
        }
        return star + 1;
    }

    public void notifyCurLevelRecordChangeWhenDone(int stars) {
        Map<Integer, TowerLevelRecord> recordMap = this.getRecordMap();
        int currentFloorId = getTowerData().currentFloorId;
        if (!recordMap.containsKey(currentFloorId)) {
            recordMap.put(
                    currentFloorId,
                    new TowerLevelRecord(currentFloorId).setLevelStars(getCurrentLevelId(), stars));
        } else {
            // Only update record if better than previous
            var prevRecord = recordMap.get(currentFloorId);
            var passedLevelMap = prevRecord.getPassedLevelMap();
            int prevStars = 0;
            if (passedLevelMap.containsKey(getCurrentLevelId())) {
                prevStars = prevRecord.getLevelStars(getCurrentLevelId());
            }
            if (stars > prevStars) {
                recordMap.put(currentFloorId, prevRecord.setLevelStars(getCurrentLevelId(), stars));
            }
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

    public int getNextFloorId() {
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
        getTowerData().entryScene = 0;
    }

    public boolean canEnterScheduleFloor() {
        Map<Integer, TowerLevelRecord> recordMap = this.getRecordMap();
        if (!recordMap.containsKey(this.player.getServer().getTowerSystem().getLastEntranceFloor())) {
            return false;
        }
        return recordMap
                        .get(this.player.getServer().getTowerSystem().getLastEntranceFloor())
                        .getStarCount()
                >= 6;
    }

    public void mirrorTeamSetUp(int teamId) {
        // use team user choose
        player.getTeamManager().useTemporaryTeam(teamId);
        player.sendPacket(new PacketTowerMiddleLevelChangeTeamNotify());
    }
}
