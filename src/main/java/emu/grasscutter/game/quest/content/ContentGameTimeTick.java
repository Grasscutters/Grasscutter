package emu.grasscutter.game.quest.content;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_GAME_TIME_TICK;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import lombok.val;

@QuestValueContent(QUEST_CONTENT_GAME_TIME_TICK)
public class ContentGameTimeTick extends BaseContent {
    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        val daysSinceStart =
                quest.getOwner().getWorld().getTotalGameTimeDays() - quest.getStartGameDay();
        val currentHour = quest.getOwner().getWorld().getGameTimeHours();

        // params[0] is days since start, str is hours of day
        val range = condition.getParamStr().split(",");
        val from = Integer.parseInt(range[0]);
        val to = Integer.parseInt(range[1]);

        val daysToPass = condition.getParam()[0];
        // if to is at the beginning of the day, we need to pass it one more time
        val daysMod = to < from && daysToPass > 0 && currentHour < to ? 1 : 0;

        val isTimeMet =
                from < to
                        ? currentHour >= from && currentHour < to
                        : currentHour < to || currentHour >= from;

        val isDaysSinceMet = daysSinceStart >= daysToPass + daysMod;

        return isTimeMet && isDaysSinceMet;
    }
}
