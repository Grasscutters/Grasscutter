package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AvatarAddNotifyOuterClass.AvatarAddNotify;

public class PacketAvatarAddNotify extends BasePacket {

    public PacketAvatarAddNotify(Avatar avatar, boolean addedToTeam) {
        super(PacketOpcodes.AvatarAddNotify);

        AvatarAddNotify proto =
                AvatarAddNotify.newBuilder().setAvatar(avatar.toProto()).setIsInTeam(addedToTeam).build();

        this.setData(proto);
    }
}
