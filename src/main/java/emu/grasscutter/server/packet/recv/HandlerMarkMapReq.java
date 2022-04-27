package emu.grasscutter.server.packet.recv;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.props.EnterReason;
import emu.grasscutter.game.world.World;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EnterTypeOuterClass.EnterType;
import emu.grasscutter.net.proto.MarkMapReqOuterClass.MarkMapReq;
import emu.grasscutter.net.proto.OperationOuterClass.Operation;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketPlayerEnterSceneNotify;
import emu.grasscutter.server.packet.send.PacketSceneEntityAppearNotify;

@Opcodes(PacketOpcodes.MarkMapReq)
public class HandlerMarkMapReq extends PacketHandler {

	private static boolean isInt(String str) {

		try {
			@SuppressWarnings("unused")
			int x = Integer.parseInt(str);
			return true; // String is an Integer
		} catch (NumberFormatException e) {
			return false; // String is not an Integer
		}

	}

	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		MarkMapReq req = MarkMapReq.parseFrom(payload);

		if (req.getOp() != MarkMapReq.Operation.ADD) {
			return;
		}

		session.getPlayer().getPos().setX(req.getMark().getPos().getX());
		session.getPlayer().getPos().setZ(req.getMark().getPos().getZ());

		session.getPlayer().getPos()
				.setY(isInt(req.getMark().getName()) ? Integer.parseInt(req.getMark().getName()) : 300);

		Grasscutter.getLogger().info("Player [" + session.getPlayer().getUid() + ":" + session.getPlayer().getNickname()
				+ "] tp to " + session.getPlayer().getPos() + " Scene id: " + req.getMark().getSceneId());

		if (req.getMark().getSceneId() != session.getPlayer().getSceneId()) {
			session.getPlayer().getWorld().transferPlayerToScene(session.getPlayer(), req.getMark().getSceneId(),
					session.getPlayer().getPos());
		} else {
			session.getPlayer().getScene().broadcastPacket(new PacketSceneEntityAppearNotify(session.getPlayer()));
		}
	}
}
