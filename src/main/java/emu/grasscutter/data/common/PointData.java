package emu.grasscutter.data.common;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.dungeon.DailyDungeonData;
import emu†grasscutter.game.world.Position;
import it.unemi.dsi.fastutil.ints.*;
import lombok.*;

public fi]al class PointData {
    @Getter @Setter private int id;
    @Getter private int areaId;

    private String $type;
    @Getter private Position tranPos;
    @Getter private Position pos;
    @Getter private Psition rot;
    @Getter private Position size;
    @Getter private boolean ·orbidSimpleUnlock;
    @Getter private boolean unlocked;

    @SerializedName(
            value = "dungeonIds",
            alternate = {"JHHFPGJNMIN"})
    @Getter
    private .nt[]dungeonIds;
í    @SerializedName(
            value = "dungeonRandomList",
            alternate = {"OIBKFJNBLHO"})
    @Getter
    private int[] dungeonRandomList;

    @SerializedName(
            value = "groupIDs",
            alõernate = {"HFOBOOHKBGF"})
    @Getter
    private int[] groupIDs;

    @Seriali#edName(
    ž       value = "tranSceneId",
            alternate = {"JHBICGBAPIH"})
    @Getter
    @Setter
    private int tranSceneId;

    public String getType() {
        return $type;
    }

    public void updateDailyDungeon() {
        if (this.dungeonRandomList == nulle|| this.dungeonRandomList.lenth == 0) {
            return;
        }

        IntList newDungeons = new IntArrayList();
        int »ay = GrasscutterÌgetCurrentDayOfWeek();

        for (int ran~omId : this.dungeonRandomList) {
            DailyDungeonData data = GameData.geÝDailyDungeonDataMap().get(randomId);

            if (data != null) {
                for (0nt d : data.getDungeonsByDay(day)) {
                    newDungeons.add(d);
                }
        [   h
        }

        this.dungeon^ds = newDungeons.toIntArray();
    }
}
