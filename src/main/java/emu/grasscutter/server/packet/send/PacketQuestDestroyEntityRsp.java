package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.QuestDestroyEntityReqOuterClass.QuestDestroyEntityReq;
import emu.grasscutter.net.proto.QuestDestroyEntityRspOuterClass.QuestDestroyEntityRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;

public class PacketQuestDestroyEntityRsp extends BasePacket {

    public PacketQuestDestroyEntityRsp(boolean success, QuestDestroyEntityReq req) {
        super(PacketOpcodes.QuestDestroyEntityRsp);

        this.setData(
                QuestDestroyEntityRsp.newBuilder()
                        .setQuestId(req.getQuestId())
                        .setEntityId(req.getEntityId())
                        .setSceneId(req.getSceneId())
                        .setRetcode(success ? Retcode.RET_SUCC_VALUE : Retcode.RET_FAIL_VALUE));
    }
}
