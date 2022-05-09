package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.VehicleSpawnReqOuterClass;

import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketVehicleSpawnRsp;

import emu.grasscutter.utils.Position;

@Opcodes(PacketOpcodes.VehicleSpawnReq)
public class HandlerVehicleSpawnReq extends PacketHandler {

	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		VehicleSpawnReqOuterClass.VehicleSpawnReq req = VehicleSpawnReqOuterClass.VehicleSpawnReq.parseFrom(payload);
		session.send(new PacketVehicleSpawnRsp(session.getPlayer(), req.getVehicleId(), req.getPointId(), new Position(req.getPos()), new Position(req.getRot())));
	}
}
