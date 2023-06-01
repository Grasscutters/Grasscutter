package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DungeonWayPointActivateRspOuterClass;
import emu.grasscutter.net.proto.RetcodeOuterClass;

public class PacketDungeonWayPointActivateRsp extends BasePacket {
    public PacketDungeonWayPointActivateRsp(boolean success, int pointId) {
        super(PacketOpcodes.DungeonWayPointActivateRsp);

        this.setData(
                DungeonWayPointActivateRspOuterClass.DungeonWayPointActivateRsp.newBuilder()
                        .setWayPointId(pointId)
                        .setRetcode(
                                success
                                        ? RetcodeOuterClass.Retcode.RET_SUCC_VALUE
                                        : RetcodeOuterClass.Retcode.RET_FAIL_VALUE));
    }
}
