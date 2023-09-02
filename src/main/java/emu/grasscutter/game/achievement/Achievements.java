package emu.grasscutter.game.achievement;

import com.github.davidmoten.guavamini.Lists;
import dev.morphia.annotations.*;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.achievement.AchievementData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.proto.AchievementOuterClass.Achievement.Status;
import emu.grasscutter.server.event.player.PlayerCompleteAchievementEvent;
import emu.grasscutter.server.packet.send.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntSupplier;
import javax.annotation.Nullable;
import lombok.*;
import org.bson.types.ObjectId;

@Entity("achievements")
@Data
@Builder(builderMethodName = "of")
public class Achievements {
    private static final IntSupplier currentTimeSecs =
            () -> (int) (System.currentTimeMillis() / 1000L);
    private static final Achievement INVALID = new Achievement(Status.STATUS_INVALID, -1, 0, 0, 0);
    @Id private ObjectId id;
    private int uid;
    @Transient private Player player;
    private Map<Integer, Achievement> achievementList;
    @Getter private int finishedAchievementNum;
    private List<Integer> takenGoalRewardIdList;

    public static Achievements getByPlayer(Player player) {
        var achievements =
                player.getAchievements() == null
                        ? DatabaseHelper.getAchievementData(player.getUid())
                        : player.getAchievements();
        if (achievements == null) {
            achievements = create(player.getUid());
        }
        return achievements;
    }

    public static Achievements create(int uid) {
        var newAchievement =
                Achievements.of()
                        .uid(uid)
                        .achievementList(init())
                        .finishedAchievementNum(0)
                        .takenGoalRewardIdList(Lists.newArrayList())
                        .build();
        newAchievement.save();
        return newAchievement;
    }

    private static Map<Integer, Achievement> init() {
        Map<Integer, Achievement> map = new HashMap<>();
        GameData.getAchievementDataMap().values().stream()
                .filter(AchievementData::isUsed)
                .forEach(
                        a -> {
                            map.put(
                                    a.getId(),
                                    new Achievement(Status.STATUS_UNFINISHED, a.getId(), a.getProgress(), 0, 0));
                        });
        return map;
    }

    public AchievementControlReturns grant(int achievementId) {
        var a = this.getAchievement(achievementId);

        if (a == null || this.isFinished(achievementId)) {
            return a == null
                    ? AchievementControlReturns.achievementNotFound()
                    : AchievementControlReturns.alreadyAchieved();
        }

        return this.progress(achievementId, a.getTotalProgress());
    }

    public AchievementControlReturns revoke(int achievementId) {
        var a = this.getAchievement(achievementId);

        if (a == null || !this.isFinished(achievementId)) {
            return a == null
                    ? AchievementControlReturns.achievementNotFound()
                    : AchievementControlReturns.notYetAchieved();
        }

        return this.progress(achievementId, 0);
    }

    public AchievementControlReturns progress(int achievementId, int progress) {
        var a = this.getAchievement(achievementId);
        if (a == null) {
            return AchievementControlReturns.achievementNotFound();
        }

        a.setCurProgress(progress);
        return AchievementControlReturns.success(this.notifyOtherAchievements(a));
    }

    private int notifyOtherAchievements(Achievement a) {
        var changedNum = new AtomicInteger();

        changedNum.addAndGet(this.update(a) ? 1 : 0);

        GameData.getAchievementDataMap().get(a.getId()).getExcludedGroupAchievementIdList().stream()
                .map(this::getAchievement)
                .filter(Objects::nonNull)
                .forEach(
                        other -> {
                            other.setCurProgress(a.getCurProgress());
                            changedNum.addAndGet(this.update(other) ? 1 : 0);
                        });

        this.computeFinishedAchievementNum();
        this.save();
        this.sendUpdatePacket(a);
        return changedNum.intValue();
    }

    private boolean update(Achievement a) {
        if (a.getStatus() == Status.STATUS_UNFINISHED && a.getCurProgress() >= a.getTotalProgress()) {
            a.setStatus(Status.STATUS_FINISHED);
            a.setFinishTimestampSec(currentTimeSecs.getAsInt());

            // Call PlayerCompleteAchievementEvent.
            new PlayerCompleteAchievementEvent(this.player, a).call();

            return true;
        } else if (this.isFinished(a.getId()) && a.getCurProgress() < a.getTotalProgress()) {
            a.setStatus(Status.STATUS_UNFINISHED);
            a.setFinishTimestampSec(0);
            return true;
        }

        return false;
    }

    private void computeFinishedAchievementNum() {
        this.finishedAchievementNum =
                GameData.getAchievementDataMap().values().stream()
                        .filter(a -> this.isFinished(a.getId()))
                        .mapToInt(value -> 1)
                        .sum();
    }

    private void sendUpdatePacket(Achievement achievement) {
        List<Achievement> achievements = Lists.newArrayList(achievement);
        achievements.addAll(
                GameData.getAchievementDataMap()
                        .get(achievement.getId())
                        .getExcludedGroupAchievementIdList()
                        .stream()
                        .map(this::getAchievement)
                        .filter(Objects::nonNull)
                        .toList());

        this.sendUpdatePacket(achievements);
    }

