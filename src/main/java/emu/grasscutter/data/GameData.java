package emu.grasscutter.data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.*;
import emu.grasscutter.game.quest.QuestEncryptionKey;
import emu.grasscutter.utils.Utils;
import emu.grasscutter.data.excels.*;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import lombok.Getter;

public class GameData {
	// BinOutputs
	private static final Int2ObjectMap<String> abilityHashes = new Int2ObjectOpenHashMap<>();
	private static final Map<String, AbilityEmbryoEntry> abilityEmbryos = new HashMap<>();
	private static final Map<String, AbilityModifierEntry> abilityModifiers = new HashMap<>();
	private static final Map<String, OpenConfigEntry> openConfigEntries = new HashMap<>();
	private static final Map<String, ScenePointEntry> scenePointEntries = new HashMap<>();
	private static final Int2ObjectMap<MainQuestData> mainQuestData = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<QuestEncryptionKey> questsKeys = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<HomeworldDefaultSaveData> homeworldDefaultSaveData = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<SceneNpcBornData> npcBornData = new Int2ObjectOpenHashMap<>();

	// ExcelConfigs
	private static final Int2ObjectMap<PlayerLevelData> playerLevelDataMap = new Int2ObjectOpenHashMap<>();

	private static final Int2ObjectMap<AvatarData> avatarDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<AvatarLevelData> avatarLevelDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<AvatarSkillDepotData> avatarSkillDepotDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<AvatarSkillData> avatarSkillDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<AvatarCurveData> avatarCurveDataMap = new Int2ObjectLinkedOpenHashMap<>();
	private static final Int2ObjectMap<AvatarFetterLevelData> avatarFetterLevelDataMap = new Int2ObjectOpenHashMap<>();
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

	private static final Int2ObjectMap<EnvAnimalGatherConfigData> envAnimalGatherConfigDataMap = new Int2ObjectOpenHashMap<>();
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
	private static final Int2ObjectMap<CodexQuestData> codexQuestDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<CodexQuestData> codexQuestDataIdMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<CodexAnimalData> codexAnimalDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<CodexWeaponData> codexWeaponDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<CodexWeaponData> codexWeaponDataIdMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<CodexMaterialData> codexMaterialDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<CodexMaterialData> codexMaterialDataIdMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<CodexReliquaryData> codexReliquaryDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<CodexReliquaryData> codexReliquaryDataIdMap = new Int2ObjectOpenHashMap<>();
	private static final ArrayList<CodexReliquaryData> codexReliquaryArrayList = new ArrayList<>();
	private static final Int2ObjectMap<FetterCharacterCardData> fetterCharacterCardDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<RewardData> rewardDataMap = new Int2ObjectOpenHashMap<>();

	private static final Int2ObjectMap<WorldAreaData> worldAreaDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<WorldLevelData> worldLevelDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<DailyDungeonData> dailyDungeonDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<DungeonEntryData> dungeonEntryDataMap = new Int2ObjectOpenHashMap<>();

	private static final Int2ObjectMap<DungeonData> dungeonDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<QuestData> questDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<ShopGoodsData> shopGoodsDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<CombineData> combineDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<RewardPreviewData> rewardPreviewDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<GatherData> gatherDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<TowerFloorData> towerFloorDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<TowerLevelData> towerLevelDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<TowerScheduleData> towerScheduleDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<BuffData> buffDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<ForgeData> forgeDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<HomeWorldLevelData> homeWorldLevelDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<FurnitureMakeConfigData> furnitureMakeConfigDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<InvestigationMonsterData> investigationMonsterDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<CityData> cityDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<WeatherData> weatherDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<BattlePassMissionData> battlePassMissionDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<BattlePassRewardData> battlePassRewardDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<CookRecipeData> cookRecipeDataMap = new Int2ObjectOpenHashMap<>();
	private static final Int2ObjectMap<CookBonusData> cookBonusDataMap = new Int2ObjectOpenHashMap<>();

	@Getter private static final Int2ObjectMap<ActivityData> activityDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<ActivityWatcherData> activityWatcherDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<MusicGameBasicData> musicGameBasicDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<PersonalLineData> personalLineDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<ChapterData> chapterDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<TriggerExcelConfigData> triggerExcelConfigDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Map<String,ScriptSceneData> scriptSceneDataMap = new HashMap<>();

    @Getter private static final Int2ObjectMap<OpenStateData> openStateDataMap = new Int2ObjectOpenHashMap<>();

	// Cache
	private static Map<Integer, List<Integer>> fetters = new HashMap<>();
	private static Map<Integer, List<ShopGoodsData>> shopGoods = new HashMap<>();
	private static final IntList scenePointIdList = new IntArrayList();



	@Getter private static final List<OpenStateData> openStateList = new ArrayList<>();
	

