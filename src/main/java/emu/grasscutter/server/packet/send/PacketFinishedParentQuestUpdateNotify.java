package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.quest.GameMainQuest;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.FinishedParentQuestUpdateNotifyOuterClass.FinishedParentQuestUpdateNotify;

import java.util.List;

public class PacketFinishedParentQuestUpdateNotify extends BasePacket {

	public PacketFinishedParentQuestUpdateNotify(GameMainQuest quest) {
		super(PacketOpcodes.FinishedParentQuestUpdateNotify);

		FinishedParentQuestUpdateNotify proto = FinishedParentQuestUpdateNotify.newBuilder()
				.addParentQuestList(quest.toProto())
				.build();

		this.setData(proto);
	}

    public PacketFinishedParentQuestUpdateNotify(List<GameMainQuest> quests) {
        super(PacketOpcodes.FinishedParentQuestUpdateNotify);

        var proto = FinishedParentQuestUpdateNotify.newBuilder();

        for(GameMainQuest mainQuest : quests) {
            proto.addParentQuestList(mainQuest.toProto());
        }
        proto.build();
        this.setData(proto);
    }
}
