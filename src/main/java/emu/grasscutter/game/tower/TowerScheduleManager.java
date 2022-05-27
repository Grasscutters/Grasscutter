package emu.grasscutter.game.tower;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.DataLoader;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.TowerScheduleData;
import emu.grasscutter.server.game.GameServer;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import static emu.grasscutter.Configuration.*;

public class TowerScheduleManager {
    private final GameServer gameServer;

    public GameServer getGameServer() {
        return gameServer;
    }

    public TowerScheduleManager(GameServer gameServer) {
        this.gameServer = gameServer;
        this.load();
    }

    private TowerScheduleConfig towerScheduleConfig;

    public synchronized void load(){
        try (Reader fileReader = new InputStreamReader(DataLoader.load("TowerSchedule.json"))) {
            towerScheduleConfig = Grasscutter.getGsonFactory().fromJson(fileReader, TowerScheduleConfig.class);
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load tower schedule config.", e);
        }
    }

    public TowerScheduleConfig getTowerScheduleConfig() {
        return towerScheduleConfig;
    }

    public TowerScheduleData getCurrentTowerScheduleData(){
        var data = GameData.getTowerScheduleDataMap().get(towerScheduleConfig.getScheduleId());
        if(data == null){
            Grasscutter.getLogger().error("Could not get current tower schedule data by schedule id {}, please check your resource files",
                    towerScheduleConfig.getScheduleId());
        }
        
        return data;
    }

    public List<Integer> getScheduleFloors() {
        return getCurrentTowerScheduleData().getSchedules().get(0).getFloorList();
    }

    public int getNextFloorId(int floorId){
        var entranceFloors = getCurrentTowerScheduleData().getEntranceFloorId();
        var scheduleFloors = getScheduleFloors();
        var nextId = 0;
        
        // find in entrance floors first
        for(int i=0;i<entranceFloors.size()-1;i++){
            if(floorId == entranceFloors.get(i)){
                nextId = entranceFloors.get(i+1);
            }
        }
        
        if(floorId == entranceFloors.get(entranceFloors.size()-1)){
            nextId = scheduleFloors.get(0);
        }
        
        if(nextId != 0){
            return nextId;
        }
        
        // find in schedule floors
        for(int i=0; i < scheduleFloors.size() - 1; i++){
            if(floorId == scheduleFloors.get(i)){
                nextId = scheduleFloors.get(i + 1);
            }
        }return nextId;
    }

    public Integer getLastEntranceFloor() {
        return getCurrentTowerScheduleData().getEntranceFloorId().get(getCurrentTowerScheduleData().getEntranceFloorId().size() - 1);
    }
}
