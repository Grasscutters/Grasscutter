package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ServerCondMeetQuestListUpdateNotifyOuterClass.ServerCondMeetQuestListUpdateNotify;

import java.util.List;

public class PacketServerCondMeetQuestListUpdateNotify extends BasePacket {

    public PacketServerCondMeetQuestListUpdateNotify(Player player) {
        super(PacketOpcodes.ServerCondMeetQuestListUpdateNotify);

        ServerCondMeetQuestListUpdateNotify.Builder proto = ServerCondMeetQuestListUpdateNotify.newBuilder();

        /*
        player.getQuestManager().forEachQuest(quest -> {
            if (quest.getState().getValue() <= 2) {
                proto.addAddQuestIdList(quest.getQuestId());
            }
        });
        */

        this.setData(proto);
    }

    public PacketServerCondMeetQuestListUpdateNotify(List<GameQuest> quests) {
        super(PacketOpcodes.ServerCondMeetQuestListUpdateNotify);

        ServerCondMeetQuestListUpdateNotify.Builder proto = ServerCondMeetQuestListUpdateNotify.newBuilder();
        for (GameQuest quest : quests) {
            proto.addAddQuestIdList(quest.getSubQuestId());
        }
        proto.build();

        this.setData(proto);
    }

    public PacketServerCondMeetQuestListUpdateNotify() {
        super(PacketOpcodes.ServerCondMeetQuestListUpdateNotify);

        ServerCondMeetQuestListUpdateNotify.Builder proto = ServerCondMeetQuestListUpdateNotify.newBuilder();
        proto.build();

        this.setData(proto);
    }
}
