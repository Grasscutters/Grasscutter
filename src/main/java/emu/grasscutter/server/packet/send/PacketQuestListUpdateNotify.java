package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.quest.GameMainQuest;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.QuestListUpdateNotifyOuterClass.QuestListUpdateNotify;

import java.util.List;

public class PacketQuestListUpdateNotify extends BasePacket {

	public PacketQuestListUpdateNotify(GameQuest quest) {
		super(PacketOpcodes.QuestListUpdateNotify);

		QuestListUpdateNotify proto = QuestListUpdateNotify.newBuilder()
				.addQuestList(quest.toProto())
				.build();

		this.setData(proto);
	}

    public PacketQuestListUpdateNotify(List<GameQuest> quests) {
        super(PacketOpcodes.QuestListUpdateNotify);
        var proto = QuestListUpdateNotify.newBuilder();
        for(GameQuest quest : quests) {
            proto.addQuestList(quest.toProto());
        }
        proto.build();

        this.setData(proto);
    }
}
