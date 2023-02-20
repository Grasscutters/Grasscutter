package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DKMDNEAEGDFOuterClass;
import emu.grasscutter.net.proto.PlayerIpRegionNotifyOuterClass;

public class PacketSkipPlayerGameTimeRsp extends BasePacket {
    public PacketSkipPlayerGameTimeRsp(PlayerIpRegionNotifyOuterClass.PlayerIpRegionNotify req) {
        super(PacketOpcodes.DKMDNEAEGDF);

        var proto = DKMDNEAEGDFOuterClass.DKMDNEAEGDF.newBuilder()
            .setClientGameTime(req.getClientGameTime())
            .setGameTime(req.getGameTime())
            .build();

        this.setData(proto);
    }
}
