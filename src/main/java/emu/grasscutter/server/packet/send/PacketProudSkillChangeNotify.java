package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ProudSkillChangeNotifyOuterClass.ProudSkillChangeNotify;

public class PacketProudSkillChangeNotify extends BasePacket {

    public PacketProudSkillChangeNotify(Avatar avatar) {
        super(PacketOpcodes.ProudSkillChangeNotify);

        ProudSkillChangeNotify proto =
                ProudSkillChangeNotify.newBuilder()
                        .setAvatarGuid(avatar.getGuid())
                        .setEntityId(avatar.getEntityId())
                        .setSkillDepotId(avatar.getSkillDepotId())
                        .addAllProudSkillList(avatar.getProudSkillList())
                        .build();

        this.setData(proto);
    }
}
