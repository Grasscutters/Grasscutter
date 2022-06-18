package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerGetForceQuitBanInfoRspOuterClass.PlayerGetForceQuitBanInfoRsp;

public class PacketPlayerGetForceQuitBanInfoRsp extends BasePacket {

    public PacketPlayerGetForceQuitBanInfoRsp(int retcode) {
        super(PacketOpcodes.PlayerGetForceQuitBanInfoRsp);

        PlayerGetForceQuitBanInfoRsp proto = PlayerGetForceQuitBanInfoRsp.newBuilder()
            .setRetcode(retcode)
            .build();

        this.setData(proto);
    }
}
