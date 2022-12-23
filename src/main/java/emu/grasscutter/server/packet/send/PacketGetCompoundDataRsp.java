package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.CompoundQueueDataOuterClass.CompoundQueueData;
import emu.grasscutter.net.proto.GetCompoundDataRspOuterClass.GetCompoundDataRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;

import java.util.List;
import java.util.Set;

public class PacketGetCompoundDataRsp extends BasePacket {
    public PacketGetCompoundDataRsp(Set<Integer> unlockedCompounds, List<CompoundQueueData> compoundQueueData) {
        super(PacketOpcodes.GetCompoundDataRsp);
        var proto = GetCompoundDataRsp.newBuilder()
            .addAllUnlockCompoundList(unlockedCompounds)
            .addAllCompoundQueueDataList(compoundQueueData)
            .setRetcode(Retcode.RET_SUCC_VALUE)
            .build();
        setData(proto);
    }
}
