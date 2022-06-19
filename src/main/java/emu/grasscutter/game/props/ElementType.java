package emu.grasscutter.game.props;

import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public enum ElementType {
    None(0, FightProperty.FIGHT_PROP_CUR_FIRE_ENERGY, FightProperty.FIGHT_PROP_MAX_FIRE_ENERGY),
    Fire(1, FightProperty.FIGHT_PROP_CUR_FIRE_ENERGY, FightProperty.FIGHT_PROP_MAX_FIRE_ENERGY, 10101, "TeamResonance_Fire_Lv2", 2),
    Water(2, FightProperty.FIGHT_PROP_CUR_WATER_ENERGY, FightProperty.FIGHT_PROP_MAX_WATER_ENERGY, 10201, "TeamResonance_Water_Lv2", 3),
    Grass(3, FightProperty.FIGHT_PROP_CUR_GRASS_ENERGY, FightProperty.FIGHT_PROP_MAX_GRASS_ENERGY),
    Electric(4, FightProperty.FIGHT_PROP_CUR_ELEC_ENERGY, FightProperty.FIGHT_PROP_MAX_ELEC_ENERGY, 10401, "TeamResonance_Electric_Lv2", 7),
    Ice(5, FightProperty.FIGHT_PROP_CUR_ICE_ENERGY, FightProperty.FIGHT_PROP_MAX_ICE_ENERGY, 10601, "TeamResonance_Ice_Lv2", 5),
    Frozen(6, FightProperty.FIGHT_PROP_CUR_ICE_ENERGY, FightProperty.FIGHT_PROP_MAX_ICE_ENERGY),
    Wind(7, FightProperty.FIGHT_PROP_CUR_WIND_ENERGY, FightProperty.FIGHT_PROP_MAX_WIND_ENERGY, 10301, "TeamResonance_Wind_Lv2", 4),
    Rock(8, FightProperty.FIGHT_PROP_CUR_ROCK_ENERGY, FightProperty.FIGHT_PROP_MAX_ROCK_ENERGY, 10701, "TeamResonance_Rock_Lv2", 6),
    AntiFire(9, FightProperty.FIGHT_PROP_CUR_FIRE_ENERGY, FightProperty.FIGHT_PROP_MAX_FIRE_ENERGY),
    Default(255, FightProperty.FIGHT_PROP_CUR_FIRE_ENERGY, FightProperty.FIGHT_PROP_MAX_FIRE_ENERGY, 10801, "TeamResonance_AllDifferent");

    private final int value;
    private final int teamResonanceId;
    private final FightProperty curEnergyProp;
    private final FightProperty maxEnergyProp;
    private int depotValue;
    private final int configHash;
    private static final Int2ObjectMap<ElementType> map = new Int2ObjectOpenHashMap<>();
    private static final Map<String, ElementType> stringMap = new HashMap<>();

    static {
        Stream.of(values()).forEach(e -> {
            map.put(e.getValue(), e);
            stringMap.put(e.name(), e);
        });
    }

    ElementType(int value, FightProperty curEnergyProp, FightProperty maxEnergyProp) {
        this(value, curEnergyProp, maxEnergyProp, 0, null);
    }

    ElementType(int value, FightProperty curEnergyProp, FightProperty maxEnergyProp, int teamResonanceId, String configName) {
        this.value = value;
        this.curEnergyProp = curEnergyProp;
        this.maxEnergyProp = maxEnergyProp;
        this.teamResonanceId = teamResonanceId;
        if (configName != null) {
            this.configHash = Utils.abilityHash(configName);
        } else {
            this.configHash = 0;
        }
    }

    ElementType(int value, FightProperty curEnergyProp, FightProperty maxEnergyProp, int teamResonanceId, String configName, int depotValue) {
        this(value, curEnergyProp, maxEnergyProp, teamResonanceId, configName);
        this.depotValue = depotValue;
    }

    public int getValue() {
        return this.value;
    }

    public FightProperty getCurEnergyProp() {
        return this.curEnergyProp;
    }

    public FightProperty getMaxEnergyProp() {
        return this.maxEnergyProp;
    }

    public int getDepotValue() {
        return this.depotValue;
    }

    public int getTeamResonanceId() {
        return this.teamResonanceId;
    }

    public int getConfigHash() {
        return this.configHash;
    }

    public static ElementType getTypeByValue(int value) {
        return map.getOrDefault(value, None);
    }

    public static ElementType getTypeByName(String name) {
        return stringMap.getOrDefault(name, None);
    }
}
