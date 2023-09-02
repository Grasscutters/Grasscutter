package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.HomeChangeEditModeRspOuterClass;

public class PacketHomeChangeEditModeRsp extends BasePacket {

    public PacketHomeChangeEditModeRsp(boolean enterEditMode) {
        super(PacketOpcodes.HomeChangeEditModeRsp);

        var proto = HomeChangeEditModeRspOuterClass.HomeChangeEditModeRsp.newBuilder();

        proto.setIsEnterEditMode(enterEditMode);

        this.setData(proto);
    }

    public PacketHomeChangeEditModeRsp(int retcode) {
        super(PacketOpcodes.HomeChangeEditModeRsp);

        this.setData(
                HomeChangeEditModeRspOuterClass.HomeChangeEditModeRsp.newBuilder().setRetcode(retcode));
    }
}
