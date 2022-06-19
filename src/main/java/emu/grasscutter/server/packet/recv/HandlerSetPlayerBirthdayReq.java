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

        // RET_BIRTHDAY_CANNOT_BE_SET_TWICE = 7009
        if (session.getPlayer().hasBirthday()) {
            session.send(new PacketSetPlayerBirthdayRsp(7009));
            return;
        }

        int month = req.getBirthday().getMonth();
        int day = req.getBirthday().getDay();

        // RET_BIRTHDAY_FORMAT_ERROR = 7022
        if (!this.isValidBirthday(month, day)) {
            session.send(new PacketSetPlayerBirthdayRsp(7022));
            return;
        }

        // Update birthday value
        session.getPlayer().setBirthday(day, month);

        // Save birthday month and day
        session.getPlayer().save();
        SocialDetail.Builder detail = session.getPlayer().getSocialDetail();

        session.send(new PacketSetPlayerBirthdayRsp(session.getPlayer()));
        session.send(new PacketGetPlayerSocialDetailRsp(detail));
    }

    private boolean isValidBirthday(int month, int day) {

        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return day > 0 & day <= 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return day > 0 && day <= 30;
            case 2:
                return day > 0 & day <= 29;
        }

        return false;
    }

}
