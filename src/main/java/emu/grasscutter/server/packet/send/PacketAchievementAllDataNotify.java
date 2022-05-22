package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AchievementAllDataNotifyOuterClass.AchievementAllDataNotify;
import emu.grasscutter.net.proto.AchievementOuterClass.Achievement;
import emu.grasscutter.server.game.GameSession;

import java.util.ArrayList;
import java.util.List;

public class PacketAchievementAllDataNotify extends BasePacket {

    public PacketAchievementAllDataNotify(GameSession session) {
        super(PacketOpcodes.AchievementAllDataNotify);

        List<Achievement> a_list = new ArrayList<>();


        session.getPlayer().getAchievementManager().getAchievementInfoProperties().forEach((achievementId, achievement) -> {
            if(!achievement.getStatusHidden()) {
                a_list.add(Achievement.newBuilder()
                        .setId(achievementId)
                        .setStatusValue(achievement.getStatusValue())
                        .setCurProgress(achievement.getCurrentProgress())
                        .setTotalProgress(achievement.getTotalProgress())
                        .setFinishTimestamp(achievement.getFinishedDate())
                        .build());
            }
        });

        AchievementAllDataNotify proto = AchievementAllDataNotify.newBuilder()
                .addAllAchievementList(a_list)
                .build();
        this.setData(proto);
    }

}