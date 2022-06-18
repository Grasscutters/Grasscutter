package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarFlycloakChangeNotifyOuterClass.AvatarFlycloakChangeNotify;

public class PacketAvatarFlycloakChangeNotify extends BasePacket {

    public PacketAvatarFlycloakChangeNotify(Avatar avatar) {
        super(PacketOpcodes.AvatarFlycloakChangeNotify);

        AvatarFlycloakChangeNotify proto = AvatarFlycloakChangeNotify.newBuilder()
            .setAvatarGuid(avatar.getGuid())
            .setFlycloakId(avatar.getFlyCloak())
            .build();

        this.setData(proto);
    }
}
