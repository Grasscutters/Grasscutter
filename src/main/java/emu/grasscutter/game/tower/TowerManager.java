package emu.grasscutter.game.tower;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Transient;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.TowerLevelData;
import emu.grasscutter.game.dungeons.DungeonSettleListener;
import emu.grasscutter.game.dungeons.TowerDungeonSettleListener;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class TowerManager {
    @Transient private Player player;

    public TowerManager(Player player) {
        this.player = player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * the floor players chose
     */
    private int currentFloorId;
    private int currentLevel;
    @Transient
    private int currentLevelId;

    /**
     * floorId - Record
     */
    private Map<Integer, TowerLevelRecord> recordMap;

    @Transient
    private int entryScene;

    public int getCurrentFloorId() {
        return currentFloorId;
    }

    public int getCurrentLevelId(){
        return this.currentLevelId + currentLevel;
    }

    /**
     * form 1-3
     */
    public int getCurrentLevel(){
        return currentLevel + 1;
    }
    private static final List<DungeonSettleListener> towerDungeonSettleListener = List.of(new TowerDungeonSettleListener());

    public Map<Integer, TowerLevelRecord> getRecordMap() {
        if(recordMap == null){
            recordMap = new HashMap<>();
            recordMap.put(1001, new TowerLevelRecord(1001));
        }
        return recordMap;
    }

    public void teamSelect(int floor, List<List<Long>> towerTeams) {
        var floorData = GameData.getTowerFloorDataMap().get(floor);

        this.currentFloorId = floorData.getFloorId();
        this.currentLevel = 0;
        this.currentLevelId = GameData.getTowerLevelDataMap().values().stream()
                .filter(x -> x.getLevelId() == floorData.getLevelId() && x.getLevelIndex() == 1)
                .findFirst()
                .map(TowerLevelData::getId)
                .orElse(0);

        if (entryScene == 0){
            entryScene = player.getSceneId();
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
        player.getScene().setPrevScene(entryScene);
        player.getScene().setPrevScenePoint(enterPointId);

        player.getSession().send(new PacketTowerEnterLevelRsp(currentFloorId, getCurrentLevel()));
        // stop using skill
        player.getSession().send(new PacketCanUseSkillNotify(false));
        // notify the cond of stars
        player.getSession().send(new PacketTowerLevelStarCondNotify(currentFloorId, getCurrentLevel()));
    }

    public void notifyCurLevelRecordChange(){
        player.getSession().send(new PacketTowerCurLevelRecordChangeNotify(currentFloorId, getCurrentLevel()));
    }
    public void notifyCurLevelRecordChangeWhenDone(int stars){
        if(!recordMap.containsKey(currentFloorId)){
            recordMap.put(currentFloorId,
                    new TowerLevelRecord(currentFloorId).setLevelStars(getCurrentLevelId(),stars));
        }else{
            recordMap.put(currentFloorId,
                    recordMap.get(currentFloorId).setLevelStars(getCurrentLevelId(),stars));
        }

        this.currentLevel++;

        if(!hasNextLevel()){
            // set up the next floor
            recordMap.putIfAbsent(getNextFloorId(), new TowerLevelRecord(getNextFloorId()));
            player.getSession().send(new PacketTowerCurLevelRecordChangeNotify(getNextFloorId(), 1));
        }else{
            player.getSession().send(new PacketTowerCurLevelRecordChangeNotify(currentFloorId, getCurrentLevel()));
        }
    }
    public boolean hasNextLevel(){
        return this.currentLevel < 3;
    }
    public int getNextFloorId() {
        return player.getServer().getTowerScheduleManager().getNextFloorId(this.currentFloorId);
    }
    public boolean hasNextFloor(){
        return player.getServer().getTowerScheduleManager().getNextFloorId(this.currentFloorId) > 0;
    }

    public void clearEntry() {
        this.entryScene = 0;
    }

    public boolean canEnterScheduleFloor(){
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
