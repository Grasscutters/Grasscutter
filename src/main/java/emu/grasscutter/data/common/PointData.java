package emu.grasscutter.data.common;

import com.google.gson.annotations.SerializedName;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.DailyDungeonData;
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import lombok.Getter;
import lombok.Setter;

public class PointData {
    @Getter @Setter private int id;
    private String $type;
    @Getter private Position tranPos;

    @SerializedName(value="dungeonIds", alternate={"JHHFPGJNMIN"})
    @Getter private int[] dungeonIds;

    @SerializedName(value="dungeonRandomList", alternate={"OIBKFJNBLHO"})
    @Getter private int[] dungeonRandomList;

    @SerializedName(value="tranSceneId", alternate={"JHBICGBAPIH"})
    @Getter @Setter private int tranSceneId;

    public String getType() {
        return $type;
    }

    public void updateDailyDungeon() {
        if (this.dungeonRandomList == null || this.dungeonRandomList.length == 0) {
            return;
        }

        IntList newDungeons = new IntArrayList();
        int day = Grasscutter.getCurrentDayOfWeek();

        for (int randomId : this.dungeonRandomList) {
            DailyDungeonData data = GameData.getDailyDungeonDataMap().get(randomId);

            if (data != null) {
                for (int d : data.getDungeonsByDay(day)) {
                    newDungeons.add(d);
                }
            }
        }

        this.dungeonIds = newDungeons.toIntArray();
    }
}
