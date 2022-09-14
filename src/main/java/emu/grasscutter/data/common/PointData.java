package emu.grasscutter.data.common;

import com.google.gson.annotations.SerializedName;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.DailyDungeonData;
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

public class PointData {
    private int id;
    private String $type;
    private Position tranPos;

    @SerializedName(value="dungeonIds", alternate={"JHHFPGJNMIN"})
    private int[] dungeonIds;

    @SerializedName(value="dungeonRandomList", alternate={"OIBKFJNBLHO"})
    private int[] dungeonRandomList;

    @SerializedName(value="tranSceneId", alternate={"JHBICGBAPIH"})
    private int tranSceneId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return $type;
    }

    public Position getTranPos() {
        return tranPos;
    }

    public int[] getDungeonIds() {
        return dungeonIds;
    }

    public int[] getDungeonRandomList() {
        return dungeonRandomList;
    }

    public int getTranSceneId() {
        return tranSceneId;
    }

    public void setTranSceneId(int tranSceneId) {
        this.tranSceneId = tranSceneId;
    }

    public void updateDailyDungeon() {
        if (getDungeonRandomList() == null || getDungeonRandomList().length == 0) {
            return;
        }

        IntList newDungeons = new IntArrayList();
        int day = Grasscutter.getCurrentDayOfWeek();

        for (int randomId : getDungeonRandomList()) {
            DailyDungeonData data = GameData.getDailyDungeonDataMap().get(randomId);

            if (data != null) {
                int[] addDungeons = data.getDungeonsByDay(day);

                for (int d : addDungeons) {
                    newDungeons.add(d);
                }
            }
        }

        this.dungeonIds = newDungeons.toIntArray();
    }
}
