package emu.grasscutter.game.props;

import it.unimi.dsi.fastutil.ints.*;
import java.util.*;
import java.util.stream.Stream;

public enum WeaponType {
    WEAPON_NONE(0),
    WEAPON_SWORD_ONE_HAND(1, 10, 5),
    WEAPON_CROSSBOW(2),
    WEAPON_STAFF(3),
    WEAPON_DOUBLE_DAGGER(4),
    WEAPON_KATANA(5),
    WEAPON_SHURIKEN(6),
    WEAPON_STICK(7),
    WEAPON_SPEAR(8),
    WEAPON_SHIELD_SMALL(9),
    WEAPON_CATALYST(10, 0, 10),
    WEAPON_CLAYMORE(11, 0, 10),
    WEAPON_BOW(12, 0, 5),
    WEAPON_POLE(13, 0, 4);

    private static final Int2ObjectMap<WeaponType> map = new Int2ObjectOpenHashMap<>();
    private static final Map<String, WeaponType> stringMap = new HashMap<>();

    static {
        Stream.of(values())
                .forEach(
                        e -> {
                            map.put(e.getValue(), e);
                            stringMap.put(e.name(), e);
                        });
    }

    private final int value;
    private int energyGainInitialProbability;
    private int energyGainIncreaseProbability;

    WeaponType(int value) {
        this.value = value;
    }

    WeaponType(int value, int energyGainInitialProbability, int energyGainIncreaseProbability) {
        this.value = value;
        this.energyGainInitialProbability = energyGainInitialProbability;
        this.energyGainIncreaseProbability = energyGainIncreaseProbability;
    }

    public static WeaponType getTypeByValue(int value) {
        return map.getOrDefault(value, WEAPON_NONE);
    }

    public static WeaponType getTypeByName(String name) {
        return stringMap.getOrDefault(name, WEAPON_NONE);
    }

    public int getValue() {
        return value;
    }

    public int getEnergyGainInitialProbability() {
        return energyGainInitialProbability;
    }

    public int getEnergyGainIncreaseProbability() {
        return energyGainIncreaseProbability;
    }
}
