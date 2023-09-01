package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.QuestDelNotifyOuterClass.QuestDelNotify;

public class PacketDelQuestNotify extends BasePacket {

    public PacketDelQuestNotify(int questId) {
        super(PacketOpcodes.QuestDelNotify);

        QuestDelNotify proto = QuestDelNotify.newBuilder().setQuestId(questId).build();

        this.setData(proto);
    }
}
