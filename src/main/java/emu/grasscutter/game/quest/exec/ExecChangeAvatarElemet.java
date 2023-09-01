package emu.grasscutter.game.quest.exec;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.props.ElementType;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.quest.enums.QuestExec;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;
import lombok.val;

/** Changes the main avatar's element. First parameter is the elementType ID. */
@QuestValueExec(QuestExec.QUEST_EXEC_CHANGE_AVATAR_ELEMET)
public class ExecChangeAvatarElemet extends QuestExecHandler {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExecParam condition, String... paramStr) {
        val targetElement = ElementType.getTypeByValue(Integer.parseInt(paramStr[0]));
        val owner = quest.getOwner();
        val mainAvatar = owner.getAvatars().getAvatarById(owner.getMainCharacterId());

        if (mainAvatar == null) {
            Grasscutter.getLogger()
                    .error("Failed to get main avatar for use {}", quest.getOwner().getUid());
            return false;
        }

        Grasscutter.getLogger()
                .debug(
                        "Changing avatar element to {} for quest {}.",
                        targetElement.name(),
                        quest.getSubQuestId());
        return mainAvatar.changeElement(targetElement);
    }
}
