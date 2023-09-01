package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.DungeonPlayerDieRspOuterClass.DungeonPlayerDieRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;

public class PacketDungeonPlayerDieRsp extends BasePacket {

    public PacketDungeonPlayerDieRsp(Retcode retcode) {
        super(PacketOpcodes.DungeonPlayerDieRsp);

        DungeonPlayerDieRsp proto =
                DungeonPlayerDieRsp.newBuilder().setRetcode(retcode.getNumber()).build();

        this.setData(proto);
    }
}
