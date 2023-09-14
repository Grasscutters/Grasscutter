package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.home.HomeScene;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeChangeEditModeReqOuterClass;
import emu.grasscutter.net.proto.RetcodeOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketHomeBasicInfoNotify;
import emu.grasscutter.server.packet.send.PacketHomeChangeEditModeRsp;
import emu.grasscutter.server.packet.send.PacketHomeComfortInfoNotify;
import emu.grasscutter.server.packet.send.PacketHomePreChangeEditModeNotify;

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

        if (!req.getIsEnterEditMode()) {
            var scene = (HomeScene) session.getPlayer().getScene();
            scene.onLeaveEditMode();
        }

        session.send(new PacketHomeChangeEditModeRsp(req.getIsEnterEditMode()));
    }
}
