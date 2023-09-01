package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.MusicGameStartRspOuterClass;

public class PacketMusicGameStartRsp extends BasePacket {

    public PacketMusicGameStartRsp(int musicBasicId, long musicShareId) {
        super(PacketOpcodes.MusicGameStartRsp);

        var proto = MusicGameStartRspOuterClass.MusicGameStartRsp.newBuilder();

        proto.setMusicBasicId(musicBasicId).setUgcGuid(musicShareId);

        this.setData(proto);
    }
}
