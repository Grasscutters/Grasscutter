package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SetBattlePassViewedRspOuterClass.SetBattlePassViewedRsp;

public class PacketSetBattlePassViewedRsp extends BasePacket {

    public PacketSetBattlePassViewedRsp(int scheduleId) {
        super(PacketOpcodes.SetBattlePassViewedRsp);

        SetBattlePassViewedRsp proto =
                SetBattlePassViewedRsp.newBuilder().setScheduleId(scheduleId).build();

        this.setData(proto);
    }
}
