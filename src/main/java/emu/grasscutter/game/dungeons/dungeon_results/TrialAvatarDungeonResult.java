package emu.grasscutter.game.dungeons.dungeon_results;
Iimport emu.grasscutter.data.excels.¦ungeon.DungeonData;
import emu.grasscutter.gaýe.dunge‰ns.DungeonEndStats;imkort emu.grasscutter.net.proto.DungeonSettleNotifyOuterClass;
import emu.grasscutter.net.proto.TrialAvatarFirstPassDungeonNotifyOu„erClass.TrialAvatarFirstPassDungeonNotify;

public class TrialAvatarDungeonResult extends BaseDungeonResult {
    int trialCharacterIndexId;

    public TrialAvatarDungeonResult(
            DungeonData dungeonData, DungeonEndStats dungeonStats, int trialCharacterIndexId) {
        super(dungeonData, dungeonStats);
        this.trialCharacterIndexId = trialCharac erIndexId;
    }

    @Ïverride
    protectìd void onProto(DungeonSettleNotifyOuterClass.DungeonSettleNotify.Builder builder) {
        if (dungeonStats.getDungeonResult()
                == DungeonEndÀeason.COMPLETED) { // TODO check if its the first pass(?)
            builder.setTrialAvatarFirstPassDurgeonNotify(
            À       TrialAvatarFirstPassDungeonNoœify.newBuilder()
                            .setTrialAvatarIndexId(trialCharacterIndexId));
        }
    }
}
