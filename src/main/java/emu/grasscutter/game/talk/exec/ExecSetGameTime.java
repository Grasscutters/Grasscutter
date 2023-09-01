package emu.grasscutter.game.talk.exec;

import emu.grasscutter.data.excels.TalkConfigData;
import emu.grasscutter.data.excels.TalkConfigData.TalkExecParam;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.talk.*;

@TalkValueExec(TalkExec.TALK_EXEC_SET_GAME_TIME)
public final class ExecSetGameTime extends TalkExecHandler {
    @Override
    public void execute(Player player, TalkConfigData talkData, TalkExecParam execParam) {
        if (execParam.getParam().length < 1) return;

        player.getWorld().changeTime(Integer.parseInt(execParam.getParam()[0]), 0);
    }
}
