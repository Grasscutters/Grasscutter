package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ShowCommonTipsNotifyOuterClass.ShowCommonTipsNotify;

public class PacketShowCommonTipsNotify extends BasePacket {

    public PacketShowCommonTipsNotify(String title, String content, int closeTime) {
        super(PacketOpcodes.ShowCommonTipsNotify);
        this.setData(
                ShowCommonTipsNotify.newBuilder()
                        .setTitle(title)
                        .setContent(content)
                        .setCloseTime(closeTime)
                        .build());
    }
}
