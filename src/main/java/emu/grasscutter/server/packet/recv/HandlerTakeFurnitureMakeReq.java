package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.TakeFurnitureMakeReqOuterClass;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.TakeFurnitureMakeReq)
public class HandlerTakeFurnitureMakeReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = TakeFurnitureMakeReqOuterClass.TakeFurnitureMakeReq.parseFrom(payload);

        session.getPlayer().getFurnitureManager().take(req.getIndex(), req.getMakeId(), req.getIsFastFinish());
    }

}
