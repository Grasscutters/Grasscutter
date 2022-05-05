package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.managers.MapMarkManager.MapMark;
import emu.grasscutter.game.managers.MapMarkManager.MapMarksManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.net.proto.MarkMapReqOuterClass.MarkMapReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketMarkMapRsp;
import emu.grasscutter.server.packet.send.PacketMarkNewNotify;
import emu.grasscutter.server.packet.send.PacketSceneEntityAppearNotify;
import emu.grasscutter.utils.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
		MarkMapReq.Operation op = req.getOp();
		Player player = session.getPlayer();
		MapMarksManager mapMarksManager = player.getMapMarksManager();
		if (op == MarkMapReq.Operation.ADD) {
			MapMark newMapMark = new MapMark(req.getMark());
			// keep teleporting functionality on fishhook mark.
			if (newMapMark.getMapMarkPointType() == MapMarkPointTypeOuterClass.MapMarkPointType.MAP_MARK_POINT_TYPE_FISH_POOL) {
				teleport(player, newMapMark);
				return;
			}
			if (mapMarksManager.addMapMark(newMapMark)) {
				player.save();
			}
		} else if (op == MarkMapReq.Operation.MOD) {
			MapMark newMapMark = new MapMark(req.getMark());
			if (mapMarksManager.removeMapMark(newMapMark.getPosition())) {
				if (mapMarksManager.addMapMark(newMapMark)) {
					player.save();
				}
			}
		} else if (op == MarkMapReq.Operation.DEL) {
			MapMark newMapMark = new MapMark(req.getMark());
			if (mapMarksManager.removeMapMark(newMapMark.getPosition())) {
				player.save();
			}
		} else if (op == MarkMapReq.Operation.GET) {
			// no-op
		}
		// send all marks to refresh client map view.
		HashMap<String, MapMark> mapMarks = mapMarksManager.getAllMapMarks();
		session.send(new PacketMarkMapRsp(player, mapMarks));
	}

	private void teleport(Player player, MapMark mapMark) {
		float y = isInt(mapMark.getName()) ? Integer.parseInt(mapMark.getName()) : 300;
		float x = mapMark.getPosition().getX();
		float z = mapMark.getPosition().getZ();
		player.getPos().set(x, y, z);
		if (mapMark.getSceneId() != player.getSceneId()) {
			player.getWorld().transferPlayerToScene(player, mapMark.getSceneId(),
					player.getPos());
		} else {
			player.getScene().broadcastPacket(new PacketSceneEntityAppearNotify(player));
		}
	}
}
