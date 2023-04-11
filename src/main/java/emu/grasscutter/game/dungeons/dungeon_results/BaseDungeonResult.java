package emu.grasscutter.game.dungeons.dungeon_results;

import emu.grasscutter.data.excels.dungeon.DungeonData;
import emu.grasscutter.game.dungeons.DungeonEndStats;
import emu.grasscutter.net.proto.DungeonSettleNotifyOuterClass.DungeonSettleNotify;
import emu.grasscutter.net.proto.ParamListOuterClass;
import emu.grasscutter.utils.Utils;
import lombok.Getter;

public class BaseDungeonResult {
    @Getter DungeonData dungeonData;
    @Getter DungeonEndStats dungeonStats;

    public BaseDungeonResult(DungeonData dungeonData, DungeonEndStats dungeonStats) {
        this.dungeonData = dungeonData;
        this.dungeonStats = dungeonStats;
    }

    protected void onProto(DungeonSettleNotify.Builder builder) {}

    public final DungeonSettleNotify.Builder getProto() {
        var success = dungeonStats.getDungeonResult().isSuccess();
        var builder =
                DungeonSettleNotify.newBuilder()
                        .setDungeonId(dungeonData.getId())
                        .setIsSuccess(success)
                        .setCloseTime(getCloseTime())
                        .setResult(success ? 1 : 0);

        // TODO check
        if (dungeonData.getSettleShows() != null) {
            for (int i = 0; i < dungeonData.getSettleShows().size(); i++) {
                var settle = dungeonData.getSettleShows().get(i);
                builder.putSettleShow(
                        i + 1,
                        switch (settle) {
                            case SETTLE_SHOW_TIME_COST -> ParamListOuterClass.ParamList.newBuilder()
                                    .addParamList(settle.getId())
                                    .addParamList(dungeonStats.getTimeTaken())
                                    .build();
                            case SETTLE_SHOW_KILL_MONSTER_COUNT -> ParamListOuterClass.ParamList.newBuilder()
                                    .addParamList(settle.getId())
                                    .addParamList(dungeonStats.getKilledMonsters())
                                    .build();
                            default -> ParamListOuterClass.ParamList.newBuilder()
                                    .addParamList(settle.getId())
                                    .build();
                        });
            }
        }

        // TODO handle settle show

        onProto(builder);

        return builder;
    }

    public int getCloseTime() {
        return Utils.getCurrentSeconds()
                + switch (dungeonStats.getDungeonResult()) {
                    case COMPLETED -> dungeonData.getSettleCountdownTime();
                    case FAILED -> dungeonData.getFailSettleCountdownTime();
                    case QUIT -> dungeonData.getQuitSettleCountdownTime();
                };
    }

    public enum DungeonEndReason {
        COMPLETED,
        FAILED,
        QUIT;

        public boolean isSuccess() {
            return this == COMPLETED;
        }
    }
}
