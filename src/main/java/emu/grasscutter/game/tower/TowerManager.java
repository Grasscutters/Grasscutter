package emu.grasscutter.game.tower;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Transient;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketTowerEnterLevelRsp;

import java.util.List;

@Entity
public class TowerManager {
    @Transient private final Player player;

    public TowerManager(Player player) {
        this.player = player;
    }
    
    private int currentLevel;
    private int currentFloor;

    public void teamSelect(int floor, List<List<Long>> towerTeams) {
        var floorData = GameData.getTowerFloorDataMap().get(floor);

        this.currentFloor = floorData.getFloorId();
        this.currentLevel = floorData.getLevelId();

        player.getTeamManager().setupTemporaryTeam(towerTeams);
    }


    public void enterLevel(int enterPointId) {
        var levelData = GameData.getTowerLevelDataMap().get(currentLevel);
        var id = levelData.getDungeonId();
        // use team user choose
        player.getTeamManager().useTemporaryTeam(0);
        player.getServer().getDungeonManager()
                .enterDungeon(player, enterPointId, id);

        player.getSession().send(new PacketTowerEnterLevelRsp(currentFloor, currentLevel));
    }
}
