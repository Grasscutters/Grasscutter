package emu.grasscutter.game.props;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Getter;

public enum ElementType {
    None		(0, FightProperty.FIGHT_PROP_CUR_FIRE_ENERGY, FightProperty.FIGHT_PROP_MAX_FIRE_ENERGY),
    Fire		(1, FightProperty.FIGHT_PROP_CUR_FIRE_ENERGY, FightProperty.FIGHT_PROP_MAX_FIRE_ENERGY, 10101, "TeamResonance_Fire_Lv2", 2),
    Water		(2, FightProperty.FIGHT_PROP_CUR_WATER_ENERGY, FightProperty.FIGHT_PROP_MAX_WATER_ENERGY, 10201, "TeamResonance_Water_Lv2", 3),
    Grass		(3, FightProperty.FIGHT_PROP_CUR_GRASS_ENERGY, FightProperty.FIGHT_PROP_MAX_GRASS_ENERGY, 10501, "TeamResonance_Grass_Lv2", 8),
    Electric	(4, FightProperty.FIGHT_PROP_CUR_ELEC_ENERGY, FightProperty.FIGHT_PROP_MAX_ELEC_ENERGY, 10401, "TeamResonance_Electric_Lv2", 7),
    Ice			(5, FightProperty.FIGHT_PROP_CUR_ICE_ENERGY, FightProperty.FIGHT_PROP_MAX_ICE_ENERGY, 10601, "TeamResonance_Ice_Lv2", 5),
    Frozen		(6, FightProperty.FIGHT_PROP_CUR_ICE_ENERGY, FightProperty.FIGHT_PROP_MAX_ICE_ENERGY),
    Wind		(7, FightProperty.FIGHT_PROP_CUR_WIND_ENERGY, FightProperty.FIGHT_PROP_MAX_WIND_ENERGY, 10301, "TeamResonance_Wind_Lv2", 4),
    Rock		(8, FightProperty.FIGHT_PROP_CUR_ROCK_ENERGY, FightProperty.FIGHT_PROP_MAX_ROCK_ENERGY, 10701, "TeamResonance_Rock_Lv2", 6),
    AntiFire	(9, FightProperty.FIGHT_PROP_CUR_FIRE_ENERGY, FightProperty.FIGHT_PROP_MAX_FIRE_ENERGY),
    Default		(255, FightProperty.FIGHT_PROP_CUR_FIRE_ENERGY, FightProperty.FIGHT_PROP_MAX_FIRE_ENERGY, 10801, "TeamResonance_AllDifferent");

    @Getter private final int value;
    @Getter private final int teamResonanceId;
    @Getter private final FightProperty curEnergyProp;
    @Getter private final FightProperty maxEnergyProp;
    @Getter private final int depotValue;
    @Getter private final int configHash;
    private static final Int2ObjectMap<ElementType> map = new Int2ObjectOpenHashMap<>();
    private static final Map<String, ElementType> stringMap = new HashMap<>();

    static {
        Stream.of(values()).forEach(e -> {
            map.put(e.getValue(), e);
            stringMap.put(e.name(), e);
        });
    }

    private ElementType(int value, FightProperty curEnergyProp, FightProperty maxEnergyProp) {
        this(value, curEnergyProp, maxEnergyProp, 0, null, 1);
    }

    private ElementType(int value, FightProperty curEnergyProp, FightProperty maxEnergyProp, int teamResonanceId, String configName) {
        this(value, curEnergyProp, maxEnergyProp, teamResonanceId, configName, 1);
    }

    private ElementType(int value, FightProperty curEnergyProp, FightProperty maxEnergyProp, int teamResonanceId, String configName, int depotValue) {
        this.value = value;
        this.curEnergyProp = curEnergyProp;
        this.maxEnergyProp = maxEnergyProp;
        this.teamResonanceId = teamResonanceId;
        this.depotValue = depotValue;
        if (configName != null) {
            this.configHash = Utils.abilityHash(configName);
        } else {
            this.configHash = 0;
        }
    }

    public static ElementType getTypeByValue(int value) {
        return map.getOrDefault(value, None);
    }

    public static ElementType getTypeByName(String name) {
        return stringMap.getOrDefault(name, None);
    }
}
