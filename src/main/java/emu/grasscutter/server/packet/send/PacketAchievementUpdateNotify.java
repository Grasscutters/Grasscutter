package emu.grasscutter.server.packet.send;

import emu.grasscutter.data.GameData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.PlayerAchievementInfo;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AchievementOuterClass.Achievement;
import emu.grasscutter.net.proto.AchievementUpdateNotifyOuterClass.AchievementUpdateNotify;

import java.util.ArrayList;
import java.util.List;

public class PacketAchievementUpdateNotify extends BasePacket {

    public PacketAchievementUpdateNotify(Player player, PlayerAchievementInfo achievementInfo) {
        super(PacketOpcodes.AchievementUpdateNotify);

        List<Achievement> a_list = new ArrayList<>();

        var achievementExcelInfo = GameData.getAchievementDataIdMap().get(achievementInfo.getAchievementId());
        var achievementToAdd = Achievement.newBuilder()
                .setId(achievementInfo.getAchievementId())
                .setStatusValue(achievementInfo.getStatusValue())
                .setTotalProgress(achievementExcelInfo.getProgress())
                .setFinishTimestamp(achievementInfo.getFinishedDate());
        if(!achievementInfo.getFinished()){
            achievementToAdd.setCurProgress(achievementInfo.getCurrentProgress());
        }else{
            List<Integer> removeWatchers = new ArrayList<>();
            removeWatchers.add(achievementInfo.getAchievementId());
            player.sendPacket(new PacketWatcherChangeNotify(new ArrayList<>(), removeWatchers));
        }
        a_list.add(achievementToAdd.build());

        AchievementUpdateNotify proto = AchievementUpdateNotify.newBuilder()
                .addAllAchievementList(a_list)
                .build();
        this.setData(proto);
    }
}
