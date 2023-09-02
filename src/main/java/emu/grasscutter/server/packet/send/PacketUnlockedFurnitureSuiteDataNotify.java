package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.UnlockedFurnitureSuiteDataNotifyOuterClass;
import java.util.Set;

public class PacketUnlockedFurnitureSuiteDataNotify extends BasePacket {

    public PacketUnlockedFurnitureSuiteDataNotify(Set<Integer> unlockList) {
        super(PacketOpcodes.UnlockedFurnitureSuiteDataNotify);

        var proto =
                UnlockedFurnitureSuiteDataNotifyOuterClass.UnlockedFurnitureSuiteDataNotify.newBuilder();

        proto.addAllFurnitureSuiteIdList(unlockList);
        proto.setIsAll(true);

        this.setData(proto);
    }
}
