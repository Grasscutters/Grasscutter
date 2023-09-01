package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.activity.PlayerActivityData;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ActivityUpdateWatcherNotifyOuterClass;

public class PacketActivityUpdateWatcherNotify extends BasePacket {

    public PacketActivityUpdateWatcherNotify(
            int activityId, PlayerActivityData.WatcherInfo watcherInfo) {
        super(PacketOpcodes.ActivityUpdateWatcherNotify);

        var proto = ActivityUpdateWatcherNotifyOuterClass.ActivityUpdateWatcherNotify.newBuilder();

        proto.setActivityId(activityId).setWatcherInfo(watcherInfo.toProto());

        this.setData(proto);
    }
}
