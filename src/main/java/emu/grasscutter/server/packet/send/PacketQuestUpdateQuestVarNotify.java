package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.QuestUpdateQuestVarNotifyOuterClass.QuestUpdateQuestVarNotify;
import java.util.stream.IntStream;

public class PacketQuestUpdateQuestVarNotify extends BasePacket {
    public PacketQuestUpdateQuestVarNotify(int mainQuestId, int... questVars) {
        super(PacketOpcodes.QuestUpdateQuestVarNotify);

        this.setData(
                QuestUpdateQuestVarNotify.newBuilder()
                        .setParentQuestId(mainQuestId)
                        .addAllQuestVar(IntStream.of(questVars).boxed().toList())
                        .build());
    }
}
