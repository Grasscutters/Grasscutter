package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.FireWorkNotifyOuterClass;
import emu.grasscutter.net.proto.FireWorkDataOuterClass;

public class PacketFireworkNotify extends BasePacket {

    public PacketFireworkNotify(FireWorkDataOuterClass.FireWorkData pinfo) {
        super(PacketOpcodes.FireworkNotify);

        var proto
                = FireWorkNotifyOuterClass.FireWorkNotify.newBuilder();

        proto.addFireWorkData(pinfo);

        setData(proto.build());
    }

}
