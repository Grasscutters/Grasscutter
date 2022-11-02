package emu.grasscutter.data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.*;
import emu.grasscutter.game.quest.QuestEncryptionKey;
import emu.grasscutter.utils.Utils;
import emu.grasscutter.data.excels.*;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntSet;
import lombok.Getter;
import lombok.experimental.Tolerate;

public class GameData {
    // BinOutputs
    @Getter private static final Int2ObjectMap<HomeworldDefaultSaveData> homeworldDefaultSaveData = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<String> abilityHashes = new Int2ObjectOpenHashMap<>();
    @Deprecated(forRemoval = true)
    @Getter private static final Map<String, AbilityModifierEntry> abilityModifiers = new HashMap<>();
    @Getter private static final Map<String, ConfigGadget> gadgetConfigData = new HashMap<>();
    @Getter private static final Map<String, OpenConfigEntry> openConfigEntries = new HashMap<>();
    @Deprecated(forRemoval = true) @Getter private static final Map<String, ScenePointEntry> scenePointEntries = new HashMap<>();
    protected static final Map<String, AbilityData> abilityDataMap = new HashMap<>();
    protected static final Int2ObjectMap<ScenePointEntry> scenePointEntryMap = new Int2ObjectOpenHashMap<>();
    private static final Int2ObjectMap<MainQuestData> mainQuestData = new Int2ObjectOpenHashMap<>();
    private static final Int2ObjectMap<QuestEncryptionKey> questsKeys = new Int2ObjectOpenHashMap<>();
    private static final Int2ObjectMap<SceneNpcBornData> npcBornData = new Int2ObjectOpenHashMap<>();
    private static final Map<String, AbilityEmbryoEntry> abilityEmbryos = new HashMap<>();

    // ExcelConfigs
    @Getter private static final ArrayList<CodexReliquaryData> codexReliquaryArrayList = new ArrayList<>();
    @Getter private static final Int2ObjectMap<ActivityData> activityDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<ActivityShopData> activityShopDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<ActivityWatcherData> activityWatcherDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<AvatarCostumeData> avatarCostumeDataItemIdMap = new Int2ObjectLinkedOpenHashMap<>();
    @Getter private static final Int2ObjectMap<AvatarCostumeData> avatarCostumeDataMap = new Int2ObjectLinkedOpenHashMap<>();
    @Getter private static final Int2ObjectMap<AvatarCurveData> avatarCurveDataMap = new Int2ObjectLinkedOpenHashMap<>();
    @Getter private static final Int2ObjectMap<AvatarData> avatarDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<AvatarFetterLevelData> avatarFetterLevelDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<AvatarFlycloakData> avatarFlycloakDataMap = new Int2ObjectLinkedOpenHashMap<>();
    @Getter private static final Int2ObjectMap<AvatarLevelData> avatarLevelDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<AvatarSkillData> avatarSkillDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<AvatarSkillDepotData> avatarSkillDepotDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<AvatarTalentData> avatarTalentDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<BattlePassMissionData> battlePassMissionDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<BattlePassRewardData> battlePassRewardDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<BlossomRefreshExcelConfigData> blossomRefreshExcelConfigDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<BuffData> buffDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<ChapterData> chapterDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<CityData> cityDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<CodexAnimalData> codexAnimalDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<CodexMaterialData> codexMaterialDataIdMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<CodexQuestData> codexQuestDataIdMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<CodexReliquaryData> codexReliquaryDataIdMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<CodexWeaponData> codexWeaponDataIdMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<CombineData> combineDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<CookBonusData> cookBonusDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<CookRecipeData> cookRecipeDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<CompoundData> compoundDataMap=new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<DailyDungeonData> dailyDungeonDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<DungeonData> dungeonDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<DungeonEntryData> dungeonEntryDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<EnvAnimalGatherConfigData> envAnimalGatherConfigDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<EquipAffixData> equipAffixDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<FetterCharacterCardData> fetterCharacterCardDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<ForgeData> forgeDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<FurnitureMakeConfigData> furnitureMakeConfigDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<GadgetData> gadgetDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<GatherData> gatherDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<HomeWorldBgmData> homeWorldBgmDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<HomeWorldLevelData> homeWorldLevelDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<InvestigationMonsterData> investigationMonsterDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<ItemData> itemDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<MonsterCurveData> monsterCurveDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<MonsterData> monsterDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<MonsterDescribeData> monsterDescribeDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<MusicGameBasicData> musicGameBasicDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<NpcData> npcDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<OpenStateData> openStateDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<PersonalLineData> personalLineDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<PlayerLevelData> playerLevelDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<ProudSkillData> proudSkillDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<QuestData> questDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<ReliquaryAffixData> reliquaryAffixDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<ReliquaryMainPropData> reliquaryMainPropDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<ReliquarySetData> reliquarySetDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<RewardData> rewardDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<RewardPreviewData> rewardPreviewDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<SceneData> sceneDataMap = new Int2ObjectLinkedOpenHashMap<>();
    @Getter private static final Int2ObjectMap<TowerFloorData> towerFloorDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<TowerLevelData> towerLevelDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<TowerScheduleData> towerScheduleDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<TriggerExcelConfigData> triggerExcelConfigDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<WeaponCurveData> weaponCurveDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<WeaponLevelData> weaponLevelDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<WeaponPromoteData> weaponPromoteDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<WeatherData> weatherDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<WorldAreaData> worldAreaDataMap = new Int2ObjectOpenHashMap<>();
    @Getter private static final Int2ObjectMap<WorldLevelData> worldLevelDataMap = new Int2ObjectOpenHashMap<>();
    private static final Int2ObjectMap<AvatarPromoteData> avatarPromoteDataMap = new Int2ObjectOpenHashMap<>();
    private static final Int2ObjectMap<FetterData> fetterDataMap = new Int2ObjectOpenHashMap<>();
    private static final Int2ObjectMap<ReliquaryLevelData> reliquaryLevelDataMap = new Int2ObjectOpenHashMap<>();
    private static final Int2ObjectMap<ShopGoodsData> shopGoodsDataMap = new Int2ObjectOpenHashMap<>();
    // The following are accessed via getMapByResourceDef, and will show as unused
    private static final Int2ObjectMap<CodexMaterialData> codexMaterialDataMap = new Int2ObjectOpenHashMap<>();
    private static final Int2ObjectMap<CodexQuestData> codexQuestDataMap = new Int2ObjectOpenHashMap<>();
    private static final Int2ObjectMap<CodexReliquaryData> codexReliquaryDataMap = new Int2ObjectOpenHashMap<>();
    private static final Int2ObjectMap<CodexWeaponData> codexWeaponDataMap = new Int2ObjectOpenHashMap<>();

