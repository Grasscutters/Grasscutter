package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.enums.QuestState;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.QuestListNotifyOuterClass.QuestListNotify;

public class PacketQuestListNotify extends BasePacket {

    public PacketQuestListNotify(Player player) {
        super(PacketOpcodes.QuestListNotify, true);

        QuestListNotify.Builder proto = QuestListNotify.newBuilder();

        player
                .getQuestManager()
                .forEachQuest(
                        quest -> {
                            if (quest.getState() != QuestState.QUEST_STATE_UNSTARTED) {
                                proto.addQuestList(quest.toProto());
                            }
                        });

        this.setData(proto);
    }
}
