package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DestroyMaterialReqOuterClass.DestroyMaterialReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.DestroyMaterialReq)
public class HandlerDestroyMaterialReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        DestroyMaterialReq req = DestroyMaterialReq.parseFrom(payload);

        // Delete items
        session.getServer().getInventorySystem().destroyMaterial(session.getPlayer(), req.getMaterialListList());
    }
}
