package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarSkillChangeNotifyOuterClass.AvatarSkillChangeNotify;

public class PacketAvatarSkillChangeNotify extends BasePacket {

    public PacketAvatarSkillChangeNotify(Avatar avatar, int skillId, int oldLevel, int curLevel) {
        super(PacketOpcodes.AvatarSkillChangeNotify);

        AvatarSkillChangeNotify proto = AvatarSkillChangeNotify.newBuilder()
            .setAvatarGuid(avatar.getGuid())
            .setEntityId(avatar.getEntityId())
            .setSkillDepotId(avatar.getSkillDepotId())
            .setAvatarSkillId(skillId)
            .setOldLevel(oldLevel)
            .setCurLevel(curLevel)
            .build();

        this.setData(proto);
    }
}
