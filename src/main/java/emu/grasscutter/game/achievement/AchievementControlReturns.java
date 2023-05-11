package emu.grasscutter.game.achievement;

import lombok.Getter;

@Getter
public class AchievementControlReturns {
    private final Return ret;
    private final int changedAchievementStatusNum;

    private AchievementControlReturns(Return ret) {
        this(ret, 0);
    }

    private AchievementControlReturns(Return ret, int changedAchievementStatusNum) {
        this.ret = ret;
        this.changedAchievementStatusNum = changedAchievementStatusNum;
    }

    public static AchievementControlReturns success(int changedAchievementStatusNum) {
        return new AchievementControlReturns(Return.SUCCESS, changedAchievementStatusNum);
    }

    public static AchievementControlReturns achievementNotFound() {
        return new AchievementControlReturns(Return.ACHIEVEMENT_NOT_FOUND);
    }

    public static AchievementControlReturns alreadyAchieved() {
        return new AchievementControlReturns(Return.ALREADY_ACHIEVED);
    }

    public static AchievementControlReturns notYetAchieved() {
        return new AchievementControlReturns(Return.NOT_YET_ACHIEVED);
    }

    public enum Return {
        SUCCESS("commands.achievement.success."),
        ACHIEVEMENT_NOT_FOUND("commands.achievement.fail.achievement_not_found"),
        ALREADY_ACHIEVED("commands.achievement.fail.already_achieved"),
        NOT_YET_ACHIEVED("commands.achievement.fail.not_yet_achieved");

        @Getter private final String key;

        Return(String key) {
            this.key = key;
        }
    }
}
