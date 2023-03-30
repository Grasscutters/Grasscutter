// removed due to proto not existing

/*
package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.FireworksLaunchDataNotifyOuterClass;
import emu.grasscutter.net.proto.FireworksLaunchSchemeDataOuterClass;

public class PacketFireworksLaunchDataNotify extends BasePacket {

    public PacketFireworksLaunchDataNotify(FireworksLaunchSchemeDataOuterClass.FireworksLaunchSchemeData notify) {
        super(PacketOpcodes.FireworksLaunchDataNotify);

        var proto
                = FireworksLaunchDataNotifyOuterClass.FireworksLaunchDataNotify.newBuilder();

        proto.setLastUseSchemeId(1).addSchemeDataList(notify);

        setData(proto.build());
    }

}
*/