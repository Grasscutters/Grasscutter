package emu.grasscutter.game.quest.content;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_UNLOCK_TRANS_POINT;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;

@QuestValueContent(QUEST_CONTENT_UNLOCK_TRANS_POINT)
public class ContentUnlockTransPoint extends BaseContent {
    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        var sceneId = condition.getParam()[0];
        var scenePointId = condition.getParam()[1];
        var scenePoints = quest.getOwner().getUnlockedScenePoints().get(sceneId);
        return scenePoints != null && scenePoints.contains(scenePointId);
    }
}
