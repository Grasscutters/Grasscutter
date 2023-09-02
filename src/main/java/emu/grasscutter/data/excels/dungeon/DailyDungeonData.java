package emu.grasscutter.data.excels.dungeon;

import emu.grasscutter.data.*;
import it.unimi.dsi.fastutil.ints.*;
import java.util.Calendar;
import lombok.Getter;

@ResourceType(name = "DailyDungeonConfigData.json")
public class DailyDungeonData extends GameResource {
    private static final int[] empty = new int[0];
    private final Int2ObjectMap<int[]> map;

    @Getter(onMethod_ = @Override)
    private int id;

    private int[] monday;
    private int[] tuesday;
    private int[] wednesday;
    private int[] thursday;
    private int[] friday;
    private int[] saturday;
    private int[] sunday;

    public DailyDungeonData() {
        this.map = new Int2ObjectOpenHashMap<>();
    }

    public int[] getDungeonsByDay(int day) {
        return map.getOrDefault(day, empty);
    }

    @Override
    public void onLoad() {
        map.put(Calendar.MONDAY, monday);
        map.put(Calendar.TUESDAY, tuesday);
        map.put(Calendar.WEDNESDAY, wednesday);
        map.put(Calendar.THURSDAY, thursday);
        map.put(Calendar.FRIDAY, friday);
        map.put(Calendar.SATURDAY, saturday);
        map.put(Calendar.SUNDAY, sunday);
    }
}