    private void sendUpdatePacket(List<Achievement> achievement) {
        if (this.isPacketSendable()) {
            this.player.sendPacket(new PacketAchievementUpdateNotify(achievement));
        }
    }

    @Nullable public Achievement getAchievement(int achievementId) {
        if (this.isInvalid(achievementId)) {
            return null;
        }

        return this.getAchievementList()
                .computeIfAbsent(
                        achievementId,
                        id -> {
                            return new Achievement(
                                    Status.STATUS_UNFINISHED,
                                    id,
                                    GameData.getAchievementDataMap().get(id.intValue()).getProgress(),
                                    0,
                                    0);
                        });
    }

    public boolean isInvalid(int achievementId) {
        var data = GameData.getAchievementDataMap().get(achievementId);
        return data == null || data.isDisuse();
    }

    public Status getStatus(int achievementId) {
        return this.getAchievementList().getOrDefault(achievementId, INVALID).getStatus();
    }

    public boolean isFinished(int achievementId) {
        var status = this.getStatus(achievementId);
        return status == Status.STATUS_FINISHED || status == Status.STATUS_REWARD_TAKEN;
    }

    public void takeReward(List<Integer> ids) {
        List<GameItem> rewards = Lists.newArrayList();

        for (int i : ids) {
            var target = GameData.getAchievementDataMap().get(i);
            if (target == null) {
                Grasscutter.getLogger().warn("null returned while taking reward!");
                return;
            }

            if (this.isRewardTaken(i)) {
                this.player.sendPacket(new PacketTakeAchievementRewardRsp());
                return;
            }

            var data = GameData.getRewardDataMap().get(target.getFinishRewardId());
            if (data == null) {
                Grasscutter.getLogger().warn("null returned while getting reward data!");
                continue;
            }

            data.getRewardItemList()
                    .forEach(
                            itemParamData -> {
                                var itemData = GameData.getItemDataMap().get(itemParamData.getId());
                                if (itemData == null) {
                                    Grasscutter.getLogger().warn("itemData == null!");
                                    return;
                                }

                                rewards.add(new GameItem(itemData, itemParamData.getCount()));
                            });

            var a = this.getAchievement(i);
            a.setStatus(Status.STATUS_REWARD_TAKEN);
            this.save();
            this.sendUpdatePacket(a);
        }

        this.player.getInventory().addItems(rewards, ActionReason.AchievementReward);
        this.player.sendPacket(
                new PacketTakeAchievementRewardRsp(
                        ids, rewards.stream().map(GameItem::toItemParam).toList()));
    }

    public void takeGoalReward(List<Integer> ids) {
        List<GameItem> rewards = Lists.newArrayList();

        for (int i : ids) {
            if (this.takenGoalRewardIdList.contains(i)) {
                this.player.sendPacket(new PacketTakeAchievementGoalRewardRsp());
            }

            var goalData = GameData.getAchievementGoalDataMap().get(i);
            if (goalData == null) {
                Grasscutter.getLogger().warn("null returned while getting goal reward data!");
                continue;
            }

            var data = GameData.getRewardDataMap().get(goalData.getFinishRewardId());
            if (data == null) {
                Grasscutter.getLogger().warn("null returned while getting reward data!");
                continue;
            }

            data.getRewardItemList()
                    .forEach(
                            itemParamData -> {
                                var itemData = GameData.getItemDataMap().get(itemParamData.getId());
                                if (itemData == null) {
                                    Grasscutter.getLogger().warn("itemData == null!");
                                    return;
                                }

                                rewards.add(new GameItem(itemData, itemParamData.getCount()));
                            });

            this.takenGoalRewardIdList.add(i);
            this.save();
        }

        this.player.getInventory().addItems(rewards, ActionReason.AchievementGoalReward);
        this.player.sendPacket(
                new PacketTakeAchievementGoalRewardRsp(
                        ids, rewards.stream().map(GameItem::toItemParam).toList()));
    }

    public boolean isRewardTaken(int achievementId) {
        return this.getStatus(achievementId) == Status.STATUS_REWARD_TAKEN;
    }

    public boolean isRewardLeft(int achievementId) {
        return this.getStatus(achievementId) == Status.STATUS_FINISHED;
    }

    private boolean isPacketSendable() {
        return this.player != null;
    }

    public void save() {
        DatabaseHelper.saveAchievementData(this);
    }

    public void onLogin(Player player) {
        if (this.player == null) {
            this.player = player;
        }

        this.registerNewAchievementsIfExist();
        this.player.sendPacket(new PacketAchievementAllDataNotify(this.player));
    }

    private void registerNewAchievementsIfExist() {
        GameData.getAchievementDataMap().values().stream()
                .filter(AchievementData::isUsed)
                .filter(a -> !this.achievementList.containsKey(a.getId()))
                .forEach(
                        a -> {
                            Grasscutter.getLogger().trace("Registering a new achievement (id: {})", a.getId());
                            this.achievementList.put(
                                    a.getId(),
                                    new Achievement(Status.STATUS_UNFINISHED, a.getId(), a.getProgress(), 0, 0));
                        });
        this.save();
    }
}
