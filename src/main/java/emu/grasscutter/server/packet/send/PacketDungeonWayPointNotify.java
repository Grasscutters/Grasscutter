package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.DungeonWayPointNotifyOuterClass;
import java.util.Set;

public class PacketDungeonWayPointNotify extends BasePacket {
    public PacketDungeonWayPointNotify(boolean added, Set<Integer> activePointIds) {
        super(PacketOpcodes.DungeonWayPointNotify);

        this.setData(
                DungeonWayPointNotifyOuterClass.DungeonWayPointNotify.newBuilder()
                        .addAllActiveWayPointList(activePointIds)
                        .setIsAdd(added));
    }
}
