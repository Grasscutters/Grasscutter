package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.MassiveEntityElementOpBatchNotifyOuterClass;

public class PacketMassiveEntityElementOpBatchNotify extends BasePacket {
    public PacketMassiveEntityElementOpBatchNotify(MassiveEntityElementOpBatchNotifyOuterClass.MassiveEntityElementOpBatchNotify notify) {
        super(PacketOpcodes.MassiveEntityElementOpBatchNotify);

        this.setData(MassiveEntityElementOpBatchNotifyOuterClass.MassiveEntityElementOpBatchNotify.newBuilder(notify));
    }
}
