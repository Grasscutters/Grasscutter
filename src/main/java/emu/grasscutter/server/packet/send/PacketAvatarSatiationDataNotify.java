package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AvatarSatiationDataNotifyOuterClass.AvatarSatiationDataNotify;
import emu.grasscutter.net.proto.AvatarSatiationDataOuterClass.AvatarSatiationData;

public class PacketAvatarSatiationDataNotify extends BasePacket {

    public PacketAvatarSatiationDataNotify(Avatar avatar, float finishTime, long penaltyTime) {
        super(PacketOpcodes.AvatarSatiationDataNotify);

        AvatarSatiationData.Builder avatarSatiation =
                AvatarSatiationData.newBuilder().setAvatarGuid(avatar.getGuid()).setFinishTime(finishTime);

        // Penalty for overeating
        if (penaltyTime > 0) {
            avatarSatiation.setPenaltyFinishTime(penaltyTime);
        }

        avatarSatiation.build();

        AvatarSatiationDataNotify notify =
                AvatarSatiationDataNotify.newBuilder().addSatiationDataList(0, avatarSatiation).build();

        this.setData(notify);
    }

    public PacketAvatarSatiationDataNotify(float time, Avatar avatar) {
        super(PacketOpcodes.AvatarSatiationDataNotify);

        var avatarSatiation =
                AvatarSatiationData.newBuilder()
                        .setAvatarGuid(avatar.getGuid())
                        .setFinishTime(time + (avatar.getSatiation() / 30f))
                        // Penalty time always ends before finish time
                        .setPenaltyFinishTime(time + (avatar.getSatiationPenalty() / 100f))
                        .build();

        AvatarSatiationDataNotify notify =
                AvatarSatiationDataNotify.newBuilder().addSatiationDataList(0, avatarSatiation).build();

        this.setData(notify);
    }
}
