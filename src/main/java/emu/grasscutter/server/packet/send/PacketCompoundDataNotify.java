package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.CompoundDataNotifyOuterClass.CompoundDataNotify;
import emu.grasscutter.net.proto.CompoundQueueDataOuterClass.CompoundQueueData;

import java.util.List;
import java.util.Set;

public class PacketCompoundDataNotify extends BasePacket {

    public PacketCompoundDataNotify(Set<Integer> unlockedCompounds, List<CompoundQueueData> compoundQueueData) {
        super(PacketOpcodes.CompoundDataNotify);
        var proto= CompoundDataNotify.newBuilder()
            .addAllUnlockCompoundList(unlockedCompounds)
            .addAllCompoundQueueDataList(compoundQueueData)
            .build();
        this.setData(proto);
    }
}
