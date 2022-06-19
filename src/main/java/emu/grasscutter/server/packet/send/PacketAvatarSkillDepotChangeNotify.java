package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarSkillDepotChangeNotifyOuterClass.AvatarSkillDepotChangeNotify;

public class PacketAvatarSkillDepotChangeNotify extends BasePacket {

    public PacketAvatarSkillDepotChangeNotify(Avatar avatar) {
        super(PacketOpcodes.AvatarSkillDepotChangeNotify);

        AvatarSkillDepotChangeNotify proto = AvatarSkillDepotChangeNotify.newBuilder()
            .setAvatarGuid(avatar.getGuid())
            .setEntityId(avatar.getEntityId())
            .setSkillDepotId(avatar.getSkillDepotId())
            .setCoreProudSkillLevel(avatar.getCoreProudSkillLevel())
            .addAllTalentIdList(avatar.getTalentIdList())
            .addAllProudSkillList(avatar.getProudSkillList())
            .putAllSkillLevelMap(avatar.getSkillLevelMap())
            .putAllProudSkillExtraLevelMap(avatar.getProudSkillBonusMap())
            .build();

        this.setData(proto);
    }
}
