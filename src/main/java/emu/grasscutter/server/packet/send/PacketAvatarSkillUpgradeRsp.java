package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AvatarSkillUpgradeRspOuterClass.AvatarSkillUpgradeRsp;

public class PacketAvatarSkillUpgradeRsp extends BasePacket {

    public PacketAvatarSkillUpgradeRsp(Avatar avatar, int skillId, int oldLevel, int newLevel) {
        super(PacketOpcodes.AvatarSkillUpgradeRsp);

        AvatarSkillUpgradeRsp proto =
                AvatarSkillUpgradeRsp.newBuilder()
                        .setAvatarGuid(avatar.getGuid())
                        .setAvatarSkillId(skillId)
                        .setOldLevel(oldLevel)
                        .setCurLevel(newLevel)
                        .build();

        this.setData(proto);
    }
}
