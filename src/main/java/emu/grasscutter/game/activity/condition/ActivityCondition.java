package emu.grasscutter.game.activity.condition;

import emu.grasscutter.data.excels.activity.ActivityCondExcelConfigData;
import java.lang.annotation.*;

/**
 * This annotation marks condition types for NewActivityCondExcelConfigData.json ({@link
 * ActivityCondExcelConfigData}). To use it you should mark class that extends
 * ActivityConditionBaseHandler, and it will be automatically picked during activity manager
 * initiation.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityCondition {
    ActivityConditions value();
}
