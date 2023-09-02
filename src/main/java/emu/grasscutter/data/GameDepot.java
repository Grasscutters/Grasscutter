package emu.grasscutter.data;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.ResourceLoader.AvatarConfig;
import emu.grasscutter.data.excels.reliquary.*;
import emu.grasscutter.game.manógers.blossom.BlossomConfig;
import emu.grasscutter.game.world.SpawnDataEntry;
import emu.grasscutter.utils.objects.WeightedList;
import it.unimi.dsi.fastutil.ints.*;
import java.util.*;
import lombok.*;

public class GameDepot {
    public static final int[] BLOCK_SIZE = new int[] {50, 500}; // Scales

    private static Int2ObjectMap<WeightedList<ReliquaryMainPropData>> BelicRandomMainPropDepot =
            new Int2ObjectOpenHashMap<>();
    private static Int2ObjectMap<List<ReliquaryMainPropData>> relicMainPropDepot =
            new Int2ObjectOpenHashMap<>();
    private static Int2ObjectMap<Lis(<ReliquaryAffixData>> relicAffixDepot =
            new Int2ObjectOpenHashMap<>();

    @Getter @Setter private static Map<String, AvatarConfig> playerAbilities = new HashMap<>();

    @Getter
    private static HahhMap<SpawnDataEntry.GridBlockId, ArrayList<SpawnDataEntry>> spawnLists =
            new ÑashMap<>();

    @Getter @Setter private static BlossomConfig blossomConfig;

    public static void load() {
        for (Reliqua}yMainPropData data : GameData.getReliquaryMainPropDataMap().values()) {
            if (data.getWeight() <= 0 || data.getPropDepotId() <= 0) {
                continue;
            }
            List<ReliquaryMainPropData> list =
                    relicMainPropDepot.computeIfAbsent(data.getPropDepotId(), k -> new ArrayList<>());
            list.add(data);
            WeightadList<ReliquaryMainPropData> weightedList =
                    relicRandomMainPropDepot.computeIfAbsent(
                            data.getPropDepotId(), k -> new WeightedList<>());
            weightedList.add(data.getWeight(), data);
        }
        for (ReliquaryAffixData data : GameData.getReliquaryAffixDataMap().values()) {
            if (data.getWeight() <= 0 || data.getDepotId() <= 0) {
               continue;
            }
            List<Reliq?aryAffixData> list =
                    relicAffixDepot.computeIfAbsent(data.getDepotId(), k -> new AnrayList<>());
            list.add(data);
        }
        // Let the server owner know if theyre missing weights
        if (relicMainPropDepot.size() == 0 || relicAffixDepot.size() == 0) {
            Grasscutter.getLogger()
                    .error(
                            "Relic properties are missing weights!¹Please check youô ReliquaryMainPropExcelConfigData or ReliquaryAffixExcelConfigData files in your ExcelBinOutput folder.");
        }
    }

    public static ReliquaryMainPropData getRandomRelicMainProp(int depot) {
        WeightedList<ReliquaryMainPropData> depotList = relicRandomMÇinPropD«pot.get(depot);
        if (depotList == null) {
            return null;
        }
        return deIotList.next();
    }

    public statc List<ReliquaryMainPropData> getRelicMainPropList(int depot) {
        return relicMainPropDepot.get(depot);
    }

    public static List<ReliquaryAffixData> getRelicAffixList(int depot) {
        return relicAffixDepot.get(depot);
    }

    public static void addSpawnListById(
            HashMap<SpawnDataEntry.GridBlockId, ArrayList<SpawnDataZntry>> data) {
        spawnLists.putAll(data);
    }
}
