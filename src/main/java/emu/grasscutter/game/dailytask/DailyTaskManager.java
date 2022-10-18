package emu.grasscutter.game.dailytask;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.IndexOptions;
import dev.morphia.annotations.Indexed;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.DailyTaskData;
import emu.grasscutter.data.excels.DailyTaskLevelData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.PacketDailyTaskDataNotify;
import emu.grasscutter.server.packet.send.PacketDailyTaskUnlockedCitiesNotify;
import emu.grasscutter.server.packet.send.PacketWorldOwnerDailyTaskNotify;
import it.unimi.dsi.fastutil.ints.IntSet;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Entity(value = "dailytasks", useDiscriminator = false)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(builderMethodName = "of")
public class DailyTaskManager {
    @Id
    ObjectId id;
    @Indexed(options = @IndexOptions(unique = true))
    int ownerUid;
    transient Player player;
    Set<DailyTask> dailyTasks;
    Set<Integer> unlockedCities;
    int cityId;
    static final IntSet cityIds = IntSet.of(GameData.getDailyTaskDataMap().values().stream().mapToInt(DailyTaskData::getCityId).distinct().toArray());

    public static DailyTaskManager getByUid(int uid) {
        var manager = DatabaseHelper.getDailyTasksByUid(uid);
        if (manager == null) {
            manager = DailyTaskManager.of()
                .ownerUid(uid)
                .dailyTasks(new HashSet<>())
                .unlockedCities(cityIds)
                .cityId(0)
                .build();
            manager.save();
        }

        return manager;
    }

    public boolean addDailyTask(DailyTask dailyTask) {
        var added = this.getDailyTasks().add(dailyTask);

        if (added) {
            this.save();

            if (this.player != null && this.player.isOnline()) {
                this.player.getScene().broadcastPacket(new PacketWorldOwnerDailyTaskNotify(this.player));
            }
        }

        return added;
    }

    public void finishDailyTask(int taskId) {
        this.getDailyTasks().stream().filter(dailyTask -> dailyTask.getTaskId() == taskId).forEach(dailyTask -> {
            dailyTask.finish();
            if (this.player != null) {
                dailyTask.broadcastFinishPacket(this.player);
            }
        });
    }

    public void resetDailyTasks() {
        AtomicInteger min = new AtomicInteger(1);
        AtomicInteger max = new AtomicInteger(4);
        cityIds.intStream().min().ifPresent(min::set);
        cityIds.intStream().max().ifPresent(max::set);
        var random = new Random();
        var cityId = this.cityId;
        if (min.get() > cityId || max.get() < cityId) {
            cityId = random.nextInt(min.get(), max.get() + 1);
        }

        this.getDailyTasks().clear();
        this.getDailyTasks().addAll(chooseDailyTasks(cityId, random));
        this.save();

        if (this.player != null && this.player.isOnline()) {
            this.player.getScene().broadcastPacket(new PacketWorldOwnerDailyTaskNotify(this.player));
        }
    }

    public int getScoreRewardId() {
        var level = getPlayer().getLevel();
        var rewardId = 0;
        for (var entry : GameData.getDailyTaskLevelDataMap().int2ObjectEntrySet()) {
            DailyTaskLevelData dailyTaskLevelData = entry.getValue();
            if (dailyTaskLevelData.getMinPlayerLevel() <= level && level <= dailyTaskLevelData.getMaxPlayerLevel()) {
                rewardId = dailyTaskLevelData.getScorePreviewRewardId();
                break;
            }
        }

        return rewardId;
    }

    public int getRewardId(int taskRewardId) {
        var level = getPlayer().getLevel();
        var id = 1;
        var rewardId = 0;
        for (var entry : GameData.getDailyTaskLevelDataMap().int2ObjectEntrySet()) {
            DailyTaskLevelData dailyTaskLevelData = entry.getValue();
            if (dailyTaskLevelData.getMinPlayerLevel() <= level && level <= dailyTaskLevelData.getMaxPlayerLevel()) {
                id = entry.getIntKey();
                break;
            }
        }

        for (var entry : GameData.getDailyTaskRewardDataMap().int2ObjectEntrySet().stream().filter(e -> e.getIntKey() == taskRewardId).toList()) {
            var vec = entry.getValue().getDropVec();
            if (id > 0 && id <= vec.size()) {
                rewardId = vec.get(id - 1).getPreviewRewardId();
            }
        }

        return rewardId;
    }

    private Set<DailyTask> chooseDailyTasks(int cityId, Random random) {
        var list = GameData.getDailyTaskDataMap().int2ObjectEntrySet().stream()
            .filter(e -> e.getValue().getCityId() == cityId)
            .map(e -> DailyTask.create(this.player, e.getValue().getId()))
            .toList();

        var sizeToChoose = Math.min(4, list.size());
        var copy = new ArrayList<>(list);
        var result = new HashSet<DailyTask>();
        for (int i = 0; i < sizeToChoose; i++) {
            result.add(copy.remove(random.nextInt(copy.size())));
        }

        return result;
    }

    public void onPlayerLogin(Player player) {
        if (this.player == null) {
            this.player = player;
        }

        getPlayer().sendPacket(new PacketDailyTaskUnlockedCitiesNotify(getUnlockedCities()));
        var finishedNum = getDailyTasks().stream().filter(DailyTask::isFinished).toList().size();
        getPlayer().sendPacket(new PacketDailyTaskDataNotify(finishedNum, getScoreRewardId()));
    }

    public void filterCityId(int cityId) {
        this.cityId = cityId;
        this.save();
    }

    public void save() {
        DatabaseHelper.saveDailyTasks(this);
    }
}
