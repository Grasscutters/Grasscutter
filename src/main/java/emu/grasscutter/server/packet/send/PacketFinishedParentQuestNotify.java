package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.GameMainQuest;
import emu.grasscutter.game.quest.enums.ParentQuestState;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.FinishedParentQuestNotifyOuterClass.FinishedParentQuestNotify;

public class PacketFinishedParentQuestNotify extends BasePacket {

	public PacketFinishedParentQuestNotify(Player player) {
		super(PacketOpcodes.FinishedParentQuestNotify, true);

		FinishedParentQuestNotify.Builder proto = FinishedParentQuestNotify.newBuilder();

		for (GameMainQuest mainQuest : player.getQuestManager().getMainQuests().values()) {
            //Canceled Quests do not appear in this packet
            if(mainQuest.getState() != ParentQuestState.PARENT_QUEST_STATE_CANCELED) {
                proto.addParentQuestList(mainQuest.toProto());
            }
		}

		this.setData(proto);
	}
}
