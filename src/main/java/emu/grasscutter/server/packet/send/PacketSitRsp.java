package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SitRspOuterClass.SitRsp;
import emu.grasscutter.utils.Position;

public class PacketSitRsp extends GenshinPacket {

    public PacketSitRsp(long chairId, Position pos, int EntityId) {
        super(PacketOpcodes.SitRsp);

        SitRsp proto = SitRsp.newBuilder()
                .setEntityId(EntityId)
                .setPosition(pos.toProto())
                .setChairId(chairId)
                .build();

        this.setData(proto);
    }
}
