package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarGainFlycloakNotifyOuterClass.AvatarGainFlycloakNotify;

public class PacketAvatarGainFlycloakNotify extends BasePacket {

    public PacketAvatarGainFlycloakNotify(int flycloak) {
        super(PacketOpcodes.AvatarGainFlycloakNotify);

        AvatarGainFlycloakNotify proto = AvatarGainFlycloakNotify.newBuilder()
            .setFlycloakId(flycloak)
            .build();

        this.setData(proto);
    }
}
