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
import it.unimi.dsi.fastutil.ints.IntSet;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Entity(value = "dailytasks", useDiscriminator = false)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(builderMethodName = "of")
public class DailyTaskManager {
    @Id ObjectId id;
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

    private List<DailyTask> chooseDailyTasks(int cityId, Random random) {
        var list = GameData.getDailyTaskDataMap().int2ObjectEntrySet().stream()
            .filter(e -> e.getIntKey() == cityId)
            .map(e -> new DailyTask(getRewardId(e.getValue().getTaskRewardId()), e.getValue().getId(), e.getValue().getFinishProgress(), 0))
            .toList();

        return chooseRandom(list, 4, random);
    }

    private static <E> List<E> chooseRandom(Collection<E> list, int pullSize, Random random) {
        int size = list.size();
        if (pullSize < 0 || size < pullSize) {
            pullSize = 0;
        }
        List<E> result = new ArrayList<>(pullSize);
        List<E> copied = new ArrayList<>(list);
        for (int i = 0; i < pullSize; ++i) {
            int j = (int) (random.nextDouble() * (size - i));
            result.add(copied.get(j));
            copied.set(j, copied.get(size - i - 1));
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
