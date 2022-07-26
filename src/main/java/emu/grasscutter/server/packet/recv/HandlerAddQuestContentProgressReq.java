package emu.grasscutter.server.packet.recv;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AddQuestContentProgressReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketAddQuestContentProgressRsp;
import emu.grasscutter.data.excels.QuestData.QuestCondition;
import java.util.List;
import java.util.stream.Stream;

@Opcodes(PacketOpcodes.AddQuestContentProgressReq)
public class HandlerAddQuestContentProgressReq extends PacketHandler {

	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = AddQuestContentProgressReqOuterClass.AddQuestContentProgressReq.parseFrom(payload);
        //Find all conditions in quest that are the same as the given one
        Stream<QuestCondition> finishCond = GameData.getQuestDataMap().get(req.getParam()).getFinishCond().stream();
        Stream<QuestCondition> acceptCond = GameData.getQuestDataMap().get(req.getParam()).getAcceptCond().stream();
        Stream<QuestCondition> failCond = GameData.getQuestDataMap().get(req.getParam()).getFailCond().stream();
        List<QuestCondition> allCondMatch = Stream.concat(Stream.concat(acceptCond,failCond),finishCond).filter(p -> p.getType().getValue() == req.getContentType()).toList();
        for(QuestCondition cond : allCondMatch ) {
            session.getPlayer().getQuestManager().triggerEvent(QuestTrigger.getContentTriggerByValue(req.getContentType()), cond.getParam());
        }
        session.send(new PacketAddQuestContentProgressRsp(req.getContentType()));
	}

}
