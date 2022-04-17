package emu.grasscutter.game.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public enum MaterialType {
	MATERIAL_NONE						(0),
	MATERIAL_FOOD						(1),
	MATERIAL_QUEST						(2),
	MATERIAL_EXCHANGE					(4),
	MATERIAL_CONSUME					(5),
	MATERIAL_EXP_FRUIT					(6),
	MATERIAL_AVATAR						(7),
	MATERIAL_ADSORBATE					(8),
	MATERIAL_CRICKET					(9),
	MATERIAL_ELEM_CRYSTAL				(10),
	MATERIAL_WEAPON_EXP_STONE			(11),
	MATERIAL_CHEST						(12),
	MATERIAL_RELIQUARY_MATERIAL 		(13),
	MATERIAL_AVATAR_MATERIAL			(14),
	MATERIAL_NOTICE_ADD_HP				(15),
	MATERIAL_SEA_LAMP					(16),
	MATERIAL_SELECTABLE_CHEST			(17),
	MATERIAL_FLYCLOAK					(18),
	MATERIAL_NAMECARD					(19),
	MATERIAL_TALENT						(20),
	MATERIAL_WIDGET						(21),
	MATERIAL_CHEST_BATCH_USE			(22),
	MATERIAL_FAKE_ABSORBATE				(23),
	MATERIAL_CONSUME_BATCH_USE			(24),
	MATERIAL_WOOD						(25),
	MATERIAL_FURNITURE_FORMULA			(27),
	MATERIAL_CHANNELLER_SLAB_BUFF		(28),
	MATERIAL_FURNITURE_SUITE_FORMULA	(29),
	MATERIAL_COSTUME					(30);
	
	private final int value;
	private static final Int2ObjectMap<MaterialType> map = new Int2ObjectOpenHashMap<>();
	private static final Map<String, MaterialType> stringMap = new HashMap<>();
	
	static {
		Stream.of(values()).forEach(e -> {
			map.put(e.getValue(), e);
			stringMap.put(e.name(), e);
		});
	}
	
	private MaterialType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public static MaterialType getTypeByValue(int value) {
		return map.getOrDefault(value, MATERIAL_NONE);
	}
	
	public static MaterialType getTypeByName(String name) {
		return stringMap.getOrDefault(name, MATERIAL_NONE);
	}
}
