package emu.grasscutter.data;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.*;
import emu.grasscutter.data.binout.AbilityModifier.AbilityConfigData;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierActionType;
import emu.grasscutter.data.common.PointData;
import emu.grasscutter.data.common.ScenePointConfig;
import emu.grasscutter.game.managers.blossom.BlossomConfig;
import emu.grasscutter.game.quest.QuestEncryptionKey;
import emu.grasscutter.game.world.SpawnDataEntry;
import emu.grasscutter.game.world.SpawnDataEntry.GridBlockId;
import emu.grasscutter.game.world.SpawnDataEntry.SpawnGroupEntry;
import emu.grasscutter.scripts.SceneIndexManager;
import emu.grasscutter.utils.JsonUtils;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static emu.grasscutter.config.Configuration.DATA;
import static emu.grasscutter.config.Configuration.RESOURCE;
import static emu.grasscutter.utils.Language.translate;

public class ResourceLoader {

    private static final List<String> loadedResources = new ArrayList<>();

    public static List<Class<?>> getResourceDefClasses() {
        Reflections reflections = new Reflections(ResourceLoader.class.getPackage().getName());
        Set<?> classes = reflections.getSubTypesOf(GameResource.class);

        List<Class<?>> classList = new ArrayList<>(classes.size());
        classes.forEach(o -> {
            Class<?> c = (Class<?>) o;
            if (c.getAnnotation(ResourceType.class) != null) {
                classList.add(c);
            }
        });

        classList.sort((a, b) -> b.getAnnotation(ResourceType.class).loadPriority().value() - a.getAnnotation(ResourceType.class).loadPriority().value());

        return classList;
    }

    private static boolean loadedAll = false;
    public static void loadAll() {
        if (loadedAll) return;
        Grasscutter.getLogger().info(translate("messages.status.resources.loading"));

        // Load ability lists
        loadAbilityEmbryos();
        loadOpenConfig();
        loadAbilityModifiers();
        // Load resources
        loadResources(true);
        // Process into depots
        GameDepot.load();
        // Load spawn data and quests
        loadSpawnData();
        loadQuests();
        loadScriptSceneData();
        // Load scene points - must be done AFTER resources are loaded
        loadScenePoints();
        // Load default home layout
        loadHomeworldDefaultSaveData();
        loadNpcBornData();
        loadBlossomResources();

        Grasscutter.getLogger().info(translate("messages.status.resources.finish"));
        loadedAll = true;
    }

    public static void loadResources() {
        loadResources(false);
    }

