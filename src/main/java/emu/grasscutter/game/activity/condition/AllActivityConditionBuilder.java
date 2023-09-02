package emu.grasscutter.game.activity.condition;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.excels.activity.ActivityCondExcelConfigData;
import java.util.*;
import java.util.stream.Collectors;
import org.reflections.Reflections;

/**
 * Class that used for scanning classpath, picking up all activity conditions (for
 * NewActivityCondExcelConfigData.json {@link ActivityCondExcelConfigData}) and saving them to map.
 * Check for more info {@link ActivityCondition}
 */
public class AllActivityConditionBuilder {

    /**
     * Build activity conditions handlers
     *
     * @return map containing all condition handlers for NewActivityCondExcelConfigData.json
     */
    public static Map<ActivityConditions, ActivityConditionBaseHandler> buildActivityConditions() {
        return new AllActivityConditionBuilder().initActivityConditions();
    }

    private Map<ActivityConditions, ActivityConditionBaseHandler> initActivityConditions() {
        Reflections reflector = Grasscutter.reflector;
        return reflector.getTypesAnnotatedWith(ActivityCondition.class).stream()
                .map(this::newInstance)
                .map(h -> new AbstractMap.SimpleEntry<>(extractActionType(h), h))
                .collect(
                        Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }

    private ActivityConditions extractActionType(ActivityConditionBaseHandler e) {
        ActivityCondition condition = e.getClass().getAnnotation(ActivityCondition.class);
        if (condition == null) {
            Grasscutter.getLogger()
                    .error("Failed to read command type for class {}", e.getClass().getName());
            return null;
        }

        return condition.value();
    }

    private ActivityConditionBaseHandler newInstance(Class<?> clazz) {
        try {
            Object result = clazz.getDeclaredConstructor().newInstance();
            if (result instanceof ActivityConditionBaseHandler) {
                return (ActivityConditionBaseHandler) result;
            }
            Grasscutter.getLogger()
                    .error(
                            "Failed to initiate activity condition: {}, object have wrong type", clazz.getName());
        } catch (Exception e) {
            String message =
                    String.format(
                            "Failed to initiate activity condition: %s, %s", clazz.getName(), e.getMessage());
            Grasscutter.getLogger().error(message, e);
        }
        return null;
    }
}
