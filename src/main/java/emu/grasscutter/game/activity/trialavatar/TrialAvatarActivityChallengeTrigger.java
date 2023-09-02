package emu.grasscutter.game.activity.trialavatar;

import emu.grasscutter.game.activity.*;
import emu.grasscutter.game.props.WatcherTriggerType;
import java.util.stream.Stream;
import lombok.val;

@ActivityWatcherType(WatcherTriggerType.TRIGGER_FINISH_CHALLENGE)
public class TrialAvatarActivityChallengeTrigger extends ActivityWatcher {
    @Override
    protected boolean isMeet(String... param) {
        if (param.length < 3) return false;

        val handler = (TrialAvatarActivityHandler) getActivityHandler();
        if (handler == null) return false;

        val paramList = handler.getTriggerParamList();
        if (paramList.isEmpty()) return false;

        val paramCond = Stream.of(paramList.get(0).split(",")).toList();
        return Stream.of(param).allMatch(x -> paramCond.contains(x));
    }

    @Override
    public void trigger(PlayerActivityData playerActivityData, String... param) {
        if (!isMeet(param)) return;

        val handler = (TrialAvatarActivityHandler) getActivityHandler();
        if (handler == null) return;

        handler.setPassDungeon(playerActivityData);
    }
}
