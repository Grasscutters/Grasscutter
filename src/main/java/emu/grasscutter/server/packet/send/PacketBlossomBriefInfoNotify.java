package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.BlossomBriefInfoNotifyOuterClass;
import emu.grasscutter.net.proto.BlossomBriefInfoOuterClass;

public class PacketBlossomBriefInfoNotify extends BasePacket {
    public PacketBlossomBriefInfoNotify(Iterable<BlossomBriefInfoOuterClass.BlossomBriefInfo> blossoms) {
        super(PacketOpcodes.BlossomBriefInfoNotify);
        this.setData(BlossomBriefInfoNotifyOuterClass.BlossomBriefInfoNotify.newBuilder().addAllBriefInfoList(blossoms));
    }
}
