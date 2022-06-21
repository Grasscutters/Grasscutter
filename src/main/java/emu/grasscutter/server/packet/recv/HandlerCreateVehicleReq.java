package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.CreateVehicleReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketCreateVehicleRsp;
import emu.grasscutter.utils.Position;

@Opcodes(PacketOpcodes.CreateVehicleReq)
public class HandlerCreateVehicleReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        CreateVehicleReqOuterClass.CreateVehicleReq req = CreateVehicleReqOuterClass.CreateVehicleReq.parseFrom(payload);
        session.send(new PacketCreateVehicleRsp(session.getPlayer(), req.getVehicleId(), req.getScenePointId(), new Position(req.getPos()), new Position(req.getRot())));
    }
}
