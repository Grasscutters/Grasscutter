package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SetOpenStateRspOuterClass.SetOpenStateRsp;

public class PacketSetOpenStateRsp extends BasePacket {

    public PacketSetOpenStateRsp(int openState, int value) {
        super(PacketOpcodes.SetOpenStateRsp);

        SetOpenStateRsp proto = SetOpenStateRsp.newBuilder()
            .setKey(openState).setValue(value).build();

        this.setData(proto);
    }

}
