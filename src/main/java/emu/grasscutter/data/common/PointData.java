package emu.grasscutter.data.common;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.dungeon.DailyDungeonData;
import emu.grasscutter.game.world.Position;
import it.unimi.dsi.fastutil.ints.*;
import lombok.*;

public final class PointData {
    @Getter @Setter private int id;
    @Getter private int areaId;

    private String $type;
    @Getter private Position tranPos;
    @Getter private Position pos;
    @Getter private Position rot;
    @Getter private Position size;
    @Getter private boolean forbidSimpleUnlock;
    @Getter private boolean unlocked;

    @SerializedName(
            value = "dungeonIds",
            alternate = {"JHHFPGJNMIN"})
    @Getter
    private int[] dungeonIds;

    @SerializedName(
            value = "dungeonRandomList",
            alternate = {"OIBKFJNBLHO"})
    @Getter
    private int[] dungeonRandomList;

    @SerializedName(
            value = "groupIDs",
            alternate = {"HFOBOOHKBGF"})
    @Getter
    private int[] groupIDs;

    @SerializedName(
            value = "tranSceneId",
            alternate = {"JHBICGBAPIH"})
    @Getter
    @Setter
    private int tranSceneId;

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
