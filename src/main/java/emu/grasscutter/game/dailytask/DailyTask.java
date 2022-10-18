package emu.grasscutter.game.dailytask;

import dev.morphia.annotations.Entity;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.DailyTaskInfoOuterClass;
import emu.grasscutter.server.packet.send.PacketDailyTaskProgressNotify;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.annotation.Nullable;
import java.util.Objects;

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

    @Nullable
    public static DailyTask create(Player owner, int dailyTaskId) {
        var manager = owner.getDailyTaskManager();
        var task = GameData.getDailyTaskDataMap().get(dailyTaskId);
        if (task == null) {
            return null;
        }
        return new DailyTask(manager.getRewardId(task.getTaskRewardId()), dailyTaskId, task.getFinishProgress(), 0);
    }

    public void finish() {
        if (finished) {
            return;
        }

        this.finished = true;
        this.progress = this.finishProgress;
    }

    public void broadcastFinishPacket(Player owner) {
        owner.getScene().broadcastPacket(new PacketDailyTaskProgressNotify(toProto()));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyTask dailyTask = (DailyTask) o;
        return taskId == dailyTask.taskId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId);
    }
}
