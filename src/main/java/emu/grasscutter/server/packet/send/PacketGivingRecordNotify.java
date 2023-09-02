package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.GivingRecordNotifyOuterClass.GivingRecordNotify;
import emu.grasscutter.net.proto.GivingRecordOuterClass.GivingRecord;
import java.util.Collection;

public final class PacketGivingRecordNotify extends BasePacket {
    public PacketGivingRecordNotify(Collection<GivingRecord> records) {
        super(PacketOpcodes.GivingRecordNotify);

        this.setData(GivingRecordNotify.newBuilder().addAllGivingRecordList(records).build());
    }
}
