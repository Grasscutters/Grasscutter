package emu.grasscutter.game.props;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public enum ClimateType {
	CLIMATE_NONE(0),
	CLIMATE_SUNNY(1),
	CLIMATE_CLOUDY(2),
	CLIMATE_RAIN(3),
	CLIMATE_THUNDERSTORM(4),
	CLIMATE_SNOW(5),
	CLIMATE_MIST(6);
	
	private final int value;
	private static final Int2ObjectMap<ClimateType> map = new Int2ObjectOpenHashMap<>();
	private static final Map<String, ClimateType> stringMap = new HashMap<>();
	
	static {
		Stream.of(values()).forEach(e -> {
			map.put(e.getValue(), e);
			stringMap.put(e.name(), e);
		});
	}
	
	private ClimateType(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public String getShortName() {
		return this.name().substring(8).toLowerCase();
	}
	
	public static ClimateType getTypeByValue(int value) {
		return map.getOrDefault(value, CLIMATE_NONE);
	}
	
	public static ClimateType getTypeByName(String name) {
		return stringMap.getOrDefault(name, CLIMATE_NONE);
	}
	
	public static ClimateType getTypeByShortName(String shortName) {
		String name = "CLIMATE_" + shortName.toUpperCase();
		return stringMap.getOrDefault(name, CLIMATE_NONE);
	}
}
