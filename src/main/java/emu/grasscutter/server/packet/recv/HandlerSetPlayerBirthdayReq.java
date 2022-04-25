package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SetPlayerBirthdayReqOuterClass.SetPlayerBirthdayReq;
import emu.grasscutter.net.proto.SocialDetailOuterClass.SocialDetail;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetPlayerSocialDetailRsp;
import emu.grasscutter.server.packet.send.PacketSetPlayerBirthdayRsp;

@Opcodes(PacketOpcodes.SetPlayerBirthdayReq)
public class HandlerSetPlayerBirthdayReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        SetPlayerBirthdayReq req = SetPlayerBirthdayReq.parseFrom(payload);

        if (req.getBirthday().getDay() > 0 && req.getBirthday().getMonth() > 0) {
            int day = req.getBirthday().getDay();
            int month = req.getBirthday().getMonth();

            // Update birthday value
            session.getPlayer().setBirthday(day, month);

            // Save birthday month and day
            session.getPlayer().save();
            SocialDetail.Builder detail = session.getPlayer().getSocialDetail();

            session.send(new PacketSetPlayerBirthdayRsp(session.getPlayer()));
            session.send(new PacketGetPlayerSocialDetailRsp(detail));
        }
    }
}
