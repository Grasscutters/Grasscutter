package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketEnterScenePeerNotify;
import emu.grasscutter.server.packet.send.PacketEnterSceneReadyRsp;

@Opcodes(PacketOpcodes.EnterSceneReadyReq)
public class HandlerEnterSceneReadyReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) {
        session.send(new PacketEnterScenePeerNotify(session.getPlayer()));
        session.send(new PacketEnterSceneReadyRsp(session.getPlayer()));
    }

}