	public static Int2ObjectMap<?> getMapByResourceDef(Class<?> resourceDefinition) {
		Int2ObjectMap<?> map = null;

		try {
			Field field = GameData.class.getDeclaredField(Utils.lowerCaseFirstChar(resourceDefinition.getSimpleName()) + "Map");
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

	public static Map<String, AbilityModifierEntry> getAbilityModifiers() {
		return abilityModifiers;
	}

	public static Map<String, OpenConfigEntry> getOpenConfigEntries() {
		return openConfigEntries;
	}

	public static Map<String, ScenePointEntry> getScenePointEntries() {
		return scenePointEntries;
	}

	// TODO optimize
	public static ScenePointEntry getScenePointEntryById(int sceneId, int pointId) {
		return getScenePointEntries().get(sceneId + "_" + pointId);
	}

	public static Int2ObjectMap<MainQuestData> getMainQuestDataMap() {
		return mainQuestData;
	}

	public static Int2ObjectMap<QuestEncryptionKey> getMainQuestEncryptionMap() {
		return questsKeys;
	}

	public static Int2ObjectMap<HomeworldDefaultSaveData> getHomeworldDefaultSaveData() {
		return homeworldDefaultSaveData;
	}

	public static Int2ObjectMap<SceneNpcBornData> getSceneNpcBornData() {
		return npcBornData;
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

	public static Int2ObjectMap<AvatarFetterLevelData> getAvatarFetterLevelDataMap() {
		return avatarFetterLevelDataMap;
	}

	public static Int2ObjectMap<FetterCharacterCardData> getFetterCharacterCardDataMap() {
		return fetterCharacterCardDataMap;
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

	public static int getAvatarFetterLevelExpRequired(int level) {
		AvatarFetterLevelData levelData = avatarFetterLevelDataMap.get(level);
		return levelData != null ? levelData.getExp() : 0;
	}

	public static Int2ObjectMap<ProudSkillData> getProudSkillDataMap() {
		return proudSkillDataMap;
	}

	public static Int2ObjectMap<MonsterData> getMonsterDataMap() {
		return monsterDataMap;
	}
	public static Int2ObjectMap<EnvAnimalGatherConfigData> getEnvAnimalGatherConfigDataMap() {
		return envAnimalGatherConfigDataMap;
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

	public static Int2ObjectMap<RewardData> getRewardDataMap() {
		return rewardDataMap;
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

	public static Int2ObjectMap<CodexQuestData> getCodexQuestDataIdMap(){return codexQuestDataIdMap;}

	public static Int2ObjectMap<CodexAnimalData> getCodexAnimalDataMap(){return codexAnimalDataMap;}

	public static Int2ObjectMap<CodexWeaponData> getCodexWeaponDataIdMap(){return codexWeaponDataIdMap;}

	public static Int2ObjectMap<CodexMaterialData> getCodexMaterialDataIdMap(){return codexMaterialDataIdMap;}

	public static Int2ObjectMap<CodexReliquaryData> getcodexReliquaryIdMap(){return codexReliquaryDataIdMap;}

	public static ArrayList<CodexReliquaryData> getcodexReliquaryArrayList(){return codexReliquaryArrayList;}

	public static Int2ObjectMap<WorldAreaData> getWorldAreaDataMap() {
		return worldAreaDataMap;
	}

	public static Int2ObjectMap<WorldLevelData> getWorldLevelDataMap() {
		return worldLevelDataMap;
	}

	public static Int2ObjectMap<DungeonData> getDungeonDataMap() {
		return dungeonDataMap;
	}

	public static Int2ObjectMap<DailyDungeonData> getDailyDungeonDataMap() {
		return dailyDungeonDataMap;
	}

	public static Int2ObjectMap<DungeonEntryData> getDungeonEntryDatatMap(){
		return dungeonEntryDataMap;
	}

	public static Map<Integer, List<ShopGoodsData>> getShopGoodsDataEntries() {
		if (shopGoods.isEmpty()) {
			shopGoodsDataMap.forEach((k, v) -> {
				if (!shopGoods.containsKey(v.getShopType()))
					shopGoods.put(v.getShopType(), new ArrayList<>());
				shopGoods.get(v.getShopType()).add(v);
			});
		}

		return shopGoods;
	}

	public static Int2ObjectMap<RewardPreviewData> getRewardPreviewDataMap() {
		return rewardPreviewDataMap;
	}

	public static IntList getScenePointIdList() {
		return scenePointIdList;
	}

	public static Int2ObjectMap<CombineData> getCombineDataMap() {
		return combineDataMap;
	}

	public static Int2ObjectMap<TowerFloorData> getTowerFloorDataMap(){
		return towerFloorDataMap;
	}

	public static Int2ObjectMap<TowerLevelData> getTowerLevelDataMap(){
		return towerLevelDataMap;
	}

	public static Int2ObjectMap<TowerScheduleData> getTowerScheduleDataMap(){
		return towerScheduleDataMap;
	}

	public static Int2ObjectMap<QuestData> getQuestDataMap() {
		return questDataMap;
	}

	public static Int2ObjectMap<ForgeData> getForgeDataMap() {
		return forgeDataMap;
	}

	public static Int2ObjectMap<HomeWorldLevelData> getHomeWorldLevelDataMap() {
		return homeWorldLevelDataMap;
	}

	public static Int2ObjectMap<FurnitureMakeConfigData> getFurnitureMakeConfigDataMap() {
		return furnitureMakeConfigDataMap;
	}

	public static Int2ObjectMap<GatherData> getGatherDataMap() {
		return gatherDataMap;
	}

	public static Int2ObjectMap<InvestigationMonsterData> getInvestigationMonsterDataMap() {
		return investigationMonsterDataMap;
	}

	public static Int2ObjectMap<CityData> getCityDataMap() {
		return cityDataMap;
	}

	public static Int2ObjectMap<WeatherData> getWeatherDataMap() {
		return weatherDataMap;
	}

	public static Int2ObjectMap<BattlePassMissionData> getBattlePassMissionDataMap() {
		return battlePassMissionDataMap;
	}

	public static Int2ObjectMap<BattlePassRewardData> getBattlePassRewardDataMap() {
		return battlePassRewardDataMap;
	}

	public static Int2ObjectMap<CookRecipeData> getCookRecipeDataMap() {
		return cookRecipeDataMap;
	}

	public static Int2ObjectMap<CookBonusData> getCookBonusDataMap() {
		return cookBonusDataMap;
	}

    public static Int2ObjectMap<BuffData> getBuffDataMap() {
        return buffDataMap;
    }
}
