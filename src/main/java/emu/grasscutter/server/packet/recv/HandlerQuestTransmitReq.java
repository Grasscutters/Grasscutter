package emu.grasscutter.server.packet.recv;

import emu.grasscutter.data.GameData;
import emu.grasscutter.game.quest.GameMainQuest;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketQuestTransmitRsp;
import emu.grasscutter.utils.Position;
import emu.grasscutter.net.proto.QuestTransmitReqOuterClass.QuestTransmitReq;

import java.util.List;
import java.util.ArrayList;
import lombok.val;

@Opcodes(PacketOpcodes.QuestTransmitReq)
public class HandlerQuestTransmitReq extends PacketHandler {

	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		val req = QuestTransmitReq.parseFrom(payload);
		GameMainQuest mainQuest = session.getPlayer().getQuestManager().getMainQuestById(req.getQuestId() / 100);
		List<Position> posAndRot = new ArrayList<>();
		boolean result = false;
		if(mainQuest.hasTeleportPostion(req.getQuestId(), posAndRot)){
			int sceneId = GameData.getTeleportDataMap().get(req.getQuestId()).getTransmit_points().get(0).getScene_id();
			result = session.getPlayer().getWorld().transferPlayerToScene(session.getPlayer(), sceneId, posAndRot.get(0));
		}
		session.send(new PacketQuestTransmitRsp(result, req));
	}
}
