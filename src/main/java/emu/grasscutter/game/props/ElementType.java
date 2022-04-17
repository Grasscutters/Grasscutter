package emu.grasscutter.game.props;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public enum ElementType {
	None		(0, FightProperty.FIGHT_PROP_MAX_FIRE_ENERGY),
	Fire		(1, FightProperty.FIGHT_PROP_MAX_FIRE_ENERGY, 10101, "TeamResonance_Fire_Lv2"),
	Water		(2, FightProperty.FIGHT_PROP_MAX_WATER_ENERGY, 10201, "TeamResonance_Water_Lv2"),
	Grass		(3, FightProperty.FIGHT_PROP_MAX_GRASS_ENERGY),
	Electric	(4, FightProperty.FIGHT_PROP_MAX_ELEC_ENERGY, 10401, "TeamResonance_Electric_Lv2"),
	Ice			(5, FightProperty.FIGHT_PROP_MAX_ICE_ENERGY, 10601, "TeamResonance_Ice_Lv2"),
	Frozen		(6, FightProperty.FIGHT_PROP_MAX_ICE_ENERGY),
	Wind		(7, FightProperty.FIGHT_PROP_MAX_WIND_ENERGY, 10301, "TeamResonance_Wind_Lv2"),
	Rock		(8, FightProperty.FIGHT_PROP_MAX_ROCK_ENERGY, 10701, "TeamResonance_Rock_Lv2"),
	AntiFire	(9, FightProperty.FIGHT_PROP_MAX_FIRE_ENERGY),
	Default		(255, FightProperty.FIGHT_PROP_MAX_FIRE_ENERGY, 10801, "TeamResonance_AllDifferent");
	
	private final int value;
	private final int teamResonanceId;
	private final FightProperty energyProperty;
	private final int configHash;
	private static final Int2ObjectMap<ElementType> map = new Int2ObjectOpenHashMap<>();
	private static final Map<String, ElementType> stringMap = new HashMap<>();
	
	static {
		Stream.of(values()).forEach(e -> {
			map.put(e.getValue(), e);
			stringMap.put(e.name(), e);
		});
	}
	
	private ElementType(int value, FightProperty energyProperty) {
		this(value, energyProperty, 0, null);
	}
	
	private ElementType(int value, FightProperty energyProperty, int teamResonanceId, String configName) {
		this.value = value;
		this.energyProperty = energyProperty;
		this.teamResonanceId = teamResonanceId;
		if (configName != null) {
			this.configHash = Utils.abilityHash(configName);
		} else {
			this.configHash = 0;
		}
	}

	public int getValue() {
		return value;
	}
	
	public FightProperty getEnergyProperty() {
		return energyProperty;
	}

	public int getTeamResonanceId() {
		return teamResonanceId;
	}

	public int getConfigHash() {
		return configHash;
	}

	public static ElementType getTypeByValue(int value) {
		return map.getOrDefault(value, None);
	}
	
	public static ElementType getTypeByName(String name) {
		return stringMap.getOrDefault(name, None);
	}
}
