package emu.grasscutter.game.talk;

import emu.grasscutter.data.excels.TalkConfigData;
import emu.grasscutter.data.excels.TalkConfigData.TalkExecParam;
import emu.grasscutter.game.player.Player;

public abstract class TalkExecHandler {
    /**
     * @param player The player who is talking.
     * @param talkData The data associated with the talk.
     * @param execParam The execution parameter for the talk.
     */
    public abstract void execute(Player player, TalkConfigData talkData, TalkExecParam execParam);
}
