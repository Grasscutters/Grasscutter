package emu.grasscutter.data;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.*;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.data.common.PointData;
import emu.grasscutter.game.managers.blossom.BlossomConfig;
import emu.grasscutter.game.quest.QuestEncryptionKey;
import emu.grasscutter.game.world.SpawnDataEntry;
import emu.grasscutter.game.world.SpawnDataEntry.GridBlockId;
import emu.grasscutter.game.world.SpawnDataEntry.SpawnGroupEntry;
import emu.grasscutter.scripts.SceneIndexManager;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.JsonUtils;
import emu.grasscutter.utils.TsvUtils;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import lombok.val;

import org.reflections.Reflections;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static emu.grasscutter.utils.FileUtils.getDataPath;
import static emu.grasscutter.utils.FileUtils.getResourcePath;
import static emu.grasscutter.utils.Language.translate;

public class ResourceLoader {

    private static final Set<String> loadedResources = new CopyOnWriteArraySet<>();

    // Get a list of all resource classes, sorted by loadPriority
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

    // Get a list containing sets of all resource classes, sorted by loadPriority
    protected static List<Set<Class<?>>> getResourceDefClassesPrioritySets() {
        val reflections = new Reflections(ResourceLoader.class.getPackage().getName());
        val classes = reflections.getSubTypesOf(GameResource.class);
        val priorities = ResourceType.LoadPriority.getInOrder();
        Grasscutter.getLogger().debug("Priorities are "+priorities);
        val map = new LinkedHashMap<ResourceType.LoadPriority, Set<Class<?>>>(priorities.size());
        priorities.forEach(p -> map.put(p, new HashSet<>()));

        classes.forEach(c -> {
            // val c = (Class<?>) o;
            val annotation = c.getAnnotation(ResourceType.class);
            if (annotation != null) {
                map.get(annotation.loadPriority()).add(c);
            }
        });
        return List.copyOf(map.values());
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
        loadGadgetConfigData();
        loadSpawnData();
        loadQuests();
        loadScriptSceneData();
        // Load scene points - must be done AFTER resources are loaded
        loadScenePoints();
        // Load default home layout
        loadHomeworldDefaultSaveData();
        loadNpcBornData();
        loadBlossomResources();
        cacheTalentLevelSets();

        Grasscutter.getLogger().info(translate("messages.status.resources.finish"));
        loadedAll = true;
    }

    public static void loadResources() {
        loadResources(false);
    }

    public static void loadResources(boolean doReload) {
        long startTime = System.nanoTime();
        val errors = new ConcurrentLinkedQueue<Pair<String, Exception>>();  // Logger in a parallel stream will deadlock

        getResourceDefClassesPrioritySets().forEach(classes -> {
            classes.stream()
                .parallel().unordered()
                .forEach(c -> {
                    val type = c.getAnnotation(ResourceType.class);
                    if (type == null) return;

                    val map = GameData.getMapByResourceDef(c);
                    if (map == null) return;

                    try {
                        loadFromResource(c, type, map, doReload);
                    } catch (Exception e) {
                        errors.add(Pair.of(Arrays.toString(type.name()), e));
                    }
                });
        });
        errors.forEach(pair -> Grasscutter.getLogger().error("Error loading resource file: " + pair.left(), pair.right()));
        long endTime = System.nanoTime();
        long ns = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        Grasscutter.getLogger().debug("Loading resources took "+ns+"ns == "+ns/1000000+"ms");
    }

