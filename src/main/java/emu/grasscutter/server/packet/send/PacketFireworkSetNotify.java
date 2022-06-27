package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.FireworkSetNotifyOuterClass;
import emu.grasscutter.net.proto.JMPCGMBHJLGOuterClass;

public class PacketFireworkSetNotify extends BasePacket {

    public PacketFireworkSetNotify(JMPCGMBHJLGOuterClass.JMPCGMBHJLG notify) {
        super(PacketOpcodes.FireworkSetNotify);

        var proto
                = FireworkSetNotifyOuterClass.FireworkSetNotify.newBuilder();

        proto.setEFAEGBLFBBP(1).addGBNIPCKFHMO(notify);

        setData(proto.build());
    }

}
