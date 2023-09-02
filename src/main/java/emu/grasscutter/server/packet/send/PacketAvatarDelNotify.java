package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AvatarDelNotifyOuterClass.AvatarDelNotify;
import java.util.List;

public class PacketAvatarDelNotify extends BasePacket {

    public PacketAvatarDelNotify(List<Long> avatarGuidList) {
        super(PacketOpcodes.AvatarDelNotify);

        AvatarDelNotify proto =
                AvatarDelNotify.newBuilder().addAllAvatarGuidList(avatarGuidList).build();

        this.setData(proto);
    }
}
