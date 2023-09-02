package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AvatarUpgradeRspOuterClass.AvatarUpgradeRsp;
import java.util.Map;

public class PacketAvatarUpgradeRsp extends BasePacket {

    public PacketAvatarUpgradeRsp(Avatar avatar, int oldLevel, Map<Integer, Float> oldFightPropMap) {
        super(PacketOpcodes.AvatarUpgradeRsp);

        this.buildHeader(0);

        AvatarUpgradeRsp proto =
                AvatarUpgradeRsp.newBuilder()
                        .setAvatarGuid(avatar.getGuid())
                        .setOldLevel(oldLevel)
                        .setCurLevel(avatar.getLevel())
                        .putAllOldFightPropMap(oldFightPropMap)
                        .putAllCurFightPropMap(avatar.getFightProperties())
                        .build();

        this.setData(proto);
    }
}
