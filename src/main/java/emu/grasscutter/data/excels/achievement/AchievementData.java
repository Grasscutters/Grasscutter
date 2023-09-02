package emu.grasscutter.data.excels.achievement;

import com.github.davidmoten.guavamini.Lists;
import emu.grasscutter.data.*;
import emu.grasscutter.data.excels.BattlePassMissionData;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
@ResourceType(name = "AchievementExcelConfigData.json")
public class AchievementData extends GameResource {
    private static final AtomicBoolean isDivided = new AtomicBoolean();
    private int goalId;
    private int preStageAchievementId;
    private Set<Integer> groupAchievementIdList = new HashSet<>();
    private boolean isParent;
    private long titleTextMapHash;
    private long descTextMapHash;
    private int finishRewardId;
    private boolean isDeleteWatcherAfterFinish;
    private int id;
    private BattlePassMissionData.TriggerConfig triggerConfig;
    private int progress;
    private boolean isDisuse;

    public static void divideIntoGroups() {
        if (isDivided.get()) {
            return;
        }

        isDivided.set(true);
        var map = GameData.getAchievementDataMap();
        var achievementDataList = map.values().stream().filter(AchievementData::isUsed).toList();
        for (var data : achievementDataList) {
            if (!data.hasPreStageAchievement() || data.hasGroupAchievements()) {
                continue;
            }

            List<Integer> ids = Lists.newArrayList();
            int parentId = data.getId();
            while (true) {
                var next = map.get(parentId + 1);
                if (next == null || parentId != next.getPreStageAchievementId()) {
                    break;
                }

                parentId++;
            }

            map.get(parentId).isParent = true;

            while (true) {
                ids.add(parentId);
                var previous = map.get(--parentId);
                if (previous == null) {
                    break;
                } else if (!previous.hasPreStageAchievement()) {
                    ids.add(parentId);
                    break;
                }
            }

            for (int i : ids) {
                map.get(i).groupAchievementIdList.addAll(ids);
            }
        }

        map.values().stream()
                .filter(a -> !a.hasGroupAchievements() && a.isUsed())
                .forEach(a -> a.isParent = true);
    }

    public boolean hasPreStageAchievement() {
        return this.preStageAchievementId != 0;
    }

    public boolean hasGroupAchievements() {
        return !this.groupAchievementIdList.isEmpty();
    }

    public boolean isUsed() {
        return !this.isDisuse;
    }

    public Set<Integer> getGroupAchievementIdList() {
        return this.groupAchievementIdList.stream().collect(Collectors.toUnmodifiableSet());
    }

    public Set<Integer> getExcludedGroupAchievementIdList() {
        return this.groupAchievementIdList.stream()
                .filter(integer -> integer != this.getId())
                .collect(Collectors.toUnmodifiableSet());
    }
}
