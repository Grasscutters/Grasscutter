package emu.grasscutter.game.home.suite.event;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.HomeAvatarSummonEventInfoOuterClass;
import emu.grasscutter.utils.Utils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HomeAvatarSummonEvent extends HomeAvatarEvent {
    public static final int TIME_LIMIT_SECS = 240;
    final int eventOverTime;

    public HomeAvatarSummonEvent(
            Player homeOwner, int eventId, int rewardId, int avatarId, int suiteId, int guid) {
        super(homeOwner, eventId, rewardId, avatarId, suiteId, guid);

        this.eventOverTime = Utils.getCurrentSeconds() + TIME_LIMIT_SECS;
    }

    public HomeAvatarSummonEventInfoOuterClass.HomeAvatarSummonEventInfo toProto() {
        return HomeAvatarSummonEventInfoOuterClass.HomeAvatarSummonEventInfo.newBuilder()
                .setAvatarId(this.getAvatarId())
                .setEventId(this.getEventId())
                .setGuid(this.getGuid())
                .setSuitId(this.getSuiteId())
                .setRandomPosition(this.getRandomPos())
                .setEventOverTime(this.eventOverTime)
                .build();
    }

    public boolean isTimeOver() {
        return Utils.getCurrentSeconds() > this.eventOverTime;
    }
}
