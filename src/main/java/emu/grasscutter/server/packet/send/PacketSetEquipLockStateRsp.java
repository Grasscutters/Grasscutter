package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SetEquipLockStateRspOuterClass.SetEquipLockStateRsp;

public class PacketSetEquipLockStateRsp extends BasePacket {

    public PacketSetEquipLockStateRsp(GameItem equip) {
        super(PacketOpcodes.SetEquipLockStateRsp);

        this.buildHeader(0);

        SetEquipLockStateRsp proto =
                SetEquipLockStateRsp.newBuilder()
                        .setTargetEquipGuid(equip.getGuid())
                        .setIsLocked(equip.isLocked())
                        .build();

        this.setData(proto);
    }
}
