package emu.grasscutter.game.home.suite.event;

import emu.grasscutter.data.GameData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.proto.HomeAvatarRewardEventInfoOuterClass;
import java.util.List;

public class HomeAvatarRewardEvent extends HomeAvatarEvent {
    public HomeAvatarRewardEvent(
            Player homeOwner, int eventId, int rewardId, int avatarId, int suiteId, int guid) {
        super(homeOwner, eventId, rewardId, avatarId, suiteId, guid);
    }

    public HomeAvatarRewardEventInfoOuterClass.HomeAvatarRewardEventInfo toProto() {
        return HomeAvatarRewardEventInfoOuterClass.HomeAvatarRewardEventInfo.newBuilder()
                .setAvatarId(this.getAvatarId())
                .setEventId(this.getEventId())
                .setGuid(this.getGuid())
                .setSuiteId(this.getSuiteId())
                .setRandomPosition(this.getRandomPos())
                .build();
    }

    @Override
    public List<GameItem> giveRewards() {
        var data = GameData.getRewardDataMap().get(this.getRewardId());
        if (data == null) {
            return List.of();
        }

        var rewards = data.getRewardItemList().stream().map(GameItem::new).toList();
        this.getHomeOwner().getInventory().addItems(rewards, ActionReason.HomeAvatarEventReward);
        return rewards;
    }
}
