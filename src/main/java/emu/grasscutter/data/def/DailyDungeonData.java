package emu.grasscutter.data.def;

import java.util.Calendar;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

import emu.grasscutter.game.props.SceneType;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

@ResourceType(name = "DailyDungeonConfigData.json")
public class DailyDungeonData extends GameResource {
	private int Id;
	private int[] Monday;
	private int[] Tuesday;
	private int[] Wednesday;
	private int[] Thursday;
	private int[] Friday;
	private int[] Saturday;
	private int[] Sunday;
	
	private static final int[] empty = new int[0];
	private final Int2ObjectMap<int[]> map;
	
	public DailyDungeonData() {
		this.map = new Int2ObjectOpenHashMap<>();
	}
	    
	@Override
	public int getId() {
		return this.Id;
	}
	
	public int[] getDungeonsByDay(int day) {
		return map.getOrDefault(day, empty);
	}

	@Override
	public void onLoad() {
		map.put(Calendar.MONDAY, Monday);
		map.put(Calendar.TUESDAY, Tuesday);
		map.put(Calendar.WEDNESDAY, Wednesday);
		map.put(Calendar.THURSDAY, Thursday);
		map.put(Calendar.FRIDAY, Friday);
		map.put(Calendar.SATURDAY, Saturday);
		map.put(Calendar.SUNDAY, Sunday);
	}
}
