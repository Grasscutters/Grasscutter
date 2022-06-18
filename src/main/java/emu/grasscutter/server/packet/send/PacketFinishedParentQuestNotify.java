package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.GameMainQuest;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.FinishedParentQuestNotifyOuterClass.FinishedParentQuestNotify;

public class PacketFinishedParentQuestNotify extends BasePacket {

    public PacketFinishedParentQuestNotify(Player player) {
        super(PacketOpcodes.FinishedParentQuestNotify, true);

        FinishedParentQuestNotify.Builder proto = FinishedParentQuestNotify.newBuilder();

        for (GameMainQuest mainQuest : player.getQuestManager().getQuests().values()) {
            proto.addParentQuestList(mainQuest.toProto());
        }

        this.setData(proto);
    }
}
