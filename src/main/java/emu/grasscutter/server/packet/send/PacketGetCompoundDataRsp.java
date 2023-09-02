package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.CompoundQueueDataOuterClass.CompoundQueueData;
import emu.grasscutter.net.proto.GetCompoundDataRspOuterClass.GetCompoundDataRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import java.util.*;

public class PacketGetCompoundDataRsp extends BasePacket {
    public PacketGetCompoundDataRsp(
            Set<Integer> unlockedCompounds, List<CompoundQueueData> compoundQueueData) {
        super(PacketOpcodes.GetCompoundDataRsp);
        var proto =
                GetCompoundDataRsp.newBuilder()
                        .addAllUnlockCompoundList(unlockedCompounds)
                        .addAllCompoundQueueDataList(compoundQueueData)
                        .setRetcode(Retcode.RET_SUCC_VALUE)
                        .build();
        setData(proto);
    }
}
