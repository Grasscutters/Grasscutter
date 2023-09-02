package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.UnlockedFurnitureFormulaDataNotifyOuterClass;
import java.util.Set;

public class PacketUnlockedFurnitureFormulaDataNotify extends BasePacket {

    public PacketUnlockedFurnitureFormulaDataNotify(Set<Integer> unlockList) {
        super(PacketOpcodes.UnlockedFurnitureFormulaDataNotify);

        var proto =
                UnlockedFurnitureFormulaDataNotifyOuterClass.UnlockedFurnitureFormulaDataNotify
                        .newBuilder();

        proto.addAllFurnitureIdList(unlockList);
        proto.setIsAll(true);

        this.setData(proto);
    }
}
