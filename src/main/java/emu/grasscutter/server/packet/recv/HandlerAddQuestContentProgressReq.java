package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AddQuestContentProgressReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketAddQuestContentProgressRsp;

@Opcodes(PacketOpcodes.AddQuestContentProgressReq)
public class HandlerAddQuestContentProgressReq extends PacketHandler {

	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = AddQuestContentProgressReqOuterClass.AddQuestContentProgressReq.parseFrom(payload);

		session.getPlayer().getQuestManager().triggerEvent(QuestTrigger.getContentTriggerByValue(req.getContentType()), req.getParam());

        session.send(new PacketAddQuestContentProgressRsp(req.getContentType()));
	}

}
