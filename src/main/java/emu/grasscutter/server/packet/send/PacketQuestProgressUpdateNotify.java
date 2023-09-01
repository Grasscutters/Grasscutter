package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.QuestProgressUpdateNotifyOuterClass.QuestProgressUpdateNotify;

public class PacketQuestProgressUpdateNotify extends BasePacket {

    public PacketQuestProgressUpdateNotify(GameQuest quest) {
        super(PacketOpcodes.QuestProgressUpdateNotify);

        QuestProgressUpdateNotify.Builder proto =
                QuestProgressUpdateNotify.newBuilder().setQuestId(quest.getSubQuestId());

        if (quest.getFinishProgressList() != null) {
            for (int i : quest.getFinishProgressList()) {
                proto.addFinishProgressList(i);
            }
        }

        if (quest.getFailProgressList() != null) {
            for (int i : quest.getFailProgressList()) {
                proto.addFailProgressList(i);
            }
        }

        this.setData(proto);
    }
}
