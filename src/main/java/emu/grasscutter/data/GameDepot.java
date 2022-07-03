package emu.grasscutter.data;

import com.github.davidmoten.rtreemulti.RTree;
import com.github.davidmoten.rtreemulti.geometry.Geometry;
import com.github.davidmoten.rtreemulti.geometry.Point;
import com.github.davidmoten.rtreemulti.geometry.Rectangle;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.ResourceLoader.AvatarConfig;
import emu.grasscutter.data.excels.ReliquaryAffixData;
import emu.grasscutter.data.excels.ReliquaryMainPropData;
import emu.grasscutter.game.world.SpawnDataEntry;
import emu.grasscutter.game.world.SpawnDataEntry.SpawnGroupEntry;
import emu.grasscutter.utils.Position;
import emu.grasscutter.utils.WeightedList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class GameDepot {
    private static Int2ObjectMap<WeightedList<ReliquaryMainPropData>> relicRandomMainPropDepot = new Int2ObjectOpenHashMap<>();
    private static Int2ObjectMap<List<ReliquaryMainPropData>> relicMainPropDepot = new Int2ObjectOpenHashMap<>();
    private static Int2ObjectMap<List<ReliquaryAffixData>> relicAffixDepot = new Int2ObjectOpenHashMap<>();

	private static Map<String, AvatarConfig> playerAbilities = new HashMap<>();
	private static Int2ObjectMap<RTree<SpawnGroupEntry, Geometry>> spawnLists = new Int2ObjectOpenHashMap<>();

	public static void load() {
		for (ReliquaryMainPropData data : GameData.getReliquaryMainPropDataMap().values()) {
			if (data.getWeight() <= 0 || data.getPropDepotId() <= 0) {
				continue;
			}
            List<ReliquaryMainPropData> list = relicMainPropDepot.computeIfAbsent(data.getPropDepotId(), k -> new ArrayList<>());
            list.add(data);
            WeightedList<ReliquaryMainPropData> weightedList = relicRandomMainPropDepot.computeIfAbsent(data.getPropDepotId(), k -> new WeightedList<>());
            weightedList.add(data.getWeight(), data);
		}
		for (ReliquaryAffixData data : GameData.getReliquaryAffixDataMap().values()) {
			if (data.getWeight() <= 0 || data.getDepotId() <= 0) {
				continue;
			}
			List<ReliquaryAffixData> list = relicAffixDepot.computeIfAbsent(data.getDepotId(), k -> new ArrayList<>());
			list.add(data);
		}
		// Let the server owner know if theyre missing weights
		if (relicMainPropDepot.size() == 0 || relicAffixDepot.size() == 0) {
			Grasscutter.getLogger().error("Relic properties are missing weights! Please check your ReliquaryMainPropExcelConfigData or ReliquaryAffixExcelConfigData files in your ExcelBinOutput folder.");
		}
	}

	public static ReliquaryMainPropData getRandomRelicMainProp(int depot) {
        WeightedList<ReliquaryMainPropData> depotList = relicRandomMainPropDepot.get(depot);
		if (depotList == null) {
			return null;
		}
		return depotList.next();
	}

    public static List<ReliquaryMainPropData> getRelicMainPropList(int depot) {
        return relicMainPropDepot.get(depot);
    }

    public static List<ReliquaryAffixData> getRelicAffixList(int depot) {
		return relicAffixDepot.get(depot);
	}

	public static Int2ObjectMap<RTree<SpawnGroupEntry, Geometry>> getSpawnLists() {
		return spawnLists;
	}

	public static RTree<SpawnGroupEntry, Geometry> getSpawnListById(int sceneId) {
		return getSpawnLists().computeIfAbsent(sceneId, id -> RTree.create());
	}

	public static Map<String, AvatarConfig> getPlayerAbilities() {
		return playerAbilities;
	}

	public static void setPlayerAbilities(Map<String, AvatarConfig> playerAbilities) {
		GameDepot.playerAbilities = playerAbilities;
	}

    public static void addSpawnData(SpawnGroupEntry entry) {
	    int SINGLE_ENTITY_SIZE = 50;

	    int sceneId = entry.getSceneId();
        var rtree
            = spawnLists.get(sceneId);
        if(rtree==null){
            rtree = RTree.create();
        }

        Rectangle rectangle = null; // biggest rectangle in per SpawnDataEntry
        var spawns = entry.getSpawns();
        for(SpawnDataEntry data:spawns){
            Position pos = data.getPos();
            Rectangle singleRect = Rectangle.create(
                new double[]{pos.getX()-SINGLE_ENTITY_SIZE,pos.getZ()-SINGLE_ENTITY_SIZE},
                new double[]{pos.getX()+SINGLE_ENTITY_SIZE,pos.getZ()+SINGLE_ENTITY_SIZE});
            if(rectangle==null){
                rectangle = singleRect;
            }else{
                rectangle.add(singleRect);// Enlarge
            }
        }

        rtree = rtree.add(entry,rectangle);
        spawnLists.put(sceneId,rtree);
    }
}
