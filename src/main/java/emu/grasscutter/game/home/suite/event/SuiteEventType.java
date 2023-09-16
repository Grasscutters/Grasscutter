package emu.grasscutter.game.home.suite.event;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.HomeWorldEventData;
import javax.annotation.Nullable;

public enum SuiteEventType {
    HOME_AVATAR_REWARD_EVENT,
    HOME_AVATAR_SUMMON_EVENT;

    @Nullable public HomeWorldEventData getEventDataFrom(int avatarId, int suiteId) {
        return GameData.getHomeWorldEventDataMap().values().stream()
                .filter(
                        data ->
                                data.getEventType() == this
                                        && data.getAvatarID() == avatarId
                                        && data.getSuiteId() == suiteId)
                .findFirst()
                .orElse(null);
    }
}
