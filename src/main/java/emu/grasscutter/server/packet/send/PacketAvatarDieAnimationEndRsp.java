package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AvatarDieAnimationEndRspOuterClass.AvatarDieAnimationEndRsp;

public class PacketAvatarDieAnimationEndRsp extends BasePacket {

    public PacketAvatarDieAnimationEndRsp(long dieGuid, int skillId) {
        super(PacketOpcodes.AvatarDieAnimationEndRsp);

        AvatarDieAnimationEndRsp proto =
                AvatarDieAnimationEndRsp.newBuilder().setDieGuid(dieGuid).setSkillId(skillId).build();

        this.setData(proto);
    }
}
