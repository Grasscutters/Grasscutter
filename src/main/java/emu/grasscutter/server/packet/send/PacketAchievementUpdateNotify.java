package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.PlayerAchievementInfo;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AchievementOuterClass.Achievement;
import emu.grasscutter.net.proto.AchievementUpdateNotifyOuterClass.AchievementUpdateNotify;

import java.util.ArrayList;
import java.util.List;

public class PacketAchievementUpdateNotify extends BasePacket {

    public PacketAchievementUpdateNotify(PlayerAchievementInfo achievementInfo) {
        super(PacketOpcodes.AchievementUpdateNotify);

        List<Achievement> a_list = new ArrayList<>();

        a_list.add(Achievement.newBuilder()
                .setId(achievementInfo.getAchievementId())
                .setStatusValue(achievementInfo.getStatusValue())
                .setCurProgress(achievementInfo.getCurrentProgress())
                .setTotalProgress(achievementInfo.getTotalProgress())
                .setFinishTimestamp(achievementInfo.getFinishedDate())
                .build());

        AchievementUpdateNotify proto = AchievementUpdateNotify.newBuilder()
                .addAllAchievementList(a_list)
                .build();
        this.setData(proto);
    }
}
