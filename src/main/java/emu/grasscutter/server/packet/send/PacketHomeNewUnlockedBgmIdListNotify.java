package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeNewUnlockedBgmIdListNotifyOuterClass;

import java.util.Collection;
import java.util.Collections;

public class PacketHomeNewUnlockedBgmIdListNotify extends BasePacket {
    public PacketHomeNewUnlockedBgmIdListNotify(int homeBgmId) {
        this(Collections.singleton(homeBgmId));
    }

    public PacketHomeNewUnlockedBgmIdListNotify(Collection<Integer> homeBgmIds) {
        super(PacketOpcodes.HomeNewUnlockedBgmIdListNotify);

        var notify =
            HomeNewUnlockedBgmIdListNotifyOuterClass.HomeNewUnlockedBgmIdListNotify.newBuilder()
                .addAllNewUnlockedBgmIdList(homeBgmIds)
                .build();

        this.setData(notify);
    }
}
