package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.*;

@Opcodes(PacketOpcodes.HomeChangeEditModeReq)
public class HandlerHomeChangeEditModeReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = HomeChangeEditModeReqOuterClass.HomeChangeEditModeReq.parseFrom(payload);

        if (req.getIsEnterEditMode() && !session.getPlayer().getCurHomeWorld().getGuests().isEmpty()) {
            session.send(
                    new PacketHomeChangeEditModeRsp(RetcodeOuterClass.Retcode.RET_HOME_HAS_GUEST_VALUE));
            return;
        }

        session.getPlayer().setInEditMode(req.getIsEnterEditMode());
        session.getPlayer().getCurHomeWorld().getHome().save();
        session.send(new PacketHomePreChangeEditModeNotify(req.getIsEnterEditMode()));
        session.send(new PacketHomeBasicInfoNotify(session.getPlayer(), req.getIsEnterEditMode()));
        session.send(new PacketHomeComfortInfoNotify(session.getPlayer()));

        session.send(new PacketHomeChangeEditModeRsp(req.getIsEnterEditMode()));
    }
}
