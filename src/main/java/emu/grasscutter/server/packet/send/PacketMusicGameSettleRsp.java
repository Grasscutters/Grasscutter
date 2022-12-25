package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.MusicGameSettleRspOuterClass;

public class PacketMusicGameSettleRsp extends BasePacket {

    public PacketMusicGameSettleRsp(int musicBasicId, long musicShareId, boolean isNewRecord) {
        super(PacketOpcodes.MusicGameSettleRsp);

        var proto = MusicGameSettleRspOuterClass.MusicGameSettleRsp.newBuilder();

        proto.setMusicBasicId(musicBasicId)
            .setMusicShareId(musicShareId)
            .setIsNewRecord(isNewRecord);

        this.setData(proto);
    }
}
