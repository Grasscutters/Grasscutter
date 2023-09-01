package emu.grasscutter.game.quest.exec;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.quest.enums.QuestExec;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;

@QuestValueExec(QuestExec.QUEST_EXEC_REMOVE_TRIAL_AVATAR)
public class ExecRemoveTrialAvatar extends QuestExecHandler {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExecParam condition, String... paramStr) {
        try {
            quest.getOwner().getTeamManager().removeTrialAvatar(Integer.parseInt(paramStr[0]));
            Grasscutter.getLogger()
                    .debug("Removed trial avatar from team for quest {}", quest.getSubQuestId());
            return true;
        } catch (IllegalStateException ignored) {
            // The player does not have any trial avatars equipped.
            Grasscutter.getLogger()
                    .warn("Attempted to remove trial avatars from player with none equipped.");
            return true;
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
