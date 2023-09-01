package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;

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
