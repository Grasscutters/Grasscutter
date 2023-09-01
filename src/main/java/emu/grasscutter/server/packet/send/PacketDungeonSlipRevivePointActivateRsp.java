package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.DungeonSlipRevivePointActivateRspOuterClass.DungeonSlipRevivePointActivateRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass;

public class PacketDungeonSlipRevivePointActivateRsp extends BasePacket {
    public PacketDungeonSlipRevivePointActivateRsp(boolean success, int pointId) {
        super(PacketOpcodes.DungeonSlipRevivePointActivateRsp);

        this.setData(
                DungeonSlipRevivePointActivateRsp.newBuilder()
                        .setSlipRevivePointId(pointId)
                        .setRetcode(
                                success
                                        ? RetcodeOuterClass.Retcode.RET_SUCC_VALUE
                                        : RetcodeOuterClass.Retcode.RET_FAIL_VALUE));
    }
}
