package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.FurnitureMakeStartReqOuterClass;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.FurnitureMakeStartReq)
public class HandlerFurnitureMakeStartReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = FurnitureMakeStartReqOuterClass.FurnitureMakeStartReq.parseFrom(payload);

        session.getPlayer().getFurnitureManager().startMake(req.getMakeId(), req.getAvatarId());

    }

}
