package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetAllMailResultNotifyOuterClass;
import emu.grasscutter.net.proto.MailDataOuterClass;

import java.util.List;

public class PacketGetAllMailResultNotify extends BasePacket {
    public PacketGetAllMailResultNotify(int packetBeSentNum, int packetNum, List<MailDataOuterClass.MailData> mailData, String transaction, boolean isGift) {
        super(PacketOpcodes.GetAllMailResultNotify);

        this.setData(GetAllMailResultNotifyOuterClass.GetAllMailResultNotify.newBuilder()
            .setPacketBeSentNum(packetBeSentNum)
            .addAllMailList(mailData)
            .setTransaction(transaction)
            .setIsCollected(isGift)
            .setPacketNum(packetNum)
            .build());
    }
}
