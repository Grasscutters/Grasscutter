package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.QuestListUpdateNotifyOuterClass.QuestListUpdateNotify;

public class PacketQuestListUpdateNotify extends BasePacket {

    public PacketQuestListUpdateNotify(GameQuest quest) {
        super(PacketOpcodes.QuestListUpdateNotify);

        QuestListUpdateNotify proto = QuestListUpdateNotify.newBuilder()
            .addQuestList(quest.toProto())
            .build();

        this.setData(proto);
    }
}
