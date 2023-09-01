package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.HomeNewUnlockedBgmIdListNotifyOuterClass;

public class PacketHomeNewUnlockedBgmIdListNotify extends BasePacket {
    public PacketHomeNewUnlockedBgmIdListNotify(int homeBgmId) {
        super(PacketOpcodes.HomeNewUnlockedBgmIdListNotify);

        var notify =
                HomeNewUnlockedBgmIdListNotifyOuterClass.HomeNewUnlockedBgmIdListNotify.newBuilder()
                        .addNewUnlockedBgmIdList(homeBgmId)
                        .build();

        this.setData(notify);
    }
}
