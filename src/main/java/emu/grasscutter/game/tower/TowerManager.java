package emu.grasscutter.game.tower;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Transient;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.TowerLevelData;
import emu.grasscutter.game.dungeons.DungeonSettleListener;
import emu.grasscutter.game.dungeons.TowerDungeonSettleListener;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketTowerCurLevelRecordChangeNotify;

import emu.grasscutter.server.packet.send.PacketTowerEnterLevelRsp;

import java.util.List;

@Entity
public class TowerManager {
    @Transient private Player player;

    public TowerManager(Player player) {
        this.player = player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private int currentFloorId;
    private int currentLevel;
    @Transient
    private int currentLevelId;

    @Transient
    private int entryScene;

    public int getCurrentFloorId() {
        return currentFloorId;
    }

    private static final List<DungeonSettleListener> towerDungeonSettleListener = List.of(new TowerDungeonSettleListener());
    public void teamSelect(int floor, List<List<Long>> towerTeams) {
        var floorData = GameData.getTowerFloorDataMap().get(floor);

        this.currentFloorId = floorData.getFloorId();
        this.currentLevel = 0;
        this.currentLevelId = GameData.getTowerLevelDataMap().values().stream()
                .filter(x -> x.getLevelId() == floorData.getLevelId() && x.getLevelIndex() == 1)
                .findFirst()
                .map(TowerLevelData::getID)
                .orElse(0);

        if (entryScene == 0){
            entryScene = player.getSceneId();
        }


        player.getTeamManager().setupTemporaryTeam(towerTeams);
    }


    public void enterLevel(int enterPointId) {
        var levelData = GameData.getTowerLevelDataMap().get(currentLevelId + currentLevel);

        this.currentLevel++;
        var id = levelData.getDungeonId();

        notifyCurLevelRecordChange();
        // use team user choose
        player.getTeamManager().useTemporaryTeam(0);
        player.getServer().getDungeonManager().handoffDungeon(player, id,
                towerDungeonSettleListener);

        // make sure user can exit dungeon correctly
        player.getScene().setPrevScene(entryScene);
        player.getScene().setPrevScenePoint(enterPointId);

        player.getSession().send(new PacketTowerEnterLevelRsp(currentFloorId, currentLevel));

    }

    public void notifyCurLevelRecordChange(){
        player.getSession().send(new PacketTowerCurLevelRecordChangeNotify(currentFloorId, currentLevel));
    }
    public void notifyCurLevelRecordChangeWhenDone(){
        player.getSession().send(new PacketTowerCurLevelRecordChangeNotify(currentFloorId, currentLevel + 1));
    }
    public boolean hasNextLevel(){
        return this.currentLevel < 3;
    }

    public int getNextFloorId() {
        if(hasNextLevel()){
            return 0;
        }
        this.currentFloorId++;
        return this.currentFloorId;
    }

    public void clearEntry() {
        this.entryScene = 0;
    }
}
