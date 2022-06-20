package emu.grasscutter.data;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.ResourceLoader.AvatarConfig;
import emu.grasscutter.data.excels.ReliquaryAffixData;
import emu.grasscutter.data.excels.ReliquaryMainPropData;
import emu.grasscutter.game.world.SpawnDataEntry.SpawnGroupEntry;
import emu.grasscutter.utils.WeightedList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.danilopianini.util.FlexibleQuadTree;
import org.danilopianini.util.SpatialIndex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameDepot {
    private static final Int2ObjectMap<WeightedList<ReliquaryMainPropData>> relicMainPropDepot = new Int2ObjectOpenHashMap<>();
    private static final Int2ObjectMap<List<ReliquaryAffixData>> relicAffixDepot = new Int2ObjectOpenHashMap<>();

    private static Map<String, AvatarConfig> playerAbilities = new HashMap<>();
    private static final Int2ObjectMap<SpatialIndex<SpawnGroupEntry>> spawnLists = new Int2ObjectOpenHashMap<>();

    public static void load() {
        for (ReliquaryMainPropData data : GameData.getReliquaryMainPropDataMap().values()) {
            if (data.getWeight() <= 0 || data.getPropDepotId() <= 0) {
                continue;
            }
            WeightedList<ReliquaryMainPropData> list = relicMainPropDepot.computeIfAbsent(data.getPropDepotId(), k -> new WeightedList<>());
            list.add(data.getWeight(), data);
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
        WeightedList<ReliquaryMainPropData> depotList = relicMainPropDepot.get(depot);
        if (depotList == null) {
            return null;
        }
        return depotList.next();
    }

    public static List<ReliquaryAffixData> getRandomRelicAffixList(int depot) {
        return relicAffixDepot.get(depot);
    }

    public static Int2ObjectMap<SpatialIndex<SpawnGroupEntry>> getSpawnLists() {
        return spawnLists;
    }

    public static SpatialIndex<SpawnGroupEntry> getSpawnListById(int sceneId) {
        return getSpawnLists().computeIfAbsent(sceneId, id -> new FlexibleQuadTree<>());
    }

    public static Map<String, AvatarConfig> getPlayerAbilities() {
        return playerAbilities;
    }

    public static void setPlayerAbilities(Map<String, AvatarConfig> playerAbilities) {
        GameDepot.playerAbilities = playerAbilities;
    }
}
