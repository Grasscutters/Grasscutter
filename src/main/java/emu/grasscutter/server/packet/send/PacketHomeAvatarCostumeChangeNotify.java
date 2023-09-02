package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeAvatarCostumeChangeNotifyOuterClass;

public class PacketHomeAvatarCostumeChangeNotify extends BasePacket {
    public PacketHomeAvatarCostumeChangeNotify(int avatarId, int costumeId) {
        super(PacketOpcodes.HomeAvatarCostumeChangeNotify);

        this.setData(
                HomeAvatarCostumeChangeNotifyOuterClass.HomeAvatarCostumeChangeNotify.newBuilder()
                        .setAvatarId(avatarId)
                        .setCostumeId(costumeId));
    }
}