    @SuppressWarnings("rawtypes")
    protected static void loadFromResource(Class<?> c, ResourceType type, Int2ObjectMap map, boolean doReload) throws Exception {
        val simpleName = c.getSimpleName();
        if (doReload || !loadedResources.contains(simpleName)) {
            for (String name : type.name()) {
                loadFromResource(c, FileUtils.getExcelPath(name), map);
            }
            loadedResources.add(simpleName);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected static <T> void loadFromResource(Class<T> c, Path filename, Int2ObjectMap map) throws Exception {
        val results = switch (FileUtils.getFileExtension(filename)) {
            case "json" -> JsonUtils.loadToList(filename, c);
            case "tsj" -> TsvUtils.loadTsjToListSetField(filename, c);
            case "tsv" -> TsvUtils.loadTsvToListSetField(filename, c);
            default -> null;
        };
        if (results == null) return;
        results.forEach(o -> {
            GameResource res = (GameResource) o;
            res.onLoad();
            map.put(res.getId(), res);
        });
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected static <T> void loadFromResource(Class<T> c, String fileName, Int2ObjectMap map) throws Exception {
        JsonUtils.loadToList(getResourcePath("ExcelBinOutput/" + fileName), c).forEach(o -> {
            GameResource res = (GameResource) o;
            res.onLoad();
            map.put(res.getId(), res);
        });
    }

    public class ScenePointConfig {  // Sadly this doesn't work as a local class in loadScenePoints()
        public Map<Integer, PointData> points;
    }
    private static void loadScenePoints() {
        val pattern = Pattern.compile("scene([0-9]+)_point\\.json");
        try {
            Files.newDirectoryStream(getResourcePath("BinOutput/Scene/Point"), "scene*_point.json").forEach(path -> {
                val matcher = pattern.matcher(path.getFileName().toString());
                if (!matcher.find()) return;
                int sceneId = Integer.parseInt(matcher.group(1));

                ScenePointConfig config;
                try {
                    config = JsonUtils.loadToClass(path, ScenePointConfig.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }

                if (config.points == null) return;

                val scenePoints = new IntArrayList();
                config.points.forEach((pointId, pointData) -> {
                    val scenePoint = new ScenePointEntry(sceneId, pointData);
                    scenePoints.add(pointId);
                    pointData.setId(pointId);

                    GameData.getScenePointIdList().add(pointId);
                    GameData.getScenePointEntries().put(scenePoint.getName(), scenePoint);
                    GameData.scenePointEntryMap.put((sceneId << 16) + pointId, scenePoint);

                    pointData.updateDailyDungeon();
                });
                GameData.getScenePointsPerScene().put(sceneId, scenePoints);
            });
        } catch (IOException e) {
            Grasscutter.getLogger().error("Scene point files cannot be found, you cannot use teleport waypoints!");
            return;
        }
    }

    private static void cacheTalentLevelSets() {
        // All known levels, keyed by proudSkillGroupId
        GameData.getProudSkillDataMap().forEach((id, data) ->
            GameData.proudSkillGroupLevels
                .computeIfAbsent(data.getProudSkillGroupId(), i -> new IntArraySet())
                .add(data.getLevel()));
        // All known levels, keyed by avatarSkillId
        GameData.getAvatarSkillDataMap().forEach((id, data) ->
            GameData.avatarSkillLevels.put((int) id, GameData.proudSkillGroupLevels.get(data.getProudSkillGroupId())));
        // Maximum known levels, keyed by proudSkillGroupId
        GameData.proudSkillGroupLevels.forEach((id, set) ->
            GameData.proudSkillGroupMaxLevels.put((int) id, set.intStream().max().getAsInt()));
    }

    private static void loadAbilityEmbryos() {
        List<AbilityEmbryoEntry> embryoList = null;

        // Read from cached file if exists
        try {
            embryoList = JsonUtils.loadToList(getDataPath("AbilityEmbryos.json"), AbilityEmbryoEntry.class);
        } catch (Exception ignored) {}

        if (embryoList == null) {
            // Load from BinOutput
            val pattern = Pattern.compile("ConfigAvatar_(.+?)\\.json");

            val l = new ArrayList<AbilityEmbryoEntry>();
            try {
                Files.newDirectoryStream(getResourcePath("BinOutput/Avatar/"), "ConfigAvatar_*.json").forEach(path -> {
                    val matcher = pattern.matcher(path.getFileName().toString());
                    if (!matcher.find()) return;
                    String avatarName = matcher.group(1);
                    AvatarConfig config;

                    try {
                        config = JsonUtils.loadToClass(path, AvatarConfig.class);
                    } catch (Exception e) {
                        Grasscutter.getLogger().error("Error loading player ability embryos:", e);
                        return;
                    }

                    if (config.abilities == null) return;

                    int s = config.abilities.size();
                    AbilityEmbryoEntry al = new AbilityEmbryoEntry(avatarName, config.abilities.stream().map(Object::toString).toArray(size -> new String[s]));
                    l.add(al);
                });
            } catch (IOException e) {
                Grasscutter.getLogger().error("Error loading ability embryos: no files found");
                return;
            }

            embryoList = l;

            try {
                GameDepot.setPlayerAbilities(JsonUtils.loadToMap(getResourcePath("BinOutput/AbilityGroup/AbilityGroup_Other_PlayerElementAbility.json"), String.class, AvatarConfig.class));
            } catch (IOException e) {
                Grasscutter.getLogger().error("Error loading player abilities:", e);
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

    // private static HashSet<String> modifierActionTypes = new HashSet<>();
    public static class AbilityConfigData {
        public AbilityData Default;
    }
    private static void loadAbilityModifiers() {
        // Load from BinOutput
        try (Stream<Path> paths = Files.walk(getResourcePath("BinOutput/Ability/Temp/"))) {
            paths.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".json")).forEach(ResourceLoader::loadAbilityModifiers);
        } catch (IOException e) {
            Grasscutter.getLogger().error("Error loading ability modifiers: ", e);
            return;
        }
        // System.out.println("Loaded modifiers, found types:");
        // modifierActionTypes.stream().sorted().forEach(s -> System.out.printf("%s, ", s));
        // System.out.println("[End]");
    }
    private static void loadAbilityModifiers(Path path) {
        try {
            JsonUtils.loadToList(path, AbilityConfigData.class).forEach(data -> loadAbilityData(data.Default));
        } catch (IOException e) {
            Grasscutter.getLogger().error("Error loading ability modifiers from path " + path.toString() + ": ", e);
            return;
        }
    }
    private static void loadAbilityData(AbilityData data) {
        GameData.abilityDataMap.put(data.abilityName, data);

        val modifiers = data.modifiers;
        if (modifiers == null || modifiers.size() == 0) return;

        String name = data.abilityName;
        AbilityModifierEntry modifierEntry = new AbilityModifierEntry(name);
        modifiers.forEach((key, modifier) -> {
            Stream.ofNullable(modifier.onAdded).flatMap(Stream::of)
                // .map(action -> {modifierActionTypes.add(action.$type); return action;})
                .filter(action -> action.type == AbilityModifierAction.Type.HealHP)
                .forEach(action -> modifierEntry.getOnAdded().add(action));
            Stream.ofNullable(modifier.onThinkInterval).flatMap(Stream::of)
                // .map(action -> {modifierActionTypes.add(action.$type); return action;})
                .filter(action -> action.type == AbilityModifierAction.Type.HealHP)
                .forEach(action -> modifierEntry.getOnThinkInterval().add(action));
            Stream.ofNullable(modifier.onRemoved).flatMap(Stream::of)
                // .map(action -> {modifierActionTypes.add(action.$type); return action;})
                .filter(action -> action.type == AbilityModifierAction.Type.HealHP)
                .forEach(action -> modifierEntry.getOnRemoved().add(action));
        });

        GameData.getAbilityModifiers().put(name, modifierEntry);
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
            list = JsonUtils.loadToList(getDataPath("OpenConfig.json"), OpenConfigEntry.class);
        } catch (Exception ignored) {}

        if (list == null) {
            Map<String, OpenConfigEntry> map = new TreeMap<>();
            String[] folderNames = {"BinOutput/Talent/EquipTalents/", "BinOutput/Talent/AvatarTalents/"};

            for (String folderName : folderNames) {
                try {
                    Files.newDirectoryStream(getResourcePath(folderName), "*.json").forEach(path -> {
                        try {
                            JsonUtils.loadToMap(path, String.class, OpenConfigData[].class)
                                .forEach((name, data) -> map.put(name, new OpenConfigEntry(name, data)));
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    });
                } catch (IOException e) {
                    Grasscutter.getLogger().error("Error loading open config: no files found in " + folderName);
                    return;
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
        try {
            Files.list(getResourcePath("BinOutput/Quest/")).forEach(path -> {
                try {
                    val mainQuest = JsonUtils.loadToClass(path, MainQuestData.class);
                    GameData.getMainQuestDataMap().put(mainQuest.getId(), mainQuest);
                } catch (IOException e) {

                }
            });
        } catch (IOException e) {
            Grasscutter.getLogger().error("Quest data missing");
            return;
        }

        try {
            val questEncryptionMap = GameData.getMainQuestEncryptionMap();
            String path = "QuestEncryptionKeys.json";
            try {
                JsonUtils.loadToList(getResourcePath(path), QuestEncryptionKey.class).forEach(key -> questEncryptionMap.put(key.getMainQuestId(), key));
            } catch (IOException | NullPointerException ignored) {}
            try {
                DataLoader.loadList(path, QuestEncryptionKey.class).forEach(key -> questEncryptionMap.put(key.getMainQuestId(), key));
            } catch (IOException | NullPointerException ignored) {}
            Grasscutter.getLogger().debug("Loaded {} quest keys.", questEncryptionMap.size());
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load quest keys.", e);
        }

        Grasscutter.getLogger().debug("Loaded " + GameData.getMainQuestDataMap().size() + " MainQuestDatas.");
    }

    public static void loadScriptSceneData() {
        try {
            Files.list(getResourcePath("ScriptSceneData/")).forEach(path -> {
                try {
                    GameData.getScriptSceneDataMap().put(path.getFileName().toString(), JsonUtils.loadToClass(path, ScriptSceneData.class));
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            });
            Grasscutter.getLogger().debug("Loaded " + GameData.getScriptSceneDataMap().size() + " ScriptSceneDatas.");
        } catch (IOException e) {
            Grasscutter.getLogger().debug("ScriptSceneData folder missing or empty.");
            return;
        }
    }

    private static void loadHomeworldDefaultSaveData() {
        val pattern = Pattern.compile("scene([0-9]+)_home_config\\.json");
        try {
            Files.newDirectoryStream(getResourcePath("BinOutput/HomeworldDefaultSave"), "scene*_home_config.json").forEach(path -> {
                val matcher = pattern.matcher(path.getFileName().toString());
                if (!matcher.find()) return;

                try {
                    val sceneId = Integer.parseInt(matcher.group(1));
                    val data = JsonUtils.loadToClass(path, HomeworldDefaultSaveData.class);
                    GameData.getHomeworldDefaultSaveData().put(sceneId, data);
                } catch (Exception ignored) {}
            });
            Grasscutter.getLogger().debug("Loaded " + GameData.getHomeworldDefaultSaveData().size() + " HomeworldDefaultSaveDatas.");
        } catch (IOException e) {
            Grasscutter.getLogger().error("Failed to load HomeworldDefaultSave folder.");
        }
    }

    private static void loadNpcBornData() {
        try {
            Files.newDirectoryStream(getResourcePath("BinOutput/Scene/SceneNpcBorn/"), "*.json").forEach(path -> {
                try {
                    val data = JsonUtils.loadToClass(path, SceneNpcBornData.class);
                    if (data.getBornPosList() == null || data.getBornPosList().size() == 0) {
                        return;
                    }

                    data.setIndex(SceneIndexManager.buildIndex(3, data.getBornPosList(), item -> item.getPos().toPoint()));
                    GameData.getSceneNpcBornData().put(data.getSceneId(), data);
                } catch (IOException ignored) {}
            });
            Grasscutter.getLogger().debug("Loaded " + GameData.getSceneNpcBornData().size() + " SceneNpcBornDatas.");
        } catch (IOException e) {
            Grasscutter.getLogger().error("Failed to load SceneNpcBorn folder.");
        }
    }

    private static void loadGadgetConfigData() {
        try {
            Files.newDirectoryStream(getResourcePath("BinOutput/Gadget/"), "*.json").forEach(path -> {
                try {
                    GameData.getGadgetConfigData().putAll(JsonUtils.loadToMap(path, String.class, ConfigGadget.class));
                } catch (Exception e) {
                    Grasscutter.getLogger().error("failed to load ConfigGadget entries for " + path.toString(), e);
                    return;
                }
            });

            Grasscutter.getLogger().debug("Loaded {} ConfigGadget entries.", GameData.getGadgetConfigData().size());
        } catch (IOException e) {
            Grasscutter.getLogger().error("Failed to load ConfigGadget folder.");
        }
    }

    private static void loadBlossomResources() {
        try {
            GameDepot.setBlossomConfig(DataLoader.loadClass("BlossomConfig.json", BlossomConfig.class));
            Grasscutter.getLogger().debug("Loaded BlossomConfig.");
        } catch (IOException e) {
            Grasscutter.getLogger().warn("Failed to load BlossomConfig.");
        }
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
