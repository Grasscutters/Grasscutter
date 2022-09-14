package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.quest.GameMainQuest;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PacketHeadOuterClass;
import emu.grasscutter.net.proto.PlayerSetPauseReqOuterClass;
import emu.grasscutter.net.proto.QuestUpdateQuestVarReqOuterClass;
import emu.grasscutter.net.proto.QuestVarOpOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketPlayerSetPauseRsp;
import emu.grasscutter.server.packet.send.PacketQuestUpdateQuestVarRsp;

import java.util.List;

@Opcodes(PacketOpcodes.QuestUpdateQuestVarReq)
public class HandlerQuestUpdateQuestVarReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        //Client sends packets. One with the value, and one with the index and the new value to set/inc/dec
        var req = QuestUpdateQuestVarReqOuterClass.QuestUpdateQuestVarReq.parseFrom(payload);
        GameMainQuest mainQuest = session.getPlayer().getQuestManager().getMainQuestById(req.getQuestId()/100);
        List<QuestVarOpOuterClass.QuestVarOp> questVars = req.getQuestVarOpListList();
        if (mainQuest.getQuestVarsUpdate().size() == 0) {
            for (QuestVarOpOuterClass.QuestVarOp questVar : questVars) {
                mainQuest.getQuestVarsUpdate().add(questVar.getValue());
            }
        } else {
            for (QuestVarOpOuterClass.QuestVarOp questVar : questVars) {
                if (questVar.getIsAdd()) {
                    if (questVar.getValue() >= 0) {
                        mainQuest.incQuestVar(questVar.getIndex(), questVar.getValue());
                    } else {
                        mainQuest.decQuestVar(questVar.getIndex(), questVar.getValue());
                    }
                } else {
                    mainQuest.setQuestVar(questVar.getIndex(), mainQuest.getQuestVarsUpdate().get(0));
                }
                //remove the first element from the update list
                mainQuest.getQuestVarsUpdate().remove(0);
            }
        }
        session.send(new PacketQuestUpdateQuestVarRsp(req.getQuestId()));
    }

}
