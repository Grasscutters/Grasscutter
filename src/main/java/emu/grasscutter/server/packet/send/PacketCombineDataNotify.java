package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.CombineDataNotifyOuterClass.CombineDataNotify;

public class PacketCombineDataNotify extends BasePacket {

    public PacketCombineDataNotify(Iterable<Integer> unlockedCombines) {
        super(PacketOpcodes.CombineDataNotify);

        CombineDataNotify proto = CombineDataNotify.newBuilder()
            .addAllCombineIdList(unlockedCombines)
            .build();

        this.setData(proto);
    }
}
