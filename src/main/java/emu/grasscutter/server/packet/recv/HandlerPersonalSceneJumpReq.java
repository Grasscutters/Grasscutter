package emu.grasscutter.server.packet.recv;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.custom.ScenePointEntry;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PersonalSceneJumpReqOuterClass.PersonalSceneJumpReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketPersonalSceneJumpRsp;
import emu.grasscutter.utils.Position;


@Opcodes(PacketOpcodes.PersonalSceneJumpReq)
public class HandlerPersonalSceneJumpReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		PersonalSceneJumpReq req = PersonalSceneJumpReq.parseFrom(payload);

		// get the scene point
		String code = session.getPlayer().getSceneId() + "_" + req.getPointId();
		ScenePointEntry scenePointEntry = GameData.getScenePointEntries().get(code);

		if (scenePointEntry != null) {
			float x = scenePointEntry.getPointData().getTranPos().getX();
			float y = scenePointEntry.getPointData().getTranPos().getY();
			float z = scenePointEntry.getPointData().getTranPos().getZ();
			Position pos = new Position(x, y, z);
			int sceneId = scenePointEntry.getPointData().getTranSceneId();

			session.getPlayer().getWorld().transferPlayerToScene(session.getPlayer(), sceneId, pos);
			session.send(new PacketPersonalSceneJumpRsp(sceneId, pos));
		}

	}

}
