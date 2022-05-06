package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarExpeditionCallBackReqOuterClass.AvatarExpeditionCallBackReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketAvatarExpeditionCallBackRsp;
import emu.grasscutter.server.packet.send.PacketAvatarExpeditionStartRsp;
import emu.grasscutter.utils.Utils;

@Opcodes(PacketOpcodes.AvatarExpeditionCallBackReq)
public class HandlerAvatarExpeditionCallBackReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        AvatarExpeditionCallBackReq req = AvatarExpeditionCallBackReq.parseFrom(payload);

        for (int i = 0; i < req.getAvatarGuidCount(); i++) {
            session.getPlayer().removeExpeditionInfo(req.getAvatarGuid(i));
        }

        session.getPlayer().save();
        session.send(new PacketAvatarExpeditionCallBackRsp(session.getPlayer()));
    }
}
