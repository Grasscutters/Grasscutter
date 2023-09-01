package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;

public class PacketBlossomBriefInfoNotify extends BasePacket {
    public PacketBlossomBriefInfoNotify(
            Iterable<BlossomBriefInfoOuterClass.BlossomBriefInfo> blossoms) {
        super(PacketOpcodes.BlossomBriefInfoNotify);
        this.setData(
                BlossomBriefInfoNotifyOuterClass.BlossomBriefInfoNotify.newBuilder()
                        .addAllBriefInfoList(blossoms));
    }
}
