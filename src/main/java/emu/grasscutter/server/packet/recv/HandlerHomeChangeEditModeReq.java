package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeChangeEditModeReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketHomeBasicInfoNotify;
import emu.grasscutter.server.packet.send.PacketHomeChangeEditModeRsp;
import emu.grasscutter.server.packet.send.PacketHomeComfortInfoNotify;
import emu.grasscutter.server.packet.send.PacketHomeUnknown1Notify;

@Opcodes(PacketOpcodes.HomeChangeEditModeReq)
public class HandlerHomeChangeEditModeReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = HomeChangeEditModeReqOuterClass.HomeChangeEditModeReq.parseFrom(payload);

        session.send(new PacketHomeUnknown1Notify(req.getIsEnterEditMode()));
        session.send(new PacketHomeBasicInfoNotify(session.getPlayer(), req.getIsEnterEditMode()));
        session.send(new PacketHomeComfortInfoNotify(session.getPlayer()));

        session.send(new PacketHomeChangeEditModeRsp(req.getIsEnterEditMode()));
    }

}
