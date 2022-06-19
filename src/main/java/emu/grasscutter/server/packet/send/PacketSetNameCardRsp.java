package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SetNameCardRspOuterClass.SetNameCardRsp;

public class PacketSetNameCardRsp extends BasePacket {

    public PacketSetNameCardRsp(int nameCardId) {
        super(PacketOpcodes.SetNameCardRsp);

        SetNameCardRsp proto = SetNameCardRsp.newBuilder()
            .setNameCardId(nameCardId)
            .build();

        this.setData(proto);
    }
}
