package emu.grasscutter.game.activity.condition;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.excels.activity.ActivityCondExcelConfigData;
import emu.grasscutter.game.activity.*;
import emu.grasscutter.game.activity.condition.all.UnknownActivityConditionHandler;
import emu.grasscutter.game.quest.enums.LogicType;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

public class BasicActivityConditionExecutor implements ActivityConditionExecutor {

    private final Map<Integer, ActivityConfigItem> activityConfigItemMap;
    private final Int2ObjectMap<ActivityCondExcelConfigData> activityConditions;

    private final Int2ObjectMap<PlayerActivityData> playerActivityDataByActivityCondId;
    private final Map<ActivityConditions, ActivityConditionBaseHandler> activityConditionsHandlers;

    public BasicActivityConditionExecutor(
            Map<Integer, ActivityConfigItem> activityConfigItemMap,
            Int2ObjectMap<ActivityCondExcelConfigData> activityConditions,
            Int2ObjectMap<PlayerActivityData> playerActivityDataByActivityCondId,
            Map<ActivityConditions, ActivityConditionBaseHandler> activityConditionsHandlers) {
        this.activityConfigItemMap = activityConfigItemMap;
        this.activityConditions = activityConditions;
        this.playerActivityDataByActivityCondId = playerActivityDataByActivityCondId;
        this.activityConditionsHandlers = activityConditionsHandlers;
    }

    @Override
    public List<Integer> getMeetActivitiesConditions(List<Integer> condIds) {
        return condIds.stream().filter(this::meetsCondition).collect(Collectors.toList());
    }

    @Override
    public boolean meetsCondition(int activityCondId) {
        ActivityCondExcelConfigData condData = activityConditions.get(activityCondId);

        if (condData == null) {
            Grasscutter.getLogger()
                    .error("Could not find condition for activity with id = {}", activityCondId);
            return false;
        }

        LogicType condComb = condData.getCondComb();
        if (condComb == null) {
            condComb = LogicType.LOGIC_AND;
        }

        PlayerActivityData activity = playerActivityDataByActivityCondId.get(activityCondId);
        if (activity == null) {
            return false;
        }
        ActivityConfigItem activityConfig = activityConfigItemMap.get(activity.getActivityId());
        List<BooleanSupplier> predicates =
                condData.getCond().stream()
                        .map(
                                c ->
                                        (BooleanSupplier)
                                                () ->
                                                        activityConditionsHandlers
                                                                .getOrDefault(
                                                                        c.getType(), new UnknownActivityConditionHandler(c.getType()))
                                                                .execute(activity, activityConfig, c.paramArray()))
                        .collect(Collectors.toList());

        return LogicType.calculate(condComb, predicates);
    }
}
