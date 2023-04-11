package emu.grasscutter.game.activity.condition;

import java.util.List;

public interface ActivityConditionExecutor {

    List<Integer> getMeetActivitiesConditions(List<Integer> condIds);

    boolean meetsCondition(int activityCondId);
}
