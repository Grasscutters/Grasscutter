package emu.grasscutter.game.achievement;

public class AchievementControlReturns {
    private final Return ret;
    private final int changedAchievementStateNum;

    private AchievementControlReturns(Return ret) {
        this(ret, 0);
    }

    private AchievementControlReturns(Return ret, int changedAchievementStateNum) {
        this.ret = ret;
        this.changedAchievementStateNum = changedAchievementStateNum;
    }

    public static AchievementControlReturns success(int achievementStateChangedNum) {
        return new AchievementControlReturns(Return.SUCCESS, achievementStateChangedNum);
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

    public Return getRet() {
        return this.ret;
    }

    public int getChangedAchievementStateNum() {
        return this.changedAchievementStateNum;
    }

    public enum Return {
        SUCCESS("commands.achievement.success."),
        ACHIEVEMENT_NOT_FOUND("commands.achievement.fail.achievement_not_found"),
        ALREADY_ACHIEVED("commands.achievement.fail.already_achieved"),
        NOT_YET_ACHIEVED("commands.achievement.fail.not_yet_achieved");

        private final String key;

        Return(String key) {
            this.key = key;
        }

        public String getKey() {
            return this.key;
        }
    }
}
