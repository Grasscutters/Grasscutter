package emu.grasscutter.game.battlepass;

import dev.morphia.annotations.*;
import emu.grasscutter.data.excels.BattlePassMissionData;
import emu.grasscutter.net.proto.BattlePassRewardTagOuterClass.BattlePassRewardTag;
import emu.grasscutter.net.proto.BattlePassUnlockStatusOuterClass.BattlePassUnlockStatus;
import lombok.Getter;

@Entity
public class BattlePassReward {
    @Getter private int level;
    @Getter private int rewardId;
    @Getter private boolean paid;

    @Transient private BattlePassMissionData data;

    @Deprecated // Morphia only
    public BattlePassReward() {}

    public BattlePassReward(int level, int rewardId, boolean paid) {
        this.level = level;
        this.rewardId = rewardId;
        this.paid = paid;
    }

    public BattlePassRewardTag toProto() {
        var protoBuilder = BattlePassRewardTag.newBuilder();

        protoBuilder
                .setLevel(this.getLevel())
                .setRewardId(this.getRewardId())
                .setUnlockStatus(
                        this.isPaid()
                                ? BattlePassUnlockStatus.BATTLE_PASS_UNLOCK_STATUS_PAID
                                : BattlePassUnlockStatus.BATTLE_PASS_UNLOCK_STATUS_FREE);

        return protoBuilder.build();
    }
}
