package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ActivityTakeWatcherRewardRspOuterClass;

public class PacketActivityTakeWatcherRewardRsp extends BasePacket {

	public PacketActivityTakeWatcherRewardRsp(int activityId, int watcherId) {
		super(PacketOpcodes.ActivityTakeWatcherRewardRsp);

        var proto = ActivityTakeWatcherRewardRspOuterClass.ActivityTakeWatcherRewardRsp.newBuilder();

        proto.setActivityId(activityId)
            .setWatcherId(watcherId);

        this.setData(proto);
	}

}
