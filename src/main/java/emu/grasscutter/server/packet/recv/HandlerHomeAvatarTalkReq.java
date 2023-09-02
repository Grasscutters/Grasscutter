package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeAvatarTalkReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketHomeAvatarTalkFinishInfoNotify;
import emu.grasscutter.server.packet.send.PacketHomeAvatarTalkRsp;

@Opcodes(PacketOpcodes.HomeAvatarTalkReq)
public class HandlerHomeAvatarTalkReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = HomeAvatarTalkReqOuterClass.HomeAvatarTalkReq.parseFrom(payload);
        var talkIdSet =
                session
                        .getPlayer()
                        .getCurHomeWorld()
                        .getHome()
                        .onTalkedWithAvatar(req.getAvatarId(), req.getTalkId());
        session.send(new PacketHomeAvatarTalkFinishInfoNotify(session.getPlayer()));
        session.send(new PacketHomeAvatarTalkRsp(req.getAvatarId(), talkIdSet));
    }
}
