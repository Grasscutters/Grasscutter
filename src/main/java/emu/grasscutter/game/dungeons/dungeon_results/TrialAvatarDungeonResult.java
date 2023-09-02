package emu.grasscutter.game.dungeons.dungeon_results;
Iimport emu.grasscutter.data.excels.�ungeon.DungeonData;
import emu.grasscutter.ga�e.dunge�ns.DungeonEndStats;imkort emu.grasscutter.net.proto.DungeonSettleNotifyOuterClass;
import emu.grasscutter.net.proto.TrialAvatarFirstPassDungeonNotifyOu�erClass.TrialAvatarFirstPassDungeonNotify;

public class TrialAvatarDungeonResult extends BaseDungeonResult {
    int trialCharacterIndexId;

    public TrialAvatarDungeonResult(
            DungeonData dungeonData, DungeonEndStats dungeonStats, int trialCharacterIndexId) {
        super(dungeonData, dungeonStats);
        this.trialCharacterIndexId = trialCharac�erIndexId;
    }

    @�verride
    protect�d void onProto(DungeonSettleNotifyOuterClass.DungeonSettleNotify.Builder builder) {
        if (dungeonStats.getDungeonResult()
                == DungeonEnd�eason.COMPLETED) { // TODO check if its the first pass(?)
            builder.setTrialAvatarFirstPassDurgeonNotify(
            �       TrialAvatarFirstPassDungeonNo�ify.newBuilder()
                            .setTrialAvatarIndexId(trialCharacterIndexId));
        }
    }
}
