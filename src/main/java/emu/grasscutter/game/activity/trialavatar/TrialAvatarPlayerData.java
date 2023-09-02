package emu.grasscutter.game.activity.trialavatar;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.common.BaseTrialActivityData;
import emu.grasscutter.net.proto.TrialAvatarActivityDetailInfoOuterClass.TrialAvatarActivityDetailInfo;
import emu.grasscutter.net.proto.TrialAvatarActivityRewardDetailInfoOuterClass.TrialAvatarActivityRewardDetailInfo;
import java.util.List;
import java.util.stream.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(builderMethodName = "of")
public class TrialAvatarPlayerData {
    List<RewardInfoItem> rewardInfoList;

    private static BaseTrialActivityData getActivityData(int scheduleId) {
        // prefer custom data over official data
        return GameData.getTrialAvatarActivityCustomData().isEmpty()
                ? GameData.getTrialAvatarActivityDataMap().get(scheduleId)
                : GameData.getTrialAvatarActivityCustomData().get(scheduleId);
    }

    public static List<Integer> getAvatarIdList(int scheduleId) {
        val activityData = getActivityData(scheduleId);
        return activityData != null ? activityData.getAvatarIndexIdList() : List.of();
    }

    public static List<Integer> getRewardIdList(int scheduleId) {
        val activityData = getActivityData(scheduleId);
        return activityData != null ? activityData.getRewardIdList() : List.of();
    }

    public static TrialAvatarPlayerData create(int scheduleId) {
        List<Integer> avatarIds = getAvatarIdList(scheduleId);
        List<Integer> rewardIds = getRewardIdList(scheduleId);
        return TrialAvatarPlayerData.of()
                .rewardInfoList(
                        IntStream.range(0, avatarIds.size())
                                .filter(i -> avatarIds.get(i) > 0 && rewardIds.get(i) > 0)
                                .mapToObj(i -> RewardInfoItem.create(avatarIds.get(i), rewardIds.get(i)))
                                .collect(Collectors.toList()))
                .build();
    }

    public TrialAvatarActivityDetailInfo toProto() {
        return TrialAvatarActivityDetailInfo.newBuilder()
                .addAllRewardInfoList(getRewardInfoList().stream().map(RewardInfoItem::toProto).toList())
                .build();
    }

    public RewardInfoItem getRewardInfo(int trialAvatarIndexId) {
        return getRewardInfoList().stream()
                .filter(x -> x.getTrialAvatarIndexId() == trialAvatarIndexId)
                .findFirst()
                .orElse(null);
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Builder(builderMethodName = "of")
    public static class RewardInfoItem {
        int trialAvatarIndexId;
        int rewardId;
        boolean passedDungeon;
        boolean receivedReward;

        public static RewardInfoItem create(int trialAvatarIndexId, int rewardId) {
            return RewardInfoItem.of()
                    .trialAvatarIndexId(trialAvatarIndexId)
                    .rewardId(rewardId)
                    .passedDungeon(false)
                    .receivedReward(false)
                    .build();
        }

        public TrialAvatarActivityRewardDetailInfo toProto() {
            return TrialAvatarActivityRewardDetailInfo.newBuilder()
                    .setTrialAvatarIndexId(getTrialAvatarIndexId())
                    .setRewardId(getRewardId())
                    .setPassedDungeon(isPassedDungeon())
                    .setReceivedReward(isReceivedReward())
                    .build();
        }
    }
}
