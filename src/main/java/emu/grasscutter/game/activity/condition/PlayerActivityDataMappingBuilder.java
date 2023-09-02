package emu.grasscutter.game.activity.condition;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.activity.ActivityCondExcelConfigData;
import emu.grasscutter.game.activity.PlayerActivityData;
import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap.BasicEntry;
import java.util.Map;

/** This class is used for building mapping for PlayerActivityData */
public class PlayerActivityDataMappingBuilder {

    private final Map<Integer, PlayerActivityData> playerActivityDataMap;

    private final Int2ObjectMap<ActivityCondExcelConfigData> activityCondMap;

    /**
     * Build mapping for PlayerActivityData.
     *
     * @return mapping for activity data. Key is condId from NewActivityCondExcelConfigData.json
     *     ({@link ActivityCondExcelConfigData}) resource, value is {@link PlayerActivityData} class
     *     for related activity.
     */
    public static Int2ObjectMap<PlayerActivityData> buildPlayerActivityDataByActivityCondId(
            Map<Integer, PlayerActivityData> activities) {
        return new PlayerActivityDataMappingBuilder(activities).buildMappings();
    }

    public PlayerActivityDataMappingBuilder(Map<Integer, PlayerActivityData> playerActivityDataMap) {
        this.playerActivityDataMap = playerActivityDataMap;
        activityCondMap = GameData.getActivityCondExcelConfigDataMap();
    }

    private Int2ObjectMap<PlayerActivityData> buildMappings() {
        Int2ObjectMap<PlayerActivityData> result = new Int2ObjectRBTreeMap<>();

        activityCondMap.int2ObjectEntrySet().stream()
                .map(
                        entry ->
                                new BasicEntry<>(
                                        entry.getIntKey(), getPlayerActivityDataByCondId(entry.getIntKey())))
                .filter(entry -> entry.getValue() != null)
                .forEach(entry -> result.put(entry.getIntKey(), entry.getValue()));

        return result;
    }

    private PlayerActivityData getPlayerActivityDataByCondId(Integer key) {
        return playerActivityDataMap.get(detectActivityDataIdByCondId(key));
    }

    /**
     * Detect activity data id by cond id. Cond id comes from condId field from
     * NewActivityCondExcelConfigData.json. See {@link ActivityCondExcelConfigData} for condId.
     *
     * <p>Generally, there are 3 cases:
     *
     * <ol>
     *   <li>Activity data id >= 5003. Then cond id will be activity data id plus 3 additional digits.
     *       For example: activity data id = 5087, cond id = 5087xxx (x - any digit)
     *   <li>Activity data id = 5001. Then cond id will be activity data id plus 2 additional digits.
     *       For example: activity data id = 5001, cond id = 5001xx (x - any digit)
     *   <li>Activity data id one of [1001]. Then cond id will be activity data id plus 2 additional
     *       digits. This also applied to activity data id = 1002. For example: activity data id =
     *       1001, cond id = 1001x (x - any digit>
     * </ol>
     *
     * @param key cond id for which activity data id should be defined
     * @return activity data for given cond id. Returns -1 if activity was not found.
     */
    private Integer detectActivityDataIdByCondId(Integer key) {
        if (key / 10 == 1001 || key / 10 == 1002) {
            return 1001;
        } else if (key / 100 == 5001) {
            return key / 100;
        } else {
            return key / 1000;
        }
    }
}
