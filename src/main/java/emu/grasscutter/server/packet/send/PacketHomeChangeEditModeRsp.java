package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeChangeEditModeRspOuterClass;

public class PacketHomeChangeEditModeRsp extends BasePacket {

    public PacketHomeChangeEditModeRsp(boolean enterEditMode) {
        super(PacketOpcodes.HomeChangeEditModeRsp);

        var proto = HomeChangeEditModeRspOuterClass.HomeChangeEditModeRsp.newBuilder();

        proto.setIsEnterEditMode(enterEditMode);

        this.setData(proto);
    }
}
