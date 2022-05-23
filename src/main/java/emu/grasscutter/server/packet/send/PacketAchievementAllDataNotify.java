package emu.grasscutter.server.packet.send;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.AchievementData;
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
        List<Integer> watchers = new ArrayList<>();

        session.getPlayer().getAchievementManager().getAchievementInfoProperties().forEach((achievementId, achievement) -> {
            var achievementExcelData = GameData.getAchievementDataIdMap().get(achievementId);
            a_list.add(Achievement.newBuilder()
                    .setId(achievementId)
                    .setStatusValue(achievement.getStatusValue())
                    .setCurProgress(achievement.getCurrentProgress())
                    .setTotalProgress(achievementExcelData.getProgress())
                    .setFinishTimestamp(achievement.getFinishedDate())
                    .build());
            if(!achievement.getFinished()) {
                if(achievementExcelData.getIsDeleteWatcherAfterFinish()){
                    watchers.add(achievementId);
                }
            }
        });

        session.send(new PacketWatcherAllDataNotify(watchers));
        AchievementAllDataNotify proto = AchievementAllDataNotify.newBuilder()
                .addAllAchievementList(a_list)
                .build();
        this.setData(proto);
    }

}