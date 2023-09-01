package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.UnlockAvatarTalentRspOuterClass.UnlockAvatarTalentRsp;

public class PacketUnlockAvatarTalentRsp extends BasePacket {

    public PacketUnlockAvatarTalentRsp(Avatar avatar, int talentId) {
        super(PacketOpcodes.UnlockAvatarTalentRsp);

        UnlockAvatarTalentRsp proto =
                UnlockAvatarTalentRsp.newBuilder()
                        .setAvatarGuid(avatar.getGuid())
                        .setTalentId(talentId)
                        .build();

        this.setData(proto);
    }
}
