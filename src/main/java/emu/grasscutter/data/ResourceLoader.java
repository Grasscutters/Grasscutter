package emu.grasscutter.data;

import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import emu.grasscutter.utils.Utils;
import org.reflections.Reflections;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.common.PointData;
import emu.grasscutter.data.common.ScenePointConfig;
import emu.grasscutter.data.custom.AbilityEmbryoEntry;
import emu.grasscutter.data.custom.OpenConfigEntry;
import emu.grasscutter.data.custom.ScenePointEntry;
import emu.grasscutter.game.world.SpawnDataEntry;
import emu.grasscutter.game.world.SpawnDataEntry.SpawnGroupEntry;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public class ResourceLoader {

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
	
	public static void loadAll() {
		// Load ability lists
		loadAbilityEmbryos();
		loadOpenConfig();
		// Load resources
		loadResources();
		// Process into depots
		GameDepot.load();
		// Load spawn data
		loadSpawnData();
		// Load scene points - must be done AFTER resources are loaded
		loadScenePoints();
		// Custom - TODO move this somewhere else
		try {
			GameData.getAvatarSkillDepotDataMap().get(504).setAbilities(
				new AbilityEmbryoEntry(
					"", 
					new String[] {
						"Avatar_PlayerBoy_ExtraAttack_Wind",
						"Avatar_Player_UziExplode_Mix",
						"Avatar_Player_UziExplode",
						"Avatar_Player_UziExplode_Strike_01",
						"Avatar_Player_UziExplode_Strike_02",
						"Avatar_Player_WindBreathe",
						"Avatar_Player_WindBreathe_CameraController"
					}
			));
			GameData.getAvatarSkillDepotDataMap().get(704).setAbilities(
				new AbilityEmbryoEntry(
					"", 
					new String[] {
						"Avatar_PlayerGirl_ExtraAttack_Wind",
						"Avatar_Player_UziExplode_Mix",
						"Avatar_Player_UziExplode",
						"Avatar_Player_UziExplode_Strike_01",
						"Avatar_Player_UziExplode_Strike_02",
						"Avatar_Player_WindBreathe",
						"Avatar_Player_WindBreathe_CameraController"
					}
			));
		} catch (Exception e) {
			Grasscutter.getLogger().error("Error loading abilities", e);
		}
	}

	public static void loadResources() {
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
				loadFromResource(resourceDefinition, type, map);
			} catch (Exception e) {
				Grasscutter.getLogger().error("Error loading resource file: " + Arrays.toString(type.name()), e);
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	protected static void loadFromResource(Class<?> c, ResourceType type, Int2ObjectMap map) throws Exception {
		for (String name : type.name()) {
			loadFromResource(c, name, map);
		}
		Grasscutter.getLogger().info("Loaded " + map.size() + " " + c.getSimpleName() + "s.");
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	protected static void loadFromResource(Class<?> c, String fileName, Int2ObjectMap map) throws Exception {
		FileReader fileReader = new FileReader(Grasscutter.getConfig().RESOURCE_FOLDER + "ExcelBinOutput/" + fileName);
		Gson gson = Grasscutter.getGsonFactory();
		List list = gson.fromJson(fileReader, List.class);

		for (Object o : list) {
			Map<String, Object> tempMap = Utils.switchPropertiesUpperLowerCase((Map<String, Object>) o, c);
			GameResource res = gson.fromJson(gson.toJson(tempMap), TypeToken.get(c).getType());
			res.onLoad();
			map.put(res.getId(), res);
		}
	}

	private static void loadScenePoints() {
		Pattern pattern = Pattern.compile("(?<=scene)(.*?)(?=_point.json)");
		File folder = new File(Grasscutter.getConfig().RESOURCE_FOLDER + "BinOutput/Scene/Point");

		if (!folder.isDirectory() || !folder.exists() || folder.listFiles() == null) {
			Grasscutter.getLogger().error("Scene point files cannot be found, you cannot use teleport waypoints!");
			return;
		}

		List<ScenePointEntry> scenePointList = new ArrayList<>();
		for (File file : Objects.requireNonNull(folder.listFiles())) {
			ScenePointConfig config = null;
			Integer sceneId = null;
			
			Matcher matcher = pattern.matcher(file.getName());
			if (matcher.find()) {
				sceneId = Integer.parseInt(matcher.group(1));
			} else {
				continue;
			}

			try (FileReader fileReader = new FileReader(file)) {
				config = Grasscutter.getGsonFactory().fromJson(fileReader, ScenePointConfig.class);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			if (config.points == null) {
				continue;
			}

			for (Map.Entry<String, JsonElement> entry : config.points.entrySet()) {
				PointData pointData = Grasscutter.getGsonFactory().fromJson(entry.getValue(), PointData.class);
				pointData.setId(Integer.parseInt(entry.getKey()));

				ScenePointEntry sl = new ScenePointEntry(sceneId + "_" + entry.getKey(), pointData);
				scenePointList.add(sl);
				GameData.getScenePointIdList().add(pointData.getId());
				
				pointData.updateDailyDungeon();
			}

			for (ScenePointEntry entry : scenePointList) {
				GameData.getScenePointEntries().put(entry.getName(), entry);
			}
		}
	}

	private static void loadAbilityEmbryos() {
		// Read from cached file if exists
		File embryoCache = new File(Grasscutter.getConfig().DATA_FOLDER + "AbilityEmbryos.json");
		List<AbilityEmbryoEntry> embryoList = null;
		
		if (embryoCache.exists()) {
			// Load from cache
			try (FileReader fileReader = new FileReader(embryoCache)) {
				embryoList = Grasscutter.getGsonFactory().fromJson(fileReader, TypeToken.getParameterized(Collection.class, AbilityEmbryoEntry.class).getType());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// Load from BinOutput
			Pattern pattern = Pattern.compile("(?<=ConfigAvatar_)(.*?)(?=.json)");

			embryoList = new LinkedList<>();
			File folder = new File(Utils.toFilePath(Grasscutter.getConfig().RESOURCE_FOLDER + "BinOutput/Avatar/"));
			File[] files = folder.listFiles();
			if(files == null) {
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

				try (FileReader fileReader = new FileReader(file)) {
					config = Grasscutter.getGsonFactory().fromJson(fileReader, AvatarConfig.class);
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
		}
		
		if (embryoList == null || embryoList.isEmpty()) {
			Grasscutter.getLogger().error("No embryos loaded!");
			return;
		}

		for (AbilityEmbryoEntry entry : embryoList) {
			GameData.getAbilityEmbryoInfo().put(entry.getName(), entry);
		}
	}
	
	private static void loadSpawnData() {
		// Read from cached file if exists
		File spawnDataEntries = new File(Grasscutter.getConfig().DATA_FOLDER + "Spawns.json");
		List<SpawnGroupEntry> spawnEntryList = null;
		
		if (spawnDataEntries.exists()) {
			// Load from cache
			try (FileReader fileReader = new FileReader(spawnDataEntries)) {
				spawnEntryList = Grasscutter.getGsonFactory().fromJson(fileReader, TypeToken.getParameterized(Collection.class, SpawnGroupEntry.class).getType());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (spawnEntryList == null || spawnEntryList.isEmpty()) {
			Grasscutter.getLogger().error("No spawn data loaded!");
			return;
		}

		for (SpawnGroupEntry entry : spawnEntryList) {
			entry.getSpawns().stream().forEach(s -> {
				s.setGroup(entry);
			});
			GameDepot.getSpawnListById(entry.getSceneId()).insert(entry, entry.getPos().getX(), entry.getPos().getZ());
		}
	}
	
	private static void loadOpenConfig() {
		// Read from cached file if exists
		File openConfigCache = new File(Grasscutter.getConfig().DATA_FOLDER + "OpenConfig.json");
		List<OpenConfigEntry> list = null;
		
		if (openConfigCache.exists()) {
			try (FileReader fileReader = new FileReader(openConfigCache)) {
				list = Grasscutter.getGsonFactory().fromJson(fileReader, TypeToken.getParameterized(Collection.class, OpenConfigEntry.class).getType());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Map<String, OpenConfigEntry> map = new TreeMap<>();
			java.lang.reflect.Type type = new TypeToken<Map<String, OpenConfigData[]>>() {}.getType();
			String[] folderNames = {"BinOutput/Talent/EquipTalents/", "BinOutput/Talent/AvatarTalents/"};
			
			for (String name : folderNames) {
				File folder = new File(Utils.toFilePath(Grasscutter.getConfig().RESOURCE_FOLDER + name));
				File[] files = folder.listFiles();
				if(files == null) {
					Grasscutter.getLogger().error("Error loading open config: no files found in " + folder.getAbsolutePath()); return;
				}
				
				for (File file : files) {
					if (!file.getName().endsWith(".json")) {
						continue;
					}
					
					Map<String, OpenConfigData[]> config;
					
					try (FileReader fileReader = new FileReader(file)) {
						config = Grasscutter.getGsonFactory().fromJson(fileReader, type);
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

	// BinOutput configs
	
	private static class AvatarConfig {
		public ArrayList<AvatarConfigAbility> abilities;
		
		private static class AvatarConfigAbility {
			public String abilityName;
			public String toString() {
				return abilityName;
			}
		}
	}
	
	private static class OpenConfig {
		public OpenConfigData[] data;
	}
	
	public static class OpenConfigData {
		public String $type;
		public String abilityName;
		public int talentIndex;
		public int skillID;
		public int pointDelta;
	}
}
