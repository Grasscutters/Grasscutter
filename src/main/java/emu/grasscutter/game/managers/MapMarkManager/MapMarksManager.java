package emu.grasscutter.game.managers.MapMarkManager;

import dev.morphia.annotations.Entity;
import emu.grasscutter.utils.Position;
import java.util.HashMap;

@Entity
public class MapMarksManager {

    static final int mapMarkMaxCount = 150;
    private HashMap<String, MapMark> mapMarks;

    public MapMarksManager() {
        mapMarks = new HashMap<String, MapMark>();
    }

    public MapMarksManager(HashMap<String, MapMark> mapMarks) {
        this.mapMarks = mapMarks;
    }

    public HashMap<String, MapMark> getAllMapMarks() {
        return mapMarks;
    }

    public MapMark getMapMark(Position position) {
        String key = getMapMarkKey(position);
        if (mapMarks.containsKey(key)) {
            return mapMarks.get(key);
        } else {
            return null;
        }
    }

    public String getMapMarkKey(Position position) {
        return "x" + (int)position.getX()+ "z" + (int)position.getZ();
    }

    public boolean removeMapMark(Position position) {
        String key = getMapMarkKey(position);
        if (mapMarks.containsKey(key)) {
            mapMarks.remove(key);
            return true;
        }
        return false;
    }

    public boolean addMapMark(MapMark mapMark) {
        if (mapMarks.size() < mapMarkMaxCount) {
            if (!mapMarks.containsKey(getMapMarkKey(mapMark.getPosition()))) {
                mapMarks.put(getMapMarkKey(mapMark.getPosition()), mapMark);
                return true;
            }
        }
        return false;
    }

    public void setMapMarks(HashMap<String, MapMark> mapMarks) {
        this.mapMarks = mapMarks;
    }

}
