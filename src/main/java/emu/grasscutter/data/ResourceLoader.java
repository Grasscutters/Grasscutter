package emu.grasscutter.data;

import static emu.grasscutter.utils.FileUtils.*;
import static emu.grasscutter.utils.lang.Language.translate;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.*;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.data.binout.config.*;
import emu.grasscutter.data.binout.routes.*;
import emu.grasscutter.data.common.PointData;
import emu.grasscutter.data.custom.*;
import emu.grasscutter.data.excels.trial.TrialAvatarActivityDataData;
import emu.grasscutter.data.server.*;
import emu.grasscutter.game.activity.ActivityManager;
import emu.grasscutter.game.managers.blossom.BlossomConfig;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.world.*;
import emu.grasscutter.game.world.SpawnDataEntry.*;
import emu.grasscutter.scripts.*;
import emu.grasscutter.utils.*;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.stream.*;
import javax.script.*;
import lombok.*;

public final class ResourceLoader {

    private static final Set<String> loadedResources = new CopyOnWriteArraySet<>();
    private static boolean loadedAll = false;

    // Get a list of all resource classes, sorted by loadPriority
    public static List<Class<?>> getResourceDefClasses() {
        Set<?> classes = Grasscutter.reflector.getSubTypesOf(GameResource.class);

        List<Class<?>> classList = new ArrayList<>(classes.size());
        classes.forEach(
                o -> {
                    Class<?> c = (Class<?>) o;
                    if (c.getAnnotation(ResourceType.class) != null) {
                        classList.add(c);
                    }
                });

        classList.sort(
                (a, b) ->
                        b.getAnnotation(ResourceType.class).loadPriority().value()
                                - a.getAnnotation(ResourceType.class).loadPriority().value());

        return classList;
    }

    // Get a list containing sets of all resource classes, sorted by loadPriority
    private static List<Set<Class<?>>> getResourceDefClassesPrioritySets() {
        val classes = Grasscutter.reflector.getSubTypesOf(GameResource.class);
        val priorities = ResourceType.LoadPriority.getInOrder();
        Grasscutter.getLogger().debug("Priorities are " + priorities);
        val map = new LinkedHashMap<ResourceType.LoadPriority, Set<Class<?>>>(priorities.size());
        priorities.forEach(p -> map.put(p, new HashSet<>()));

        classes.forEach(
                c -> {
                    // val c = (Class<?>) o;
                    val annotation = c.getAnnotation(ResourceType.class);
                    if (annotation != null) {
                        map.get(annotation.loadPriority()).add(c);
                    }
                });
        return List.copyOf(map.values());
    }

    @SneakyThrows
    public static void loadAll() {
        if (loadedAll) return;
        Grasscutter.getLogger().info(translate("messages.status.resources.loading"));

        // Initialize the script loader.
        ScriptLoader.init();

        loadConfigData();
        // Load ability lists
        loadAbilityEmbryos();
        loadTalents();
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
        loadRoutes();
        loadBlossomResources();
        cacheTalentLevelSets();
        // Load activities.
        ActivityManager.loadActivityConfigData();

        // Load custom server resources.
        loadConfigLevelEntityData();
        loadQuestShareConfig();
        loadGadgetMappings();
        loadSubfieldMappings();
        loadMonsterMappings();
        loadActivityCondGroups();
        loadGroupReplacements();
        loadTrialAvatarCustomData();
        loadGlobalCombatConfig();

        EntityControllerScriptManager.load();

        Grasscutter.getLogger().info(translate("messages.status.resources.finish"));
        loadedAll = true;
    }

    public static void loadResources() {
        loadResources(false);
    }

