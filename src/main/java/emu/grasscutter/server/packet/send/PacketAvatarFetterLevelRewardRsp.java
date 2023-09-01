package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AvatarFetterLevelRewardRspOuterClass.AvatarFetterLevelRewardRsp;

public class PacketAvatarFetterLevelRewardRsp extends BasePacket {

    public PacketAvatarFetterLevelRewardRsp(long guid, int fetterLevel, int rewardId) {
        super(PacketOpcodes.AvatarFetterLevelRewardRsp);

        AvatarFetterLevelRewardRsp proto =
                AvatarFetterLevelRewardRsp.newBuilder()
                        .setAvatarGuid(guid)
                        .setFetterLevel(fetterLevel)
                        .setRetcode(0)
                        .setRewardId(rewardId)
                        .build();

        this.setData(proto);
    }

    public PacketAvatarFetterLevelRewardRsp(long guid, int fetterLevel) {
        super(PacketOpcodes.AvatarFetterLevelRewardRsp);

        AvatarFetterLevelRewardRsp proto =
                AvatarFetterLevelRewardRsp.newBuilder()
                        .setAvatarGuid(guid)
                        .setFetterLevel(fetterLevel)
                        .setRetcode(1)
                        .setRewardId(0)
                        .build();

        this.setData(proto);
    }
}