    public static void loadResources(boolean doReload) {
        for (Class<?> resourceDefinition : getResourceDefClasses()) {
            ResourceType type = resourceDefinition.getAnnotation(ResourceType.class);

            if (type == null) {
                continue;
            }

            @SuppressWarnings("rawtypes")
            Int2ObjectMap map = GameData.getMapByResourceDef(resourceDefinition);

            if (map == null) {
                continue;
            }

            try {
                loadFromResource(resourceDefinition, type, map, doReload);
            } catch (Exception e) {
                Grasscutter.getLogger().error("Error loading resource file: " + Arrays.toString(type.name()), e);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    protected static void loadFromResource(Class<?> c, ResourceType type, Int2ObjectMap map, boolean doReload) throws Exception {
        if (!loadedResources.contains(c.getSimpleName()) || doReload) {
            for (String name : type.name()) {
                loadFromResource(c, name, map);
            }
            loadedResources.add(c.getSimpleName());
            Grasscutter.getLogger().debug("Loaded " + map.size() + " " + c.getSimpleName() + "s.");
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected static <T> void loadFromResource(Class<T> c, String fileName, Int2ObjectMap map) throws Exception {
        List<T> list = JsonUtils.loadToList(RESOURCE("ExcelBinOutput/" + fileName), c);

        for (T o : list) {
            GameResource res = (GameResource) o;
            res.onLoad();
            map.put(res.getId(), res);
        }
    }

    private static void loadScenePoints() {
        Pattern pattern = Pattern.compile("(?<=scene)(.*?)(?=_point.json)");
        File folder = new File(RESOURCE("BinOutput/Scene/Point"));

        if (!folder.isDirectory() || !folder.exists() || folder.listFiles() == null) {
            Grasscutter.getLogger().error("Scene point files cannot be found, you cannot use teleport waypoints!");
            return;
        }

        for (File file : Objects.requireNonNull(folder.listFiles())) {
            ScenePointConfig config;
            Integer sceneId;

            Matcher matcher = pattern.matcher(file.getName());
            if (matcher.find()) {
                sceneId = Integer.parseInt(matcher.group(1));
            } else {
                continue;
            }

            try {
                config = JsonUtils.loadToClass(file.getPath(), ScenePointConfig.class);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            if (config.points == null) {
                continue;
            }

            List<Integer> scenePoints = new ArrayList<>();
            for (Map.Entry<String, JsonElement> entry : config.points.entrySet()) {
                int id = Integer.parseInt(entry.getKey());
                String name = sceneId + "_" + entry.getKey();
                PointData pointData = JsonUtils.decode(entry.getValue(), PointData.class);
                pointData.setId(id);

                GameData.getScenePointIdList().add(id);
                GameData.getScenePointEntries().put(name, new ScenePointEntry(name, pointData));
                scenePoints.add(id);

                pointData.updateDailyDungeon();
            }
            GameData.getScenePointsPerScene().put(sceneId, scenePoints);
        }
    }

    private static void loadAbilityEmbryos() {
        List<AbilityEmbryoEntry> embryoList = null;

        // Read from cached file if exists
        try {
            embryoList = JsonUtils.loadToList(DATA("AbilityEmbryos.json"), AbilityEmbryoEntry.class);
        } catch (Exception ignored) {}

        if (embryoList == null) {
            // Load from BinOutput
            Pattern pattern = Pattern.compile("(?<=ConfigAvatar_)(.*?)(?=.json)");

            embryoList = new ArrayList<>();
            File folder = new File(Utils.toFilePath(RESOURCE("BinOutput/Avatar/")));
            File[] files = folder.listFiles();
            if (files == null) {
                Grasscutter.getLogger().error("Error loading ability embryos: no files found in " + folder.getAbsolutePath());
                return;
            }

            for (File file : files) {
                AvatarConfig config;
                String avatarName;

                Matcher matcher = pattern.matcher(file.getName());
                if (matcher.find()) {
                    avatarName = matcher.group(0);
                } else {
                    continue;
                }

                try {
                    config = JsonUtils.loadToClass(file.getPath(), AvatarConfig.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }

                if (config.abilities == null) {
                    continue;
                }

                int s = config.abilities.size();
                AbilityEmbryoEntry al = new AbilityEmbryoEntry(avatarName, config.abilities.stream().map(Object::toString).toArray(size -> new String[s]));
                embryoList.add(al);
            }

            try {
                GameDepot.setPlayerAbilities(JsonUtils.loadToMap(RESOURCE("BinOutput/AbilityGroup/AbilityGroup_Other_PlayerElementAbility.json"), String.class, AvatarConfig.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (embryoList == null || embryoList.isEmpty()) {
            Grasscutter.getLogger().error("No embryos loaded!");
            return;
        }

        for (AbilityEmbryoEntry entry : embryoList) {
            GameData.getAbilityEmbryoInfo().put(entry.getName(), entry);
        }
    }

    private static void loadAbilityModifiers() {
        // Load from BinOutput
        File folder = new File(Utils.toFilePath(RESOURCE("BinOutput/Ability/Temp/AvatarAbilities/")));
        File[] files = folder.listFiles();
        if (files == null) {
            Grasscutter.getLogger().error("Error loading ability modifiers: no files found in " + folder.getAbsolutePath());
            return;
        }

        for (File file : files) {
            List<AbilityConfigData> abilityConfigList;

            try {
                abilityConfigList = JsonUtils.loadToList(file.getPath(), AbilityConfigData.class);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            for (AbilityConfigData data : abilityConfigList) {
                if (data.Default.modifiers == null || data.Default.modifiers.size() == 0) {
                    continue;
                }

                AbilityModifierEntry modifierEntry = new AbilityModifierEntry(data.Default.abilityName);

                for (Entry<String, AbilityModifier> entry : data.Default.modifiers.entrySet()) {
                    AbilityModifier modifier = entry.getValue();

                    // Stare.
                    if (modifier.onAdded != null) {
                        for (AbilityModifierAction action : modifier.onAdded) {
                            if (action.$type.contains("HealHP")) {
                                action.type = AbilityModifierActionType.HealHP;
                                modifierEntry.getOnAdded().add(action);
                            }
                        }
                    }

                    if (modifier.onThinkInterval != null) {
                        for (AbilityModifierAction action : modifier.onThinkInterval) {
                            if (action.$type.contains("HealHP")) {
                                action.type = AbilityModifierActionType.HealHP;
                                modifierEntry.getOnThinkInterval().add(action);
                            }
                        }
                    }

                    if (modifier.onRemoved != null) {
                        for (AbilityModifierAction action : modifier.onRemoved) {
                            if (action.$type.contains("HealHP")) {
                                action.type = AbilityModifierActionType.HealHP;
                                modifierEntry.getOnRemoved().add(action);
                            }
                        }
                    }
                }

                GameData.getAbilityModifiers().put(modifierEntry.getName(), modifierEntry);
            }
        }
    }

    private static void loadSpawnData() {
        String[] spawnDataNames = {"Spawns.json", "GadgetSpawns.json"};
        ArrayList<SpawnGroupEntry> spawnEntryMap = new ArrayList<>();

        for (String name : spawnDataNames) {
            // Load spawn entries from file
            try (InputStreamReader reader = DataLoader.loadReader(name)) {
                // Add spawns to group if it already exists in our spawn group map
                spawnEntryMap.addAll(JsonUtils.loadToList(reader, SpawnGroupEntry.class));
            } catch (Exception ignored) {}
        }

        if (spawnEntryMap.isEmpty()) {
            Grasscutter.getLogger().error("No spawn data loaded!");
            return;
        }

        HashMap<GridBlockId, ArrayList<SpawnDataEntry>> areaSort = new HashMap<>();
        //key = sceneId,x,z , value = ArrayList<SpawnDataEntry>
        for (SpawnGroupEntry entry : spawnEntryMap) {
            entry.getSpawns().forEach(
                s -> {
                    s.setGroup(entry);
                    GridBlockId point = s.getBlockId();
                    if (!areaSort.containsKey(point)) {
                        areaSort.put(point, new ArrayList<>());
                    }
                    areaSort.get(point).add(s);
                }
            );
        }
        GameDepot.addSpawnListById(areaSort);
    }

    private static void loadOpenConfig() {
        // Read from cached file if exists
        List<OpenConfigEntry> list = null;

        try {
            list = JsonUtils.loadToList(DATA("OpenConfig.json"), OpenConfigEntry.class);
        } catch (Exception ignored) {}

        if (list == null) {
            Map<String, OpenConfigEntry> map = new TreeMap<>();
            String[] folderNames = {"BinOutput/Talent/EquipTalents/", "BinOutput/Talent/AvatarTalents/"};

            for (String name : folderNames) {
                File folder = new File(Utils.toFilePath(RESOURCE(name)));
                File[] files = folder.listFiles();
                if (files == null) {
                    Grasscutter.getLogger().error("Error loading open config: no files found in " + folder.getAbsolutePath()); return;
                }

                for (File file : files) {
                    if (!file.getName().endsWith(".json")) {
                        continue;
                    }
                    Map<String, OpenConfigData[]> config;

                    try {
                        config = JsonUtils.loadToMap(file.getPath(), String.class, OpenConfigData[].class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }

                    for (Entry<String, OpenConfigData[]> e : config.entrySet()) {
                        OpenConfigEntry entry = new OpenConfigEntry(e.getKey(), e.getValue());
                        map.put(entry.getName(), entry);
                    }
                }
            }

            list = new ArrayList<>(map.values());
        }

        if (list == null || list.isEmpty()) {
            Grasscutter.getLogger().error("No openconfig entries loaded!");
            return;
        }

        for (OpenConfigEntry entry : list) {
            GameData.getOpenConfigEntries().put(entry.getName(), entry);
        }
    }

    private static void loadQuests() {
        File folder = new File(RESOURCE("BinOutput/Quest/"));

        if (!folder.exists()) {
            return;
        }

        for (File file : folder.listFiles()) {
            MainQuestData mainQuest = null;

            try {
                mainQuest = JsonUtils.loadToClass(file.getPath(), MainQuestData.class);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            GameData.getMainQuestDataMap().put(mainQuest.getId(), mainQuest);
        }

        try {
            List<QuestEncryptionKey> keys = DataLoader.loadList("QuestEncryptionKeys.json", QuestEncryptionKey.class);

            Int2ObjectMap<QuestEncryptionKey> questEncryptionMap = GameData.getMainQuestEncryptionMap();
            keys.forEach(key -> questEncryptionMap.put(key.getMainQuestId(), key));
            Grasscutter.getLogger().debug("Loaded {} quest keys.", questEncryptionMap.size());
        } catch (FileNotFoundException | NullPointerException ignored) {
            Grasscutter.getLogger().warn("Unable to load quest keys - ./resources/QuestEncryptionKeys.json not found.");
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load quest keys.", e);
        }

        Grasscutter.getLogger().debug("Loaded " + GameData.getMainQuestDataMap().size() + " MainQuestDatas.");
    }

    public static void loadScriptSceneData() {
        File folder = new File(RESOURCE("ScriptSceneData/"));

        if (!folder.exists()) {
            return;
        }

        for (File file : folder.listFiles()) {
            ScriptSceneData sceneData;
            try {
                sceneData = JsonUtils.loadToClass(file.getPath(), ScriptSceneData.class);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            GameData.getScriptSceneDataMap().put(file.getName(), sceneData);
        }

        Grasscutter.getLogger().debug("Loaded " + GameData.getScriptSceneDataMap().size() + " ScriptSceneDatas.");
    }

    @SneakyThrows
    private static void loadHomeworldDefaultSaveData() {
        var pattern = Pattern.compile("scene(.*)_home_config.json");
        Files.list(Path.of(RESOURCE("BinOutput/HomeworldDefaultSave"))).forEach(file -> {
            String filename = file.getFileName().toString();
            var matcher = pattern.matcher(filename);
            if (!matcher.find()) {
                return;
            }
            try {
                var sceneId = Integer.parseInt(matcher.group(1));
                var data = JsonUtils.loadToClass(filename, HomeworldDefaultSaveData.class);
                GameData.getHomeworldDefaultSaveData().put(sceneId, data);
            } catch (Exception ignored) {}
        });

        Grasscutter.getLogger().debug("Loaded " + GameData.getHomeworldDefaultSaveData().size() + " HomeworldDefaultSaveDatas.");
    }

    @SneakyThrows
    private static void loadNpcBornData() {
        Files.list(Path.of(RESOURCE("BinOutput/Scene/SceneNpcBorn"))).forEach(file -> {
            if (file.toFile().isDirectory()) {
                return;
            }
            try {
                var data = JsonUtils.loadToClass(file.getFileName().toString(), SceneNpcBornData.class);
                if (data.getBornPosList() == null || data.getBornPosList().size() == 0) {
                    return;
                }

                data.setIndex(SceneIndexManager.buildIndex(3, data.getBornPosList(), item -> item.getPos().toPoint()));
                GameData.getSceneNpcBornData().put(data.getSceneId(), data);
            } catch (Exception ignored) {}
        });

        Grasscutter.getLogger().debug("Loaded " + GameData.getSceneNpcBornData().size() + " SceneNpcBornDatas.");
    }

    @SneakyThrows
    private static void loadBlossomResources() {
        GameDepot.setBlossomConfig(DataLoader.loadClass("BlossomConfig.json", BlossomConfig.class));
        Grasscutter.getLogger().debug("Loaded BlossomConfig.");
    }

    // BinOutput configs

    public static class AvatarConfig {
        @SerializedName(value="abilities", alternate={"targetAbilities"})
        public ArrayList<AvatarConfigAbility> abilities;
    }

    public static class AvatarConfigAbility {
        public String abilityName;
        public String toString() {
            return abilityName;
        }
    }

    private static class OpenConfig {
        public OpenConfigData[] data;
    }

    public static class OpenConfigData {
        public String $type;
        public String abilityName;

        @SerializedName(value="talentIndex", alternate={"OJOFFKLNAHN"})
        public int talentIndex;

        @SerializedName(value="skillID", alternate={"overtime"})
        public int skillID;

        @SerializedName(value="pointDelta", alternate={"IGEBKIHPOIF"})
        public int pointDelta;
    }
}