    // Cache
    @Getter private static final IntList scenePointIdList = new IntArrayList();
    @Getter private static final List<OpenStateData> openStateList = new ArrayList<>();
    @Getter private static final Map<Integer, List<Integer>> scenePointsPerScene = new HashMap<>();
    @Getter private static final Map<String, ScriptSceneData> scriptSceneDataMap = new HashMap<>();
    private static Map<Integer, List<Integer>> fetters = new HashMap<>();
    private static Map<Integer, List<ShopGoodsData>> shopGoods = new HashMap<>();
    protected static Int2ObjectMap<IntSet> proudSkillGroupLevels = new Int2ObjectOpenHashMap<>();
    protected static Int2IntMap proudSkillGroupMaxLevels = new Int2IntOpenHashMap();
    protected static Int2ObjectMap<IntSet> avatarSkillLevels = new Int2ObjectOpenHashMap<>();

    // Getters with wrong names, remove later
    @Deprecated(forRemoval = true) public static Int2ObjectMap<CodexReliquaryData> getcodexReliquaryIdMap() {return codexReliquaryDataIdMap;}
    @Deprecated(forRemoval = true) public static Int2ObjectMap<DungeonEntryData> getDungeonEntryDatatMap() {return dungeonEntryDataMap;}
    @Deprecated(forRemoval = true) @Tolerate public static ArrayList<CodexReliquaryData> getcodexReliquaryArrayList() {return codexReliquaryArrayList;}

    // Getters with different names that stay for now
    public static Int2ObjectMap<MainQuestData> getMainQuestDataMap() {return mainQuestData;}
    public static Int2ObjectMap<QuestEncryptionKey> getMainQuestEncryptionMap() {return questsKeys;}
    public static Int2ObjectMap<SceneNpcBornData> getSceneNpcBornData() {return npcBornData;}
    public static Map<String, AbilityEmbryoEntry> getAbilityEmbryoInfo() {return abilityEmbryos;}

    // Getters that get values rather than containers. If Lombok ever gets syntactic sugar for this, we should adopt that.
    public static AbilityData getAbilityData(String abilityName) {return abilityDataMap.get(abilityName);}
    public static IntSet getAvatarSkillLevels(int avatarSkillId) {return avatarSkillLevels.get(avatarSkillId);}
    public static IntSet getProudSkillGroupLevels(int proudSkillGroupId) {return proudSkillGroupLevels.get(proudSkillGroupId);}
    public static int getProudSkillGroupMaxLevel(int proudSkillGroupId) {return proudSkillGroupMaxLevels.getOrDefault(proudSkillGroupId, 0);}

    // Multi-keyed getters
    public static AvatarPromoteData getAvatarPromoteData(int promoteId, int promoteLevel) {
        return avatarPromoteDataMap.get((promoteId << 8) + promoteLevel);
    }

    public static WeaponPromoteData getWeaponPromoteData(int promoteId, int promoteLevel) {
        return weaponPromoteDataMap.get((promoteId << 8) + promoteLevel);
    }

    public static ReliquaryLevelData getRelicLevelData(int rankLevel, int level) {
        return reliquaryLevelDataMap.get((rankLevel << 8) + level);
    }

    public static ScenePointEntry getScenePointEntryById(int sceneId, int pointId) {
        return scenePointEntryMap.get((sceneId << 16) + pointId);
    }

    // Non-nullable value getters
    public static int getAvatarLevelExpRequired(int level) {
        return Optional.ofNullable(avatarLevelDataMap.get(level)).map(d -> d.getExp()).orElse(0);
    }

    public static int getAvatarFetterLevelExpRequired(int level) {
        return Optional.ofNullable(avatarFetterLevelDataMap.get(level)).map(d -> d.getExp()).orElse(0);
    }

    public static int getRelicExpRequired(int rankLevel, int level) {
        return Optional.ofNullable(getRelicLevelData(rankLevel, level)).map(d -> d.getExp()).orElse(0);
    }


    // Generic getter
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
}
