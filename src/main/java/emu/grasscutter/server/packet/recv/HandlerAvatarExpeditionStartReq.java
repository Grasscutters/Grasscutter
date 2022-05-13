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
        float shortenRatio = 0;
        if (session.getServer().getExpeditionManager().getExpeditionAvatarEffectList() != null) {
            for (var l : session.getServer().getExpeditionManager().getExpeditionAvatarEffectList()){
                if (session.getPlayer().getAvatars().getAvatarByGuid(req.getAvatarGuid()).getAvatarId() == l.getAvatarId() &&
                        l.getArea().equals(session.getServer().getExpeditionManager().expId2Area(req.getExpId()))){
                    shortenRatio = l.getShortenRatio();
                }
            }
        }

        session.getPlayer().addExpeditionInfo(req.getAvatarGuid(), req.getExpId(), req.getHourTime(), startTime, shortenRatio);
        session.getPlayer().save();
        session.send(new PacketAvatarExpeditionStartRsp(session.getPlayer()));
    }
}