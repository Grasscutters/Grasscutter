package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerApplyEnterHomeResultRspOuterClass;

public class PacketPlayerApplyEnterHomeResultRsp extends BasePacket {
    public PacketPlayerApplyEnterHomeResultRsp(int uid, boolean agreed) {
        super(PacketOpcodes.PlayerApplyEnterHomeResultRsp);

        this.setData(PlayerApplyEnterHomeResultRspOuterClass.PlayerApplyEnterHomeResultRsp.newBuilder()
            .setApplyUid(uid)
            .setIsAgreed(agreed));
    }
}
