package emu.grasscutter.server.packet.recv;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarExpeditionStartReqOuterClass.AvatarExpeditionStartReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketAvatarExpeditionStartRsp;
import emu.grasscutter.utils.Utils;

@Opcodes(PacketOpcodes.AvatarExpeditionStartReq)
public class HandlerAvatarExpeditionStartReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        AvatarExpeditionStartReq req = AvatarExpeditionStartReq.parseFrom(payload);

        int startTime = Utils.getCurrentSeconds();
        float shortenRatio = 1;
        // 1xx = MD 2xx = LY 3xx = DQ
        if ((avatarGuidCompare(session, req.getAvatarGuid(), 10000031) && req.getExpId() < 200) || (avatarGuidCompare(session, req.getAvatarGuid(), 10000032) && req.getExpId() < 200)){
            shortenRatio = 0.25f;
        }
        if ((avatarGuidCompare(session, req.getAvatarGuid(), 10000036) && req.getExpId() < 300) || (avatarGuidCompare(session, req.getAvatarGuid(), 10000042) && req.getExpId() < 300)){
            if ((avatarGuidCompare(session, req.getAvatarGuid(), 10000036) && req.getExpId() > 200) || (avatarGuidCompare(session, req.getAvatarGuid(), 10000042) && req.getExpId() > 200)){
                shortenRatio = 0.25f;
            }
        }
        if ((avatarGuidCompare(session, req.getAvatarGuid(), 10000056) && req.getExpId() > 300)){
            shortenRatio = 0.25f;
        }

        session.getPlayer().addExpeditionInfo(req.getAvatarGuid(), req.getExpId(), req.getHourTime(), startTime, shortenRatio);
        session.getPlayer().save();
        session.send(new PacketAvatarExpeditionStartRsp(session.getPlayer()));
    }

    private boolean avatarGuidCompare(GameSession session, long avatarGuid, int avatarId){
        return session.getPlayer().getAvatars().getAvatarByGuid(avatarGuid).getAvatarId() == avatarId;
    }
}