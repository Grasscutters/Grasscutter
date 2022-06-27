package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.FireWorkNotifyOuterClass;
import emu.grasscutter.net.proto.JDPMOMKAPIFOuterClass;

public class PacketFireworkNotify extends BasePacket {

    public PacketFireworkNotify(JDPMOMKAPIFOuterClass.JDPMOMKAPIF pinfo) {
        super(PacketOpcodes.FireworkNotify);

        var proto
                = FireWorkNotifyOuterClass.FireWorkNotify.newBuilder();

        proto.addIOJKJNDAGDO(pinfo);

        setData(proto.build());
    }

}
