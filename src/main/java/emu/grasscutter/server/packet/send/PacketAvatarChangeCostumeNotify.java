package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AvatarChangeCostumeNotifyOuterClass.AvatarChangeCostumeNotify;

public class PacketAvatarChangeCostumeNotify extends BasePacket {

    public PacketAvatarChangeCostumeNotify(EntityAvatar entity) {
        super(PacketOpcodes.AvatarChangeCostumeNotify);

        AvatarChangeCostumeNotify proto =
                AvatarChangeCostumeNotify.newBuilder().setEntityInfo(entity.toProto()).build();

        this.setData(proto);
    }
}
