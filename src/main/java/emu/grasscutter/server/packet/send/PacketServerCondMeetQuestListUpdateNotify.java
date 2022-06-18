package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ServerCondMeetQuestListUpdateNotifyOuterClass.ServerCondMeetQuestListUpdateNotify;

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

    public PacketServerCondMeetQuestListUpdateNotify(GameQuest quest) {
        super(PacketOpcodes.ServerCondMeetQuestListUpdateNotify);

        ServerCondMeetQuestListUpdateNotify proto = ServerCondMeetQuestListUpdateNotify.newBuilder()
            //.addAddQuestIdList(quest.getQuestId())
            .build();

        this.setData(proto);
    }
}
