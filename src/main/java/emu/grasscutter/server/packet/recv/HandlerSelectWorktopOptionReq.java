package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.quest.enums.QuestContent;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SelectWorktopOptionReqOuterClass.SelectWorktopOptionReq;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.ScriptArgs;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketSelectWorktopOptionRsp;

@Opcodes(PacketOpcodes.SelectWorktopOptionReq)
public class HandlerSelectWorktopOptionReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        SelectWorktopOptionReq req = SelectWorktopOptionReq.parseFrom(payload);

        try {
            GameEntity entity = session.getPlayer().getScene().getEntityById(req.getGadgetEntityId());

            if (!(entity instanceof EntityGadget)) {
                return;
            }
            session.getPlayer().getScene().selectWorktopOptionWith(req);
            session
                    .getPlayer()
                    .getScene()
                    .getScriptManager()
                    .callEvent(
                            new ScriptArgs(
                                    entity.getGroupId(),
                                    EventType.EVENT_SELECT_OPTION,
                                    entity.getConfigId(),
                                    req.getOptionId()));
            session
                    .getPlayer()
                    .getQuestManager()
                    .queueEvent(
                            QuestContent.QUEST_CONTENT_WORKTOP_SELECT, entity.getConfigId(), req.getOptionId());
        } finally {
            // Always send packet
            session.send(new PacketSelectWorktopOptionRsp(req.getGadgetEntityId(), req.getOptionId()));
        }
    }
}
