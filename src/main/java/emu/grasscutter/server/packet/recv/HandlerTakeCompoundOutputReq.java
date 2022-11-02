package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.TakeCompoundOutputReqOuterClass;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.TakeCompoundOutputReq)
public class HandlerTakeCompoundOutputReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = TakeCompoundOutputReqOuterClass.TakeCompoundOutputReq.parseFrom(payload);
        session.getPlayer().getCookingCompoundManager().handleTakeCompoundOutputReq(req);
    }
}
