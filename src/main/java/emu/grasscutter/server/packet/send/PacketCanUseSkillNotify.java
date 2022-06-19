package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.CanUseSkillNotifyOuterClass;

public class PacketCanUseSkillNotify extends BasePacket {

    public PacketCanUseSkillNotify(boolean canUseSkill) {
        super(PacketOpcodes.CanUseSkillNotify);

        CanUseSkillNotifyOuterClass.CanUseSkillNotify proto = CanUseSkillNotifyOuterClass.CanUseSkillNotify.newBuilder()
            .setIsCanUseSkill(canUseSkill)
            .build();

        this.setData(proto);
    }

}
