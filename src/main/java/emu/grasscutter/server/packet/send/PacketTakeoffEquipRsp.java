package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.TakeoffEquipRspOuterClass.TakeoffEquipRsp;

public class PacketTakeoffEquipRsp extends BasePacket {

    public PacketTakeoffEquipRsp(long avatarGuid, int slot) {
        super(PacketOpcodes.TakeoffEquipRsp);

        TakeoffEquipRsp proto =
                TakeoffEquipRsp.newBuilder().setAvatarGuid(avatarGuid).setSlot(slot).build();

        this.setData(proto);
    }
}