    public static void loadResources(boolean doReload) {
        long startTime = System.nanoTime();
        val errors =
                new ConcurrentLinkedQueue<
                        Pair<String, Exception>>(); // Logger in a parallel stream will deadlock

        getResourceDefClassesPrioritySets()
                .forEach(
                        classes -> {
                            classes.stream()
                                    .parallel()
                                    .unordered()
                                    .forEach(
                                            c -> {
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
        errors.forEach(
                pair ->
                        Grasscutter.getLogger()
                                .error("Error loading resource file: " + pair.left(), pair.right()));
        long endTime = System.nanoTime();
        long ns = (endTime - startTime); // divide by 1000000 to get milliseconds.
        Grasscutter.getLogger().debug("Loading resources took " + ns + "ns == " + ns / 1000000 + "ms");
    }

    @SuppressWarnings("rawtypes")
    protected static void loadFromResource(
            Class<?> c, ResourceType type, Int2ObjectMap map, boolean doReload) throws Exception {
        val simpleName = c.getSimpleName();
        if (doReload || !loadedResources.contains(simpleName)) {
            for (String name : type.name()) {
                loadFromResource(c, FileUtils.getExcelPath(name), map);
            }
            loadedResources.add(simpleName);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected static <T> void loadFromResource(Class<T> c, Path filename, Int2ObjectMap map)
            throws Exception {
        val results =
                switch (FileUtils.getFileExtension(filename)) {
                    case "json" -> JsonUtils.loadToList(filename, c);
                    case "tsj" -> TsvUtils.loadTsjToListSetField(filename, c);
                    case "tsv" -> TsvUtils.loadTsvToListSetField(filename, c);
                    default -> null;
                };
        if (results == null) return;
        results.forEach(
                o -> {
                    GameResource res = (GameResource) o;
                    res.onLoad();
                    map.put(res.getId(), res);
                });
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected static <T> void loadFromResource(Class<T> c, String fileName, Int2ObjectMap map)
            throws Exception {
        JsonUtils.loadToList(getResourcePath("ExcelBinOutput/" + fileName), c)
                .forEach(
                        o -> {
                            GameResource res = (GameResource) o;
                            res.onLoad();
                            map.put(res.getId(), res);
                        });
    }

    private static void loadGlobalCombatConfig() {
        try {
            GameData.setConfigGlobalCombat(
                    JsonUtils.loadToClass(
                            getResourcePath("BinOutput/Common/ConfigGlobalCombat.json"),
                            ConfigGlobalCombat.class));
        } catch (IOException e) {
            Grasscutter.getLogger()
                    .error("Cannot load ConfigGlobalCombat.json, this error is important, fix it!");
        }
    }

    private static void loadScenePoints() {
        val pattern = Pattern.compile("scene([0-9]+)_point\\.json");
        try {
            Files.newDirectoryStream(getResourcePath("BinOutput/Scene/Point"), "scene*_point.json")
                    .forEach(
                            path -> {
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
                                config.points.forEach(
                                        (pointId, pointData) -> {
                                            val scenePoint = new ScenePointEntry(sceneId, pointData);
                                            scenePoints.add((int) pointId);
                                            pointData.setId(pointId);

                                            GameData.getScenePointIdList().add((int) pointId);
                                            GameData.getScenePointEntryMap().put((sceneId << 16) + pointId, scenePoint);

                                            pointData.updateDailyDungeon();
                                        });
                                GameData.getScenePointsPerScene().put(sceneId, scenePoints);
                            });
        } catch (IOException ignored) {
            Grasscutter.getLogger()
                    .error("Scene point files cannot be found, you cannot use teleport waypoints!");
        }
    }

    private static void loadRoutes() {
        try {
            Files.newDirectoryStream(getResourcePath("BinOutput/LevelDesign/Routes/"), "*.json")
                    .forEach(
                            path -> {
                                try {
                                    val data = JsonUtils.loadToClass(path, SceneRoutes.class);
                                    val routesArray = data.getRoutes();
                                    if (routesArray == null) return;
                                    val routesMap =
                                            GameData.getSceneRouteData()
                                                    .getOrDefault(data.getSceneId(), new Int2ObjectOpenHashMap<>());
                                    for (Route route : routesArray) {
                                        routesMap.put(route.getLocalId(), route);
                                    }
                                    GameData.getSceneRouteData().put(data.getSceneId(), routesMap);
                                } catch (IOException ignored) {
                                }
                            });
            Grasscutter.getLogger()
                    .debug("Loaded " + GameData.getSceneNpcBornData().size() + " SceneRouteDatas.");
        } catch (IOException e) {
            Grasscutter.getLogger().error("Failed to load SceneRouteData folder.");
        }
    }

    private static void cacheTalentLevelSets() {
        // All known levels, keyed by proudSkillGroupId
        GameData.getProudSkillDataMap()
                .forEach(
                        (id, data) ->
                                GameData.getProudSkillGroupLevels()
                                        .computeIfAbsent(data.getProudSkillGroupId(), i -> new IntArraySet())
                                        .add(data.getLevel()));
        // All known levels, keyed by avatarSkillId
        GameData.getAvatarSkillDataMap()
                .forEach(
                        (id, data) ->
                                GameData.getAvatarSkillLevels()
                                        .put(
                                                (int) id,
                                                GameData.getProudSkillGroupLevels().get(data.getProudSkillGroupId())));
        // Maximum known levels, keyed by proudSkillGroupId
        GameData.getProudSkillGroupLevels()
                .forEach(
                        (id, set) ->
                                GameData.getProudSkillGroupMaxLevels()
                                        .put((int) id, set.intStream().max().orElse(-1)));
    }

    private static void loadAbilityEmbryos() {
        List<AbilityEmbryoEntry> embryoList = null;

        // Read from cached file if exists
        try {
            embryoList =
                    JsonUtils.loadToList(getDataPath("AbilityEmbryos.json"), AbilityEmbryoEntry.class);
        } catch (Exception ignored) {
        }

        if (embryoList == null) {
            // Load from BinOutput
            var pattern = Pattern.compile("ConfigAvatar_(.+?)\\.json");

            var entries = new ArrayList<AbilityEmbryoEntry>();
            try (var stream =
                    Files.newDirectoryStream(getResourcePath("BinOutput/Avatar/"), "ConfigAvatar_*.json")) {

                stream.forEach(
                        path -> {
                            var matcher = pattern.matcher(path.getFileName().toString());
                            if (!matcher.find()) return;

                            var avatarName = matcher.group(1);
                            AvatarConfig config;
                            try {
                                config = JsonUtils.loadToClass(path, AvatarConfig.class);
                            } catch (Exception e) {
                                Grasscutter.getLogger().error("Error loading player ability embryos:", e);
                                return;
                            }

                            if (config.abilities == null) return;

                            entries.add(
                                    new AbilityEmbryoEntry(
                                            avatarName,
                                            config.abilities.stream()
                                                    .map(Object::toString)
                                                    .toArray(size -> new String[config.abilities.size()])));
                        });
            } catch (IOException e) {
                Grasscutter.getLogger().error("Error loading ability embryos: no files found");
                return;
            }

            embryoList = entries;

            try {
                GameDepot.setPlayerAbilities(
                        JsonUtils.loadToMap(
                                getResourcePath(
                                        "BinOutput/AbilityGroup/AbilityGroup_Other_PlayerElementAbility.json"),
                                String.class,
                                AvatarConfig.class));
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

    private static void loadAbilityModifiers() {
        // Load from BinOutput
        try (Stream<Path> paths = Files.walk(getResourcePath("BinOutput/Ability/Temp/"))) {
            paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json"))
                    .forEach(ResourceLoader::loadAbilityModifiers);
        } catch (IOException e) {
            Grasscutter.getLogger().error("Error loading ability modifiers: ", e);
        }
        // System.out.println("Loaded modifiers, found types:");
        // modifierActionTypes.stream().sorted().forEach(s -> System.out.printf("%s, ", s));
        // System.out.println("[End]");
    }

    private static void loadAbilityModifiers(Path path) {
        try {
            JsonUtils.loadToList(path, AbilityConfigData.class)
                    .forEach(data -> loadAbilityData(data.Default));
        } catch (IOException e) {
            Grasscutter.getLogger()
                    .error("Error loading ability modifiers from path " + path.toString() + ": ", e);
        }
    }

    private static void loadAbilityData(AbilityData data) {
        GameData.getAbilityDataMap().put(data.abilityName, data);
        GameData.getAbilityHashes().put(Utils.abilityHash(data.abilityName), data.abilityName);

        var modifiers = data.modifiers;
        if (modifiers == null || modifiers.size() == 0) return;

        var name = data.abilityName;
        var modifierEntry = new AbilityModifierEntry(name);
        modifiers.forEach(
                (key, modifier) -> {
                    Stream.ofNullable(modifier.onAdded)
                            .flatMap(Stream::of)
                            // .map(action -> {modifierActionTypes.add(action.$type); return action;})
                            .filter(action -> action.type == AbilityModifierAction.Type.HealHP)
                            .forEach(action -> modifierEntry.getOnAdded().add(action));
                    Stream.ofNullable(modifier.onThinkInterval)
                            .flatMap(Stream::of)
                            // .map(action -> {modifierActionTypes.add(action.$type); return action;})
                            .filter(action -> action.type == AbilityModifierAction.Type.HealHP)
                            .forEach(action -> modifierEntry.getOnThinkInterval().add(action));
                    Stream.ofNullable(modifier.onRemoved)
                            .flatMap(Stream::of)
                            // .map(action -> {modifierActionTypes.add(action.$type); return action;})
                            .filter(action -> action.type == AbilityModifierAction.Type.HealHP)
                            .forEach(action -> modifierEntry.getOnRemoved().add(action));
                });
    }

    private static void loadTalents() {
        // Load from BinOutput
        try (var paths = Files.walk(getResourcePath("BinOutput/Talent/AvatarTalents/"))) {
            paths
                    .filter(Files::isDirectory)
                    .forEach(
                            (folderPath) -> {
                                try (var paths2 = Files.walk(folderPath)) {
                                    paths2
                                            .filter(Files::isRegularFile)
                                            .filter(path -> path.toString().endsWith(".json"))
                                            .forEach(ResourceLoader::loadTalent);
                                } catch (IOException e) {
                                    Grasscutter.getLogger().error("Error loading talents: ", e);
                                }
                            });
        } catch (IOException e) {
            Grasscutter.getLogger().error("Error loading talents: ", e);
        }
    }

    private static void loadTalent(Path path) {
        try {
            GameData.getTalents()
                    .putAll(
                            JsonUtils.loadToMap(
                                    path, String.class, new TypeToken<List<TalentData>>() {}.getType()));
        } catch (IOException e) {
            Grasscutter.getLogger()
                    .error("Error loading ability modifiers from path " + path.toString() + ": ", e);
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
            } catch (Exception ignored) {
            }
        }

        if (spawnEntryMap.isEmpty()) {
            Grasscutter.getLogger().error("No spawn data loaded!");
            return;
        }

        HashMap<GridBlockId, ArrayList<SpawnDataEntry>> areaSort = new HashMap<>();
        // key = sceneId,x,z , value = ArrayList<SpawnDataEntry>
        for (SpawnGroupEntry entry : spawnEntryMap) {
            entry
                    .getSpawns()
                    .forEach(
                            s -> {
                                s.setGroup(entry);
                                GridBlockId point = s.getBlockId();
                                if (!areaSort.containsKey(point)) {
                                    areaSort.put(point, new ArrayList<>());
                                }
                                areaSort.get(point).add(s);
                            });
        }
        GameDepot.addSpawnListById(areaSort);
    }

    private static void loadOpenConfig() {
        // Read from cached file if exists
        List<OpenConfigEntry> list = null;

        try {
            list = JsonUtils.loadToList(getDataPath("OpenConfig.json"), OpenConfigEntry.class);
        } catch (Exception ignored) {
        }

        if (list == null) {
            Map<String, OpenConfigEntry> map = new TreeMap<>();
            String[] folderNames = {"BinOutput/Talent/EquipTalents/", "BinOutput/Talent/AvatarTalents/"};

            for (String folderName : folderNames) {
                try {
                    Files.newDirectoryStream(getResourcePath(folderName), "*.json")
                            .forEach(
                                    path -> {
                                        try {
                                            JsonUtils.loadToMap(path, String.class, OpenConfigData[].class)
                                                    .forEach((name, data) -> map.put(name, new OpenConfigEntry(name, data)));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    });
                } catch (IOException e) {
                    Grasscutter.getLogger()
                            .error("Error loading open config: no files found in " + folderName);
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
        try (var files = Files.list(getResourcePath("BinOutput/Quest/"))) {
            files.forEach(
                    path -> {
                        try {
                            val mainQuest = JsonUtils.loadToClass(path, MainQuestData.class);
                            GameData.getMainQuestDataMap().put(mainQuest.getId(), mainQuest);

                            mainQuest.onLoad(); // Load the quest data.
                        } catch (IOException ignored) {
                        }
                    });
        } catch (IOException e) {
            Grasscutter.getLogger().error("Quest data missing");
            return;
        }

        try {
            val questEncryptionMap = GameData.getMainQuestEncryptionMap();
            var path = "QuestEncryptionKeys.json";
            try {
                JsonUtils.loadToList(getResourcePath(path), QuestEncryptionKey.class)
                        .forEach(key -> questEncryptionMap.put(key.getMainQuestId(), key));
            } catch (IOException | NullPointerException ignored) {
            }

            try {
                DataLoader.loadList(path, QuestEncryptionKey.class)
                        .forEach(key -> questEncryptionMap.put(key.getMainQuestId(), key));
            } catch (IOException | NullPointerException ignored) {
            }

            Grasscutter.getLogger().debug("Loaded {} quest keys.", questEncryptionMap.size());
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load quest keys.", e);
        }

        Grasscutter.getLogger()
                .debug("Loaded " + GameData.getMainQuestDataMap().size() + " MainQuestDatas.");
    }

    public static void loadScriptSceneData() {
        try {
            Files.list(getResourcePath("ScriptSceneData/"))
                    .forEach(
                            path -> {
                                try {
                                    GameData.getScriptSceneDataMap()
                                            .put(
                                                    path.getFileName().toString(),
                                                    JsonUtils.loadToClass(path, ScriptSceneData.class));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
            Grasscutter.getLogger()
                    .debug("Loaded " + GameData.getScriptSceneDataMap().size() + " ScriptSceneDatas.");
        } catch (IOException e) {
            Grasscutter.getLogger().debug("ScriptSceneData folder missing or empty.");
        }
    }

    private static void loadHomeworldDefaultSaveData() {
        val pattern = Pattern.compile("scene([0-9]+)_home_config\\.json");
        try {
            Files.newDirectoryStream(
                            getResourcePath("BinOutput/HomeworldDefaultSave"), "scene*_home_config.json")
                    .forEach(
                            path -> {
                                val matcher = pattern.matcher(path.getFileName().toString());
                                if (!matcher.find()) return;

                                try {
                                    val sceneId = Integer.parseInt(matcher.group(1));
                                    val data = JsonUtils.loadToClass(path, HomeworldDefaultSaveData.class);
                                    GameData.getHomeworldDefaultSaveData().put(sceneId, data);
                                } catch (Exception ignored) {
                                }
                            });
            Grasscutter.getLogger()
                    .debug(
                            "Loaded "
                                    + GameData.getHomeworldDefaultSaveData().size()
                                    + " HomeworldDefaultSaveDatas.");
        } catch (IOException e) {
            Grasscutter.getLogger().error("Failed to load HomeworldDefaultSave folder.");
        }
    }

    private static void loadNpcBornData() {
        try {
            Files.newDirectoryStream(getResourcePath("BinOutput/Scene/SceneNpcBorn/"), "*.json")
                    .forEach(
                            path -> {
                                try {
                                    val data = JsonUtils.loadToClass(path, SceneNpcBornData.class);
                                    if (data.getBornPosList() == null || data.getBornPosList().size() == 0) {
                                        return;
                                    }

                                    data.setIndex(
                                            SceneIndexManager.buildIndex(
                                                    3, data.getBornPosList(), item -> item.getPos().toPoint()));
                                    GameData.getSceneNpcBornData().put(data.getSceneId(), data);
                                } catch (IOException ignored) {
                                }
                            });
            Grasscutter.getLogger()
                    .debug("Loaded " + GameData.getSceneNpcBornData().size() + " SceneNpcBornDatas.");
        } catch (IOException e) {
            Grasscutter.getLogger().error("Failed to load SceneNpcBorn folder.");
        }
    }

    private static void loadConfigData() {
        loadConfigData(GameData.getAvatarConfigData(), "BinOutput/Avatar/", ConfigEntityAvatar.class);
        loadConfigData(
                GameData.getMonsterConfigData(), "BinOutput/Monster/", ConfigEntityMonster.class);
        loadConfigDataMap(
                GameData.getGadgetConfigData(), "BinOutput/Gadget/", ConfigEntityGadget.class);
    }

    private static <T extends ConfigEntityBase> void loadConfigData(
            Map<String, T> targetMap, String folderPath, Class<T> configClass) {
        val className = configClass.getName();
        try (val stream = Files.newDirectoryStream(getResourcePath(folderPath), "*.json")) {
            stream.forEach(
                    path -> {
                        try {
                            val name = path.getFileName().toString().replace(".json", "");
                            targetMap.put(name, JsonUtils.loadToClass(path, configClass));
                        } catch (Exception e) {
                            Grasscutter.getLogger()
                                    .error("failed to load {} entries for {}", className, path.toString(), e);
                        }
                    });

            Grasscutter.getLogger()
                    .debug("Loaded {} {} entries.", GameData.getMonsterConfigData().size(), className);
        } catch (IOException e) {
            Grasscutter.getLogger().error("Failed to load {} folder.", className);
        }
    }

    private static <T extends ConfigEntityBase> void loadConfigDataMap(
            Map<String, T> targetMap, String folderPath, Class<T> configClass) {
        val className = configClass.getName();
        try (val stream = Files.newDirectoryStream(getResourcePath(folderPath), "*.json")) {
            stream.forEach(
                    path -> {
                        try {
                            targetMap.putAll(JsonUtils.loadToMap(path, String.class, configClass));
                        } catch (Exception e) {
                            Grasscutter.getLogger()
                                    .error("failed to load {} entries for {}", className, path.toString(), e);
                        }
                    });

            Grasscutter.getLogger()
                    .debug("Loaded {} {} entries.", GameData.getMonsterConfigData().size(), className);
        } catch (IOException e) {
            Grasscutter.getLogger().error("Failed to load {} folder.", className);
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

    private static void loadConfigLevelEntityData() {
        // Load from BinOutput
        val pattern = Pattern.compile("ConfigLevelEntity_(.+?)\\.json");

        try {
            var stream =
                    Files.newDirectoryStream(
                            getResourcePath("BinOutput/LevelEntity/"), "ConfigLevelEntity_*.json");
            stream.forEach(
                    path -> {
                        val matcher = pattern.matcher(path.getFileName().toString());
                        if (!matcher.find()) return;
                        Map<String, ConfigLevelEntity> config;

                        try {
                            config = JsonUtils.loadToMap(path, String.class, ConfigLevelEntity.class);
                        } catch (Exception e) {
                            Grasscutter.getLogger().error("Error loading player ability embryos:", e);
                            return;
                        }
                        GameData.getConfigLevelEntityDataMap().putAll(config);
                    });
            stream.close();
        } catch (IOException e) {
            Grasscutter.getLogger().error("Error loading config level entity: no files found");
            return;
        }

        if (GameData.getConfigLevelEntityDataMap() == null
                || GameData.getConfigLevelEntityDataMap().isEmpty()) {
            Grasscutter.getLogger().error("No config level entity loaded!");
            return;
        }
    }

    private static void loadQuestShareConfig() {
        // Load from BinOutput
        val pattern = Pattern.compile("Q(.+?)\\ShareConfig.lua");

        try {
            var bindings = ScriptLoader.getEngine().createBindings();
            var stream =
                    Files.newDirectoryStream(getResourcePath("Scripts/Quest/Share/"), "Q*ShareConfig.lua");
            stream.forEach(
                    path -> {
                        val matcher = pattern.matcher(path.getFileName().toString());
                        if (!matcher.find()) return;

                        var cs = ScriptLoader.getScript("Quest/Share/" + path.getFileName().toString());
                        if (cs == null) return;

                        try {
                            ScriptLoader.eval(cs, bindings);
                            // these are Map<String, class>
                            var teleportDataMap =
                                    ScriptLoader.getSerializer()
                                            .toMap(TeleportData.class, bindings.get("quest_data"));
                            var rewindDataMap =
                                    ScriptLoader.getSerializer().toMap(RewindData.class, bindings.get("rewind_data"));
                            // convert them to Map<Integer, class> and cache
                            GameData.getTeleportDataMap()
                                    .putAll(
                                            teleportDataMap.entrySet().stream()
                                                    .collect(
                                                            Collectors.toMap(
                                                                    entry -> Integer.valueOf(entry.getKey()), Entry::getValue)));
                            GameData.getRewindDataMap()
                                    .putAll(
                                            rewindDataMap.entrySet().stream()
                                                    .collect(
                                                            Collectors.toMap(
                                                                    entry -> Integer.valueOf(entry.getKey()), Entry::getValue)));
                        } catch (Throwable e) {
                            Grasscutter.getLogger()
                                    .error(
                                            "Error while loading Quest Share Config: {}", path.getFileName().toString());
                        }
                    });
            stream.close();
        } catch (IOException e) {
            Grasscutter.getLogger().error("Error loading Quest Share Config: no files found");
            return;
        }
        if (GameData.getTeleportDataMap() == null
                || GameData.getTeleportDataMap().isEmpty()
                || GameData.getRewindDataMap() == null
                || GameData.getRewindDataMap().isEmpty()) {
            Grasscutter.getLogger().error("No Quest Share Config loaded!");
            return;
        }
    }

    private static void loadGadgetMappings() {
        try {
            val gadgetMap = GameData.getGadgetMappingMap();
            try {
                JsonUtils.loadToList(getResourcePath("Server/GadgetMapping.json"), GadgetMapping.class)
                        .forEach(entry -> gadgetMap.put(entry.getGadgetId(), entry));
            } catch (IOException | NullPointerException ignored) {
            }
            Grasscutter.getLogger().debug("Loaded {} gadget mappings.", gadgetMap.size());
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load gadget mappings.", e);
        }
    }

    private static void loadSubfieldMappings() {
        try {
            val subfieldMap = GameData.getSubfieldMappingMap();
            try {
                JsonUtils.loadToList(getResourcePath("Server/SubfieldMapping.json"), SubfieldMapping.class)
                        .forEach(entry -> subfieldMap.put(entry.getEntityId(), entry));
                ;
            } catch (IOException | NullPointerException ignored) {
            }
            Grasscutter.getLogger().debug("Loaded {} subfield mappings.", subfieldMap.size());
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load subfield mappings.", e);
        }

        try {
            val dropSubfieldMap = GameData.getDropSubfieldMappingMap();
            try {
                JsonUtils.loadToList(
                                getResourcePath("Server/DropSubfieldMapping.json"), DropSubfieldMapping.class)
                        .forEach(entry -> dropSubfieldMap.put(entry.getDropId(), entry));
                ;
            } catch (IOException | NullPointerException ignored) {
            }
            Grasscutter.getLogger().debug("Loaded {} drop subfield mappings.", dropSubfieldMap.size());
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load drop subfield mappings.", e);
        }

        try {
            val dropTableExcelConfigDataMap = GameData.getDropTableExcelConfigDataMap();
            try {
                JsonUtils.loadToList(
                                getResourcePath("Server/DropTableExcelConfigData.json"),
                                DropTableExcelConfigData.class)
                        .forEach(entry -> dropTableExcelConfigDataMap.put(entry.getId(), entry));
                ;
            } catch (IOException | NullPointerException ignored) {
            }
            Grasscutter.getLogger()
                    .debug("Loaded {} drop table configs.", dropTableExcelConfigDataMap.size());
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load drop table config data.", e);
        }
    }

    private static void loadMonsterMappings() {
        try {
            var monsterMap = GameData.getMonsterMappingMap();
            try {
                JsonUtils.loadToList(getResourcePath("Server/MonsterMapping.json"), MonsterMapping.class)
                        .forEach(entry -> monsterMap.put(entry.getMonsterId(), entry));
            } catch (IOException | NullPointerException ignored) {
            }

            Grasscutter.getLogger().debug("Loaded {} monster mappings.", monsterMap.size());
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load monster mappings.", e);
        }
    }

    private static void loadActivityCondGroups() {
        try {
            val gadgetMap = GameData.getActivityCondGroupMap();
            try {
                JsonUtils.loadToList(
                                getResourcePath("Server/ActivityCondGroups.json"), ActivityCondGroup.class)
                        .forEach(entry -> gadgetMap.put(entry.getCondGroupId(), entry));
            } catch (IOException | NullPointerException ignored) {
            }
            Grasscutter.getLogger().debug("Loaded {} ActivityCondGroups.", gadgetMap.size());
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load ActivityCondGroups.", e);
        }
    }

    private static void loadTrialAvatarCustomData() {
        try {
            String pathName = "CustomResources/TrialAvatarExcels/";
            try {
                JsonUtils.loadToList(
                                getResourcePath(pathName + "TrialAvatarActivityDataExcelConfigData.json"),
                                TrialAvatarActivityDataData.class)
                        .forEach(
                                instance -> {
                                    instance.onLoad();
                                    GameData.getTrialAvatarActivityDataCustomData()
                                            .put(instance.getTrialAvatarIndexId(), instance);
                                });
            } catch (IOException | NullPointerException ignored) {
            }
            Grasscutter.getLogger().debug("Loaded trial activity custom data.");
            try {
                JsonUtils.loadToList(
                                getResourcePath(pathName + "TrialAvatarActivityExcelConfigData.json"),
                                TrialAvatarActivityCustomData.class)
                        .forEach(
                                instance -> {
                                    instance.onLoad();
                                    GameData.getTrialAvatarActivityCustomData()
                                            .put(instance.getScheduleId(), instance);
                                });
            } catch (IOException | NullPointerException ignored) {
            }
            Grasscutter.getLogger().debug("Loaded trial activity schedule custom data.");
            try {
                JsonUtils.loadToList(
                                getResourcePath(pathName + "TrialAvatarData.json"), TrialAvatarCustomData.class)
                        .forEach(
                                instance -> {
                                    instance.onLoad();
                                    GameData.getTrialAvatarCustomData().put(instance.getTrialAvatarId(), instance);
                                });
            } catch (IOException | NullPointerException ignored) {
            }
            Grasscutter.getLogger().debug("Loaded trial avatar custom data.");
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to load trial avatar custom data.", e);
        }
    }

    private static void loadGroupReplacements() {
        Bindings bindings = ScriptLoader.getEngine().createBindings();

        CompiledScript cs = ScriptLoader.getScript("Scene/groups_replacement.lua");
        if (cs == null) {
            Grasscutter.getLogger().error("Error while loading Group Replacements: file not found");
            return;
        }

        try {
            ScriptLoader.eval(cs, bindings);
            // these are Map<String, class>
            var replacementsMap =
                    ScriptLoader.getSerializer()
                            .toMap(GroupReplacementData.class, bindings.get("replacements"));
            // convert them to Map<Integer, class> and cache
            GameData.getGroupReplacements()
                    .putAll(
                            replacementsMap.entrySet().stream()
                                    .collect(
                                            Collectors.toMap(
                                                    entry -> Integer.valueOf(entry.getValue().getId()), Entry::getValue)));

        } catch (Throwable e) {
            Grasscutter.getLogger().error("Error while loading Group Replacements");
        }

        if (GameData.getGroupReplacements() == null || GameData.getGroupReplacements().isEmpty()) {
            Grasscutter.getLogger().error("No Group Replacements loaded!");
        } else {
            Grasscutter.getLogger()
                    .debug("Loaded {} group replacements.", GameData.getGroupReplacements().size());
        }
    }

    // private static HashSet<String> modifierActionTypes = new HashSet<>();
    public static class AbilityConfigData {
        public AbilityData Default;
    }

    public static class AvatarConfig {
        @SerializedName(
                value = "abilities",
                alternate = {"targetAbilities"})
        public ArrayList<AvatarConfigAbility> abilities;
    }

    // BinOutput configs

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

        @SerializedName(
                value = "talentIndex",
                alternate = {"OJOFFKLNAHN"})
        public int talentIndex;

        @SerializedName(
                value = "skillID",
                alternate = {"overtime"})
        public int skillID;

        @SerializedName(
                value = "pointDelta",
                alternate = {"IGEBKIHPOIF"})
        public int pointDelta;
    }

    public static
    class ScenePointConfig { // Sadly this doesn't work as a local class in loadScenePoints()
        public Map<Integer, PointData> points;
    }
}
