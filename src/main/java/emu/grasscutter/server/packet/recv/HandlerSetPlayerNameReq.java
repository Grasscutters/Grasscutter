package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SetPlayerNameReqOuterClass.SetPlayerNameReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketSetPlayerNameRsp;

@Opcodes(PacketOpcodes.SetPlayerNameReq)
public class HandlerSetPlayerNameReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        // Auto template
        SetPlayerNameReq req = SetPlayerNameReq.parseFrom(payload);

        if (req.getNickName() != null && req.getNickName().length() > 0) {
            session.getPlayer().setNickname(req.getNickName());
            session.send(new PacketSetPlayerNameRsp(session.getPlayer()));
        }
    }

}
