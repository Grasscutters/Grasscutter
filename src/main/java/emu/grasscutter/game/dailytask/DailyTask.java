package emu.grasscutter.game.dailytask;

import dev.morphia.annotations.Entity;
import emu.grasscutter.net.proto.DailyTaskInfoOuterClass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class DailyTask {
    int rewardId;
    int taskId;
    int finishProgress;
    int progress;
    boolean finished;

    public DailyTask(int rewardId, int taskId, int finishProgress, int progress) {
        this.rewardId = rewardId;
        this.taskId = taskId;
        this.finishProgress = finishProgress;
        this.progress = progress;
    }

    public void finish() {
        if (finished) {
            return;
        }

        this.finished = true;
        this.progress = this.finishProgress;
    }

    public DailyTaskInfoOuterClass.DailyTaskInfo toProto() {
        return DailyTaskInfoOuterClass.DailyTaskInfo.newBuilder()
            .setRewardId(rewardId)
            .setDailyTaskId(taskId)
            .setFinishProgress(finishProgress)
            .setProgress(progress)
            .setIsFinished(finished)
            .build();
    }
}
