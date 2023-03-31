package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SelectWorktopOptionRspOuterClass.SelectWorktopOptionRsp;

public class PacketSelectWorktopOptionRsp extends BasePacket {

    public PacketSelectWorktopOptionRsp(int entityId, int optionId) {
        super(PacketOpcodes.SelectWorktopOptionRsp);

        SelectWorktopOptionRsp proto = SelectWorktopOptionRsp.newBuilder()
            .setGadgetEntityId(entityId)
            .setOptionId(optionId)
            .build();

        this.setData(proto);
    }
}
