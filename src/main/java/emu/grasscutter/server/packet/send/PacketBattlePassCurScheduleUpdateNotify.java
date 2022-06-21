package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.*;

import java.util.ArrayList;
import java.util.List;

public class PacketBattlePassCurScheduleUpdateNotify extends BasePacket {
    public PacketBattlePassCurScheduleUpdateNotify(Player player) {
        super(PacketOpcodes.BattlePassCurScheduleUpdateNotify);

        var value = player.getBattlePassManager().getPoint();
        int level = (int) Math.floor(value / 1000d);
        var point = value - level * 1000;

        List<BattlePassRewardTagOuterClass.BattlePassRewardTag> rewardTags = new ArrayList<>();

        for (int id = 1; id <= player.getBattlePassManager().getAwardTakenLevel(); id++)
            rewardTags.add(BattlePassRewardTagOuterClass.BattlePassRewardTag.newBuilder()
                    .setLevel(id)
                    .setUnlockStatus(BattlePassUnlockStatusOuterClass.BattlePassUnlockStatus.BATTLE_PASS_UNLOCK_STATUS_FREE)
                    .setRewardId(1001000 + id)
                    .build());

        var curSchedule
                = BattlePassScheduleOuterClass.BattlePassSchedule.newBuilder()
                .setScheduleId(2700).setLevel(level).setPoint(point).setBeginTime(1653940800).setEndTime(2059483200).addAllRewardTakenList(rewardTags)
                .setIsViewed(true).setUnlockStatus(BattlePassUnlockStatusOuterClass.BattlePassUnlockStatus.BATTLE_PASS_UNLOCK_STATUS_FREE).setCurCyclePoints(0)
                .setCurCycle(BattlePassCycleOuterClass.BattlePassCycle.newBuilder().setBeginTime(1653940800).setEndTime(2059483200).setCycleIdx(3).build());

        var proto = BattlePassCurScheduleUpdateNotifyOuterClass.BattlePassCurScheduleUpdateNotify.newBuilder();

        proto.setHaveCurSchedule(true).setCurSchedule(curSchedule).build();

        setData(proto.build());

    }
}
