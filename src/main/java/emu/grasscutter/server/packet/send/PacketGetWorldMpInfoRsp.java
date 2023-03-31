package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.world.World;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetWorldMpInfoRspOuterClass.GetWorldMpInfoRsp;

public class PacketGetWorldMpInfoRsp extends BasePacket {

    public PacketGetWorldMpInfoRsp(World world) {
        super(PacketOpcodes.GetWorldMpInfoRsp);

        GetWorldMpInfoRsp proto = GetWorldMpInfoRsp.newBuilder()
            .setIsInMpMode(world.isMultiplayer())
            .build();

        this.setData(proto);
    }
}
