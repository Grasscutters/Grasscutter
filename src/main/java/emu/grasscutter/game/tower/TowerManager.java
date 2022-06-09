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
        setPlayer(player);
    }
    public void setPlayer(Player player) {
        this.player = player;
        this.towerData = player.getTowerData();
    }

    public int getCurrentFloorId() {
        return towerData.currentFloorId;
    }

    public int getCurrentLevelId(){
        return towerData.currentLevelId + towerData.currentLevel;
    }

    /**
     * form 1-3
     */
    public int getCurrentLevel(){
        return towerData.currentLevel + 1;
    }
    private static final List<DungeonSettleListener> towerDungeonSettleListener = List.of(new TowerDungeonSettleListener());
	
    public Map<Integer, TowerLevelRecord> getRecordMap() {
        Map<Integer, TowerLevelRecord> recordMap = towerData.recordMap;
        if(recordMap == null || recordMap.size()==0){
            recordMap = new HashMap<>();
            recordMap.put(1001, new TowerLevelRecord(1001));
            towerData.recordMap = recordMap;
        }
        return recordMap;
    }

    public void teamSelect(int floor, List<List<Long>> towerTeams) {
        var floorData = GameData.getTowerFloorDataMap().get(floor);
        towerData.currentFloorId = floorData.getFloorId();
        towerData.currentLevel = 0;
        towerData.currentLevelId = GameData.getTowerLevelDataMap().values().stream()
                .filter(x -> x.getLevelGroupId() == floorData.getLevelGroupId() && x.getLevelIndex() == 1)
                .findFirst()
                .map(TowerLevelData::getId)
                .orElse(0);

        if (towerData.entryScene == 0){
            towerData.entryScene = player.getSceneId();
        }

        player.getTeamManager().setupTemporaryTeam(towerTeams);
    }


    public void enterLevel(int enterPointId) {
        var levelData = GameData.getTowerLevelDataMap().get(getCurrentLevelId());

        var dungeonId = levelData.getDungeonId();

        notifyCurLevelRecordChange();
        // use team user choose
        player.getTeamManager().useTemporaryTeam(0);
        player.getServer().getDungeonManager().handoffDungeon(player, dungeonId,
                towerDungeonSettleListener);

        // make sure user can exit dungeon correctly
        player.getScene().setPrevScene(towerData.entryScene);
        player.getScene().setPrevScenePoint(enterPointId);

        player.getSession().send(new PacketTowerEnterLevelRsp(towerData.currentFloorId, getCurrentLevel()));
        // stop using skill
        player.getSession().send(new PacketCanUseSkillNotify(false));
        // notify the cond of stars
        player.getSession().send(new PacketTowerLevelStarCondNotify(towerData.currentFloorId, getCurrentLevel()));
    }

    public void notifyCurLevelRecordChange(){
        player.getSession().send(new PacketTowerCurLevelRecordChangeNotify(towerData.currentFloorId, getCurrentLevel()));
    }
    public void notifyCurLevelRecordChangeWhenDone(int stars){
        Map<Integer, TowerLevelRecord> recordMap = getRecordMap();
        int currentFloorId = towerData.currentFloorId;
        if(!recordMap.containsKey(currentFloorId)){
            recordMap.put(currentFloorId,
                    new TowerLevelRecord(currentFloorId).setLevelStars(getCurrentLevelId(),stars));
        }else{
            recordMap.put(currentFloorId,
                    recordMap.get(currentFloorId).setLevelStars(getCurrentLevelId(),stars));
        }

        towerData.currentLevel++;

        if(!hasNextLevel()){
            // set up the next floor
            recordMap.putIfAbsent(getNextFloorId(), new TowerLevelRecord(getNextFloorId()));
            player.getSession().send(new PacketTowerCurLevelRecordChangeNotify(getNextFloorId(), 1));
        }else{
            player.getSession().send(new PacketTowerCurLevelRecordChangeNotify(currentFloorId, getCurrentLevel()));
        }
    }
    public boolean hasNextLevel(){
        return towerData.currentLevel < 3;
    }
    public int getNextFloorId() {
        return player.getServer().getTowerScheduleManager().getNextFloorId(towerData.currentFloorId);
    }
    public boolean hasNextFloor(){
        return player.getServer().getTowerScheduleManager().getNextFloorId(towerData.currentFloorId) > 0;
    }

    public void clearEntry() {
        towerData.entryScene = 0;
    }

    public boolean canEnterScheduleFloor(){
        Map<Integer, TowerLevelRecord> recordMap = getRecordMap();
        if(!recordMap.containsKey(player.getServer().getTowerScheduleManager().getLastEntranceFloor())){
            return false;
        }
        return recordMap.get(player.getServer().getTowerScheduleManager().getLastEntranceFloor())
                .getStarCount() >= 6;
    }

    public void mirrorTeamSetUp(int teamId) {
        // use team user choose
        player.getTeamManager().useTemporaryTeam(teamId);
        player.sendPacket(new PacketTowerMiddleLevelChangeTeamNotify());
    }
}
