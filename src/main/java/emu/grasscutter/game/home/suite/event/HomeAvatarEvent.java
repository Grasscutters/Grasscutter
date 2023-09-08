package emu.grasscutter.game.home.suite.event;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.Utils;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class HomeAvatarEvent {
    final Player homeOwner;
    final int eventId;
    final int rewardId;
    final int avatarId;
    final int suiteId;
    final int guid;
    final int randomPos;

    public HomeAvatarEvent(
            Player homeOwner, int eventId, int rewardId, int avatarId, int suiteId, int guid) {
        this.homeOwner = homeOwner;
        this.eventId = eventId;
        this.rewardId = rewardId;
        this.avatarId = avatarId;
        this.suiteId = suiteId;
        this.guid = guid;
        this.randomPos = this.generateRandomPos();
    }

    public int generateRandomPos() {
        return Utils.randomRange(1, 97);
    }

    public List<GameItem> giveRewards() {
        return List.of();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HomeAvatarEvent that = (HomeAvatarEvent) o;
        return eventId == that.eventId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }
}
