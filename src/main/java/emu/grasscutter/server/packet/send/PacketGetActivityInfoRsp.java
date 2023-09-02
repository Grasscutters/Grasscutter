package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.activity.ActivityManager;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.GetActivityInfoRspOuterClass.GetActivityInfoRsp;
import java.util.Set;

public class PacketGetActivityInfoRsp extends BasePacket {
    public PacketGetActivityInfoRsp(Set<Integer> activityIdList, ActivityManager activityManager) {
        super(PacketOpcodes.GetActivityInfoRsp);

        var proto = GetActivityInfoRsp.newBuilder();

        activityIdList.stream()
                .map(activityManager::getInfoProtoByActivityId)
                .forEach(proto::addActivityInfoList);

        this.setData(proto);
    }
}
