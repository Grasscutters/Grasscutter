package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.CompoundDataNotifyOuterClass.CompoundDataNotify;
import emu.grasscutter.net.proto.CompoundQueueDataOuterClass.CompoundQueueData;
import java.util.*;

public class PacketCompoundDataNotify extends BasePacket {

    public PacketCompoundDataNotify(
            Set<Integer> unlockedCompounds, List<CompoundQueueData> compoundQueueData) {
        super(PacketOpcodes.CompoundDataNotify);
        var proto =
                CompoundDataNotify.newBuilder()
                        .addAllUnlockCompoundList(unlockedCompounds)
                        .addAllCompoundQueueDataList(compoundQueueData)
                        .build();
        this.setData(proto);
    }
}
