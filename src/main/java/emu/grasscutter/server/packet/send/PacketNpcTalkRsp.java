package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.NpcTalkRspOuterClass.NpcTalkRsp;

public class PacketNpcTalkRsp extends BasePacket {
    public PacketNpcTalkRsp(int npcEntityId, int curTalkId, int entityId) {
        super(PacketOpcodes.NpcTalkRsp);

        NpcTalkRsp p = NpcTalkRsp.newBuilder()
            .setNpcEntityId(npcEntityId)
            .setCurTalkId(curTalkId)
            .setEntityId(entityId)
            .build();

        this.setData(p);
    }
}
