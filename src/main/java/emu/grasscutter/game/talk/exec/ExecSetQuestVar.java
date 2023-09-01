package emu.grasscutter.game.talk.exec;

import emu.grasscutter.data.excels.TalkConfigData;
import emu.grasscutter.data.excels.TalkConfigData.TalkExecParam;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.talk.*;

@TalkValueExec(TalkExec.TALK_EXEC_SET_QUEST_VAR)
public final class ExecSetQuestVar extends TalkExecHandler {
    @Override
    public void execute(Player player, TalkConfigData talkData, TalkExecParam execParam) {
        if (execParam.getParam().length < 3) return;

        var mainQuest =
                player.getQuestManager().getMainQuestById(Integer.parseInt(execParam.getParam()[2]));
        if (mainQuest == null) return;

        mainQuest.setQuestVar(
                Integer.parseInt(execParam.getParam()[0]), Integer.parseInt(execParam.getParam()[1]));
    }
}
