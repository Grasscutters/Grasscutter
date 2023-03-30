// removed due to proto not existing

/*
package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.FireworksReformDataNotifyOuterClass;
import emu.grasscutter.net.proto.FireworksReformDataOuterClass;

public class PacketFireworksReformDataNotify extends BasePacket {

    public PacketFireworksReformDataNotify(FireworksReformDataOuterClass.FireworksReformData fireworksReformData) {
        super(PacketOpcodes.FireworksReformDataNotify);

        var proto
            = FireworksReformDataNotifyOuterClass.FireworksReformDataNotify.newBuilder();

        proto.addFireworksReformDataList(fireworksReformData);

        setData(proto.build());
    }

}
*/