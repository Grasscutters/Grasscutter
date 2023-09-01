package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;

public class PacketActivityInfoNotify extends BasePacket {

    public PacketActivityInfoNotify(ActivityInfoOuterClass.ActivityInfo activityInfo) {
        super(PacketOpcodes.ActivityInfoNotify);

        var proto = ActivityInfoNotifyOuterClass.ActivityInfoNotify.newBuilder();

        proto.setActivityInfo(activityInfo);

        this.setData(proto);
    }
}
