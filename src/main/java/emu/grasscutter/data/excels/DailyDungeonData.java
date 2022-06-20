package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.Calendar;

@ResourceType(name = "DailyDungeonConfigData.json")
public class DailyDungeonData extends GameResource {
    private int id;
    private int[] monday;
    private int[] tuesday;
    private int[] wednesday;
    private int[] thursday;
    private int[] friday;
    private int[] saturday;
    private int[] sunday;

    private static final int[] empty = new int[0];
    private final Int2ObjectMap<int[]> map;

    public DailyDungeonData() {
        this.map = new Int2ObjectOpenHashMap<>();
    }

    @Override
    public int getId() {
        return this.id;
    }

    public int[] getDungeonsByDay(int day) {
        return this.map.getOrDefault(day, empty);
    }

    @Override
    public void onLoad() {
        this.map.put(Calendar.MONDAY, this.monday);
        this.map.put(Calendar.TUESDAY, this.tuesday);
        this.map.put(Calendar.WEDNESDAY, this.wednesday);
        this.map.put(Calendar.THURSDAY, this.thursday);
        this.map.put(Calendar.FRIDAY, this.friday);
        this.map.put(Calendar.SATURDAY, this.saturday);
        this.map.put(Calendar.SUNDAY, this.sunday);
    }
}
