package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AbilityChangeNotifyOuterClass.AbilityChangeNotify;

public class PacketAbilityChangeNotify extends BasePacket {

    public PacketAbilityChangeNotify(EntityAvatar entity) {
        super(PacketOpcodes.AbilityChangeNotify, true);

        AbilityChangeNotify proto =
                AbilityChangeNotify.newBuilder()
                        .setEntityId(entity.getId())
                        .setAbilityControlBlock(entity.getAbilityControlBlock())
                        .build();

        this.setData(proto);
    }
}
