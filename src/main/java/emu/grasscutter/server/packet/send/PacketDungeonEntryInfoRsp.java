package emu.grasscutter.server.packet.send;

import emu.grasscutter.data.common.PointData;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.DungeonEntryInfoOuterClass.DungeonEntryInfo;
import emu.grasscutter.net.proto.DungeonEntryInfoRspOuterClass.DungeonEntryInfoRsp;
import java.util.*;

public class PacketDungeonEntryInfoRsp extends BasePacket {

    public PacketDungeonEntryInfoRsp(PointData pointData) {
        super(PacketOpcodes.DungeonEntryInfoRsp);

        DungeonEntryInfoRsp.Builder proto =
                DungeonEntryInfoRsp.newBuilder().setPointId(pointData.getId());

        if (pointData.getDungeonIds() != null) {
            for (int dungeonId : pointData.getDungeonIds()) {
                DungeonEntryInfo info = DungeonEntryInfo.newBuilder().setDungeonId(dungeonId).build();
                proto.addDungeonEntryList(info);
            }
        }

        this.setData(proto);
    }

    /**
     * Used in conjunction with quest-related dungeons.
     *
     * @param pointData The data associated with the dungeon.
     * @param additional A collection of additional quest-related dungeon IDs.
     */
    public PacketDungeonEntryInfoRsp(PointData pointData, List<Integer> additional) {
        super(PacketOpcodes.DungeonEntryInfoRsp);

        var packet = DungeonEntryInfoRsp.newBuilder().setPointId(pointData.getId());

        // Add dungeon IDs from the point data.
        if (pointData.getDungeonIds() != null) {
            Arrays.stream(pointData.getDungeonIds())
                    .forEach(
                            id -> packet.addDungeonEntryList(DungeonEntryInfo.newBuilder().setDungeonId(id)));
        }

        // Add additional dungeon IDs.
        additional.forEach(
                id -> packet.addDungeonEntryList(DungeonEntryInfo.newBuilder().setDungeonId(id)));

        this.setData(packet);
    }

    public PacketDungeonEntryInfoRsp() {
        super(PacketOpcodes.DungeonEntryInfoRsp);

        DungeonEntryInfoRsp proto = DungeonEntryInfoRsp.newBuilder().setRetcode(1).build();

        this.setData(proto);
    }
}
