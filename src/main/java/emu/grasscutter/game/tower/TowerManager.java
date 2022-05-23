package emu.grasscutter.game.tower;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Transient;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.TowerLevelData;
import emu.grasscutter.game.dungeons.DungeonSettleListener;
import emu.grasscutter.game.dungeons.TowerDungeonSettleListener;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class TowerManager {

  @Transient
  private Player player;
  @Transient
  private int currentLevelId;
  @Transient
  private int entryScene;

  private int currentFloorId;
  private int currentLevel;
  private Map<Integer, TowerLevelRecord> recordMap;

  private static final List<DungeonSettleListener> towerDungeonSettleListener = List
      .of(new TowerDungeonSettleListener());

  public TowerManager(Player player) {
    this();
    this.player = player;
    if (player != null) {
      Grasscutter.getLogger().debug("DEBUG Abyse: TowerManager MainPlayer " + player.getNickname() + " ");
    }
  }

  public TowerManager() {
    if (player != null) {
      Grasscutter.getLogger().debug("DEBUG Abyse: TowerManager Main " + player.getNickname() + " ");
    }
  }

  public void setPlayer(Player player) {    
    this.player = player;
    if (player != null) {
      Grasscutter.getLogger().info("DEBUG Abyse: TowerManager setPlayer " + player.getNickname() + " ");
    }
  }

  public Player getPlayer() {
    return player;
  }

  public int getCurrentFloorId() {
    return currentFloorId;
  }

  public int getCurrentLevelId() {
    return this.currentLevelId + currentLevel;
  }

  public int getCurrentLevel() {
    return currentLevel + 1;
  }

  public Map<Integer, TowerLevelRecord> getRecordMap() {
    if (recordMap == null) {
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
        .map(TowerLevelData::getID)
        .orElse(0);

    if (entryScene == 0) {
      entryScene = player.getSceneId();
    }

    player.getTeamManager().setupTemporaryTeam(towerTeams);
  }

  public void enterLevel(int enterPointId) {

    Grasscutter.getLogger().debug("DEBUG Abyse: enterLevel " + player.getNickname() + " ");

    var levelData = GameData.getTowerLevelDataMap().get(getCurrentLevelId());

    var dungeonId = levelData.getDungeonId();

    notifyCurLevelRecordChange();
    // use team user choose
    player.getTeamManager().useTemporaryTeam(0);
    player.getServer().getDungeonManager().handoffDungeon(player, dungeonId, towerDungeonSettleListener);

    // make sure user can exit dungeon correctly
    player.getScene().setPrevScene(entryScene);
    player.getScene().setPrevScenePoint(enterPointId);

    player.getSession().send(new PacketTowerEnterLevelRsp(currentFloorId, getCurrentLevel()));
    // stop using skill
    player.getSession().send(new PacketCanUseSkillNotify(false));
    // notify the cond of stars
    player.getSession().send(new PacketTowerLevelStarCondNotify(currentFloorId, getCurrentLevel()));
  }

  public void notifyCurLevelRecordChange() {
    player.getSession().send(new PacketTowerCurLevelRecordChangeNotify(currentFloorId, getCurrentLevel()));
  }

  public void notifyCurLevelRecordChangeWhenDone(int stars) {
    if (!recordMap.containsKey(currentFloorId)) {
      recordMap.put(currentFloorId,
          new TowerLevelRecord(currentFloorId).setLevelStars(getCurrentLevelId(), stars));
    } else {
      recordMap.put(currentFloorId,
          recordMap.get(currentFloorId).setLevelStars(getCurrentLevelId(), stars));
    }

    this.currentLevel++;

    if (!hasNextLevel()) {
      // set up the next floor
      recordMap.putIfAbsent(getNextFloorId(), new TowerLevelRecord(getNextFloorId()));
      player.getSession().send(new PacketTowerCurLevelRecordChangeNotify(getNextFloorId(), 1));
    } else {
      player.getSession().send(new PacketTowerCurLevelRecordChangeNotify(currentFloorId, getCurrentLevel()));
    }
  }

  public boolean hasNextLevel() {
    return this.currentLevel < 3;
  }

  public int getNextFloorId() {
    return player.getServer().getTowerScheduleManager().getNextFloorId(this.currentFloorId);
  }

  public boolean hasNextFloor() {
    return player.getServer().getTowerScheduleManager().getNextFloorId(this.currentFloorId) > 0;
  }

  public void clearEntry() {
    this.entryScene = 0;
  }

  public boolean canEnterScheduleFloor() {
    try {
      if (!recordMap.containsKey(player.getServer().getTowerScheduleManager().getLastEntranceFloor())) {
        return false;
      }
    } catch (Exception e) {
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
