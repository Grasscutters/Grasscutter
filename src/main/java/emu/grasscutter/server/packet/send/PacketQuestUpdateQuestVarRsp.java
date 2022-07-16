package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.QuestUpdateQuestVarReqOuterClass;
import emu.grasscutter.net.proto.QuestUpdateQuestVarRspOuterClass;

@Opcodes(PacketOpcodes.QuestUpdateQuestVarReq)
public class PacketQuestUpdateQuestVarRsp extends BasePacket {


    public PacketQuestUpdateQuestVarRsp(int questId) {
        super(PacketOpcodes.QuestUpdateQuestVarRsp);
        var rsp = QuestUpdateQuestVarRspOuterClass.QuestUpdateQuestVarRsp.newBuilder()
            .setQuestId(questId).build();
        this.setData(rsp);
    }
}
