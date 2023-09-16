package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.home.suite.event.HomeAvatarSummonEvent;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeAvatarSummonEventRspOuterClass;

public class PacketHomeAvatarSummonEventRsp extends BasePacket {
    public PacketHomeAvatarSummonEventRsp(HomeAvatarSummonEvent event) {
        super(PacketOpcodes.HomeAvatarSummonEventRsp);

        this.setData(
                HomeAvatarSummonEventRspOuterClass.HomeAvatarSummonEventRsp.newBuilder()
                        .setEventId(event.getEventId()));
    }

    public PacketHomeAvatarSummonEventRsp(int retcode) {
        super(PacketOpcodes.HomeAvatarSummonEventRsp);

        this.setData(
                HomeAvatarSummonEventRspOuterClass.HomeAvatarSummonEventRsp.newBuilder()
                        .setRetcode(retcode));
    }
}
