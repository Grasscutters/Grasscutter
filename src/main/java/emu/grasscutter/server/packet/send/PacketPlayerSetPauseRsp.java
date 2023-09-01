package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PlayerSetPauseRspOuterClass.PlayerSetPauseRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;

public class PacketPlayerSetPauseRsp extends BasePacket {

    public PacketPlayerSetPauseRsp(Retcode retcode) {
        super(PacketOpcodes.PlayerSetPauseRsp);

        this.setData(PlayerSetPauseRsp.newBuilder().setRetcode(retcode.getNumber()));
    }
}
