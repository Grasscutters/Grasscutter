package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.VehicleInteractReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketVehicleInteractRsp;

@Opcodes(PacketOpcodes.VehicleInteractReq)
public class HandlerVehicleInteractReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        VehicleInteractReqOuterClass.VehicleInteractReq req =
                VehicleInteractReqOuterClass.VehicleInteractReq.parseFrom(payload);
        session
                .getPlayer()
                .getStaminaManager()
                .handleVehicleInteractReq(session, req.getEntityId(), req.getInteractType());
        session.send(
                new PacketVehicleInteractRsp(
                        session.getPlayer(), req.getEntityId(), req.getInteractType()));
    }
}
