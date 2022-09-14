package emu.grasscutter.server.packet.send;

import java.util.Map;

import emu.grasscutter.game.expedition.ExpeditionInfo;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarExpeditionCallBackRspOuterClass.AvatarExpeditionCallBackRsp;

public class PacketAvatarExpeditionCallBackRsp extends BasePacket {
    public PacketAvatarExpeditionCallBackRsp(Map<Long, ExpeditionInfo> expeditionInfo) {
        super(PacketOpcodes.AvatarExpeditionCallBackRsp);

        AvatarExpeditionCallBackRsp.Builder proto = AvatarExpeditionCallBackRsp.newBuilder();
        expeditionInfo.forEach((key, e) -> proto.putExpeditionInfoMap(key, e.toProto()));

        this.setData(proto.build());
    }
}
