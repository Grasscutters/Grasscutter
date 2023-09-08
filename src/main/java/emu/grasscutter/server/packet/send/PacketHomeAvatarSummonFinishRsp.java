package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeAvatarSummonFinishRspOuterClass;

public class PacketHomeAvatarSummonFinishRsp extends BasePacket {
    public PacketHomeAvatarSummonFinishRsp(int eventId) {
        super(PacketOpcodes.HomeAvatarSummonFinishRsp);

        this.setData(
                HomeAvatarSummonFinishRspOuterClass.HomeAvatarSummonFinishRsp.newBuilder()
                        .setEventId(eventId));
    }
}
