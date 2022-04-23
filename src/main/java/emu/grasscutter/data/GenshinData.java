package emu.grasscutter.data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.utils.Utils;
import emu.grasscutter.data.custom.AbilityEmbryoEntry;
import emu.grasscutter.data.custom.OpenConfigEntry;
import emu.grasscutter.data.custom.ScenePointEntry;
import emu.grasscutter.data.def.*;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class GenshinData {
	// BinOutputs
	private static final Int2ObjectMap<String> abilityHashes = new Int2ObjectOpenHashMap<>();
	private static final Map<String, AbilityEmbryoEntry> abilityEmbryos = new HashMap<>();
	private static final Map<String, OpenConfigEntry> openConfigEntries = new HashMap<>();
	private static final Map<String, ScenePointEntry> scenePointEntries = new HashMap<>();
	
	// ExcelConfigs
	private static final Int2ObjectMap<PlayerLevelData> playerLevelDataMap = new Int2ObjectOpenHashMap<>();
	
	private static final Int2ObjectMap<AvatarData> avatarDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<AvatarLevelData> avatarLevelDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<AvatarSkillDepotData> avatarSkillDepotDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<AvatarSkillData> avatarSkillDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<AvatarCurveData> avatarCurveDataMap = new Int2ObjectLinkedOpenHashMap<>();
	private static final Int2ObjectMap<AvatarPromoteData> avatarPromoteDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<AvatarTalentData> avatarTalentDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<ProudSkillData> proudSkillDataMap = new Int2ObjectOpenHashMap<>();
	
	private static final Int2ObjectMap<ItemData> itemDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<ReliquaryLevelData> reliquaryLevelDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<ReliquaryAffixData> reliquaryAffixDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<ReliquaryMainPropData> reliquaryMainPropDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<ReliquarySetData> reliquarySetDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<WeaponLevelData> weaponLevelDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<WeaponPromoteData> weaponPromoteDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<WeaponCurveData> weaponCurveDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<EquipAffixData> equipAffixDataMap = new Int2ObjectOpenHashMap<>();
	
	private static final Int2ObjectMap<MonsterData> monsterDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<NpcData> npcDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<GadgetData> gadgetDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<MonsterCurveData> monsterCurveDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<MonsterDescribeData> monsterDescribeDataMap = new Int2ObjectOpenHashMap<>();
	
	private static final Int2ObjectMap<AvatarFlycloakData> avatarFlycloakDataMap = new Int2ObjectLinkedOpenHashMap<>();
	private static final Int2ObjectMap<AvatarCostumeData> avatarCostumeDataMap = new Int2ObjectLinkedOpenHashMap<>();
	private static final Int2ObjectMap<AvatarCostumeData> avatarCostumeDataItemIdMap = new Int2ObjectLinkedOpenHashMap<>();
	
	private static final Int2ObjectMap<SceneData> sceneDataMap = new Int2ObjectLinkedOpenHashMap<>();
	private static final Int2ObjectMap<FetterData> fetterDataMap = new Int2ObjectOpenHashMap<>();

	// Cache
	private static Map<Integer, List<Integer>> fetters = new HashMap<>();
	
	public static Int2ObjectMap<?> getMapByResourceDef(Class<?> resourceDefinition) {
		Int2ObjectMap<?> map = null;
		
		try {
			Field field = GenshinData.class.getDeclaredField(Utils.lowerCaseFirstChar(resourceDefinition.getSimpleName()) + "Map");
			field.setAccessible(true);
			
			map = (Int2ObjectMap<?>) field.get(null);
			
			field.setAccessible(false);
		} catch (Exception e) {
			Grasscutter.getLogger().error("Error fetching resource map for " + resourceDefinition.getSimpleName(), e);
		}
		
		return map;
	}

	public static Int2ObjectMap<String> getAbilityHashes() {
		return abilityHashes;
	}
	
	public static Map<String, AbilityEmbryoEntry> getAbilityEmbryoInfo() {
		return abilityEmbryos;
	}

	public static Map<String, OpenConfigEntry> getOpenConfigEntries() {
		return openConfigEntries;
	}

	public static Map<String, ScenePointEntry> getScenePointEntries() {
		return scenePointEntries;
	}

	public static Int2ObjectMap<AvatarData> getAvatarDataMap() {
		return avatarDataMap;
	}
	
	public static Int2ObjectMap<ItemData> getItemDataMap() {
		return itemDataMap;
	}

	public static Int2ObjectMap<AvatarSkillDepotData> getAvatarSkillDepotDataMap() {
		return avatarSkillDepotDataMap;
	}
	
	public static Int2ObjectMap<AvatarSkillData> getAvatarSkillDataMap() {
		return avatarSkillDataMap;
	}

	public static Int2ObjectMap<PlayerLevelData> getPlayerLevelDataMap() {
		return playerLevelDataMap;
	}

	public static Int2ObjectMap<AvatarLevelData> getAvatarLevelDataMap() {
		return avatarLevelDataMap;
	}

	public static Int2ObjectMap<WeaponLevelData> getWeaponLevelDataMap() {
		return weaponLevelDataMap;
	}
	
	public static Int2ObjectMap<ReliquaryAffixData> getReliquaryAffixDataMap() {
		return reliquaryAffixDataMap;
	}
	
	public static Int2ObjectMap<ReliquaryMainPropData> getReliquaryMainPropDataMap() {
		return reliquaryMainPropDataMap;
	}

	public static Int2ObjectMap<WeaponPromoteData> getWeaponPromoteDataMap() {
		return weaponPromoteDataMap;
	}

	public static Int2ObjectMap<WeaponCurveData> getWeaponCurveDataMap() {
		return weaponCurveDataMap;
	}
	
	public static Int2ObjectMap<AvatarCurveData> getAvatarCurveDataMap() {
		return avatarCurveDataMap;
	}

	public static int getRelicExpRequired(int rankLevel, int level) {
		ReliquaryLevelData levelData = reliquaryLevelDataMap.get((rankLevel << 8) + level);
		return levelData != null ? levelData.getExp() : 0;
	}
	
	public static ReliquaryLevelData getRelicLevelData(int rankLevel, int level) {
		return reliquaryLevelDataMap.get((rankLevel << 8) + level);
	}
	
	public static WeaponPromoteData getWeaponPromoteData(int promoteId, int promoteLevel) {
		return weaponPromoteDataMap.get((promoteId << 8) + promoteLevel);
	}

	public static int getWeaponExpRequired(int rankLevel, int level) {
		WeaponLevelData levelData = weaponLevelDataMap.get(level);
		if (levelData == null) {
			return 0;
		}
		try {
			return levelData.getRequiredExps()[rankLevel - 1];
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static AvatarPromoteData getAvatarPromoteData(int promoteId, int promoteLevel) {
		return avatarPromoteDataMap.get((promoteId << 8) + promoteLevel);
	}

	public static int getAvatarLevelExpRequired(int level) {
		AvatarLevelData levelData = avatarLevelDataMap.get(level);
		return levelData != null ? levelData.getExp() : 0;
	}
	
	public static Int2ObjectMap<ProudSkillData> getProudSkillDataMap() {
		return proudSkillDataMap;
	}

	public static Int2ObjectMap<MonsterData> getMonsterDataMap() {
		return monsterDataMap;
	}

	public static Int2ObjectMap<NpcData> getNpcDataMap() {
		return npcDataMap;
	}

	public static Int2ObjectMap<GadgetData> getGadgetDataMap() {
		return gadgetDataMap;
	}

	public static Int2ObjectMap<ReliquarySetData> getReliquarySetDataMap() {
		return reliquarySetDataMap;
	}

	public static Int2ObjectMap<EquipAffixData> getEquipAffixDataMap() {
		return equipAffixDataMap;
	}

	public static Int2ObjectMap<MonsterCurveData> getMonsterCurveDataMap() {
		return monsterCurveDataMap;
	}

	public static Int2ObjectMap<MonsterDescribeData> getMonsterDescribeDataMap() {
		return monsterDescribeDataMap;
	}

	public static Int2ObjectMap<AvatarTalentData> getAvatarTalentDataMap() {
		return avatarTalentDataMap;
	}

	public static Int2ObjectMap<AvatarFlycloakData> getAvatarFlycloakDataMap() {
		return avatarFlycloakDataMap;
	}

	public static Int2ObjectMap<AvatarCostumeData> getAvatarCostumeDataMap() {
		return avatarCostumeDataMap;
	}
	
	public static Int2ObjectMap<AvatarCostumeData> getAvatarCostumeDataItemIdMap() {
		return avatarCostumeDataItemIdMap;
	}

	public static Int2ObjectMap<SceneData> getSceneDataMap() {
		return sceneDataMap;
	}

	public static Map<Integer, List<Integer>> getFetterDataEntries() {
		if (fetters.isEmpty()) {
			fetterDataMap.forEach((k, v) -> {
				if (!fetters.containsKey(v.getAvatarId())) {
					fetters.put(v.getAvatarId(), new ArrayList<>());
				}
				fetters.get(v.getAvatarId()).add(k);
			});
		}

		return fetters;
	}
}
