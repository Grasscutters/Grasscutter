package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PlayerGetForceQuitBanInfoRspOuterClass.PlayerGetForceQuitBanInfoRsp;

public class PacketPlayerGetForceQuitBanInfoRsp extends BasePacket {

    public PacketPlayerGetForceQuitBanInfoRsp(int retcode) {
        super(PacketOpcodes.PlayerGetForceQuitBanInfoRsp);

        PlayerGetForceQuitBanInfoRsp proto =
                PlayerGetForceQuitBanInfoRsp.newBuilder().setRetcode(retcode).build();

        this.setData(proto);
    }
}
