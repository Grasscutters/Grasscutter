package emu.grasscutter.game.home;

import com.github.davidmoten.guavamini.Lists;
import emu.grasscutter.game.home.suite.event.HomeAvatarRewardEvent;
import emu.grasscutter.game.home.suite.event.HomeAvatarSummonEvent;
import emu.grasscutter.game.home.suite.event.SuiteEventType;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.HomeAvatarRewardEventNotifyOuterClass;
import emu.grasscutter.net.proto.HomeAvatarSummonAllEventNotifyOuterClass;
import emu.grasscutter.net.proto.RetcodeOuterClass;
import emu.grasscutter.server.packet.send.PacketHomeAvatarSummonAllEventNotify;
import emu.grasscutter.utils.Either;
import java.util.*;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HomeModuleManager {
    final Player homeOwner;
    final HomeWorld homeWorld;
    final GameHome home;
    final int moduleId;
    final HomeScene outdoor;
    HomeScene indoor;
    final List<HomeAvatarRewardEvent> rewardEvents;
    final List<HomeAvatarSummonEvent> summonEvents;

    public HomeModuleManager(HomeWorld homeWorld) {
        this.homeOwner = homeWorld.getHost();
        this.homeWorld = homeWorld;
        this.home = homeWorld.getHome();
        this.moduleId = this.homeOwner.getCurrentRealmId();
        this.outdoor = homeWorld.getSceneById(homeWorld.getActiveOutdoorSceneId());
        this.refreshMainHouse();
        this.rewardEvents = Lists.newArrayList();
        this.summonEvents = Collections.synchronizedList(Lists.newArrayList());
    }

    public void tick() {
        if (this.moduleId == 0) {
            return;
        }

        this.outdoor.onTick();
        this.indoor.onTick();
        this.summonEvents.removeIf(HomeAvatarSummonEvent::isTimeOver);
    }

    public void refreshMainHouse() {
        if (this.moduleId == 0) {
            return;
        }

        this.indoor = this.homeWorld.getSceneById(this.homeWorld.getActiveIndoorSceneId());
    }

    public void onUpdateArrangement() {
        this.fireAllAvatarRewardEvents();
        this.cancelSummonEventsIfAvatarLeave();
    }

    private void fireAllAvatarRewardEvents() {
        this.rewardEvents.clear();
        var allBlockItems =
                Stream.of(this.getOutdoorSceneItem(), this.getIndoorSceneItem())
                        .map(HomeSceneItem::getBlockItems)
                        .map(Map::values)
                        .flatMap(Collection::stream)
                        .toList();

        var suites =
                allBlockItems.stream()
                        .map(HomeBlockItem::getSuiteList)
                        .filter(Objects::nonNull)
                        .flatMap(Collection::stream)
                        .distinct()
                        .toList();

        allBlockItems.stream()
                .map(HomeBlockItem::getDeployNPCList)
                .flatMap(Collection::stream)
                .forEach(
                        avatar -> {
                            suites.forEach(
                                    suite -> {
                                        var data =
                                                SuiteEventType.HOME_AVATAR_REWARD_EVENT.getEventDataFrom(
                                                        avatar.getAvatarId(), suite.getSuiteId());
                                        if (data == null || this.home.isRewardEventFinished(data.getId())) {
                                            return;
                                        }

                                        this.rewardEvents.add(
                                                new HomeAvatarRewardEvent(
                                                        homeOwner,
                                                        data.getId(),
                                                        data.getRewardID(),
                                                        data.getAvatarID(),
                                                        data.getSuiteId(),
                                                        suite.getGuid()));
                                    });
                        });

        if (this.summonEvents != null) {
            var suiteIdList = this.rewardEvents.stream().map(HomeAvatarRewardEvent::getSuiteId).toList();
            this.summonEvents.removeIf(event -> suiteIdList.contains(event.getSuiteId()));
        }
    }

    private void cancelSummonEventsIfAvatarLeave() {
        var avatars =
                Stream.of(this.getOutdoorSceneItem(), this.getIndoorSceneItem())
                        .map(HomeSceneItem::getBlockItems)
                        .map(Map::values)
                        .flatMap(Collection::stream)
                        .map(HomeBlockItem::getDeployNPCList)
                        .flatMap(Collection::stream)
                        .map(HomeNPCItem::getAvatarId)
                        .toList();

        this.summonEvents.removeIf(event -> !avatars.contains(event.getAvatarId()));
    }

    public Either<List<GameItem>, Integer> claimAvatarRewards(int eventId) {
        if (this.rewardEvents.isEmpty()) {
            return Either.right(RetcodeOuterClass.Retcode.RET_FAIL_VALUE);
        }

        var event = this.rewardEvents.remove(0);
        if (event.getEventId() != eventId) {
            return Either.right(RetcodeOuterClass.Retcode.RET_FAIL_VALUE);
        }

        if (!this.homeOwner.getHome().onClaimAvatarRewards(eventId)) {
            return Either.right(RetcodeOuterClass.Retcode.RET_FAIL_VALUE);
        }

        return Either.left(event.giveRewards());
    }

    public Either<HomeAvatarSummonEvent, Integer> fireAvatarSummonEvent(
            Player owner, int avatarId, int guid, int suiteId) {
        var targetSuite =
                ((HomeScene) owner.getScene())
                        .getSceneItem().getBlockItems().values().stream()
                                .map(HomeBlockItem::getSuiteList)
                                .flatMap(Collection::stream)
                                .filter(suite -> suite.getGuid() == guid)
                                .findFirst()
                                .orElse(null);

        if (this.isInRewardEvent(avatarId)) {
            return Either.right(RetcodeOuterClass.Retcode.RET_DUPLICATE_AVATAR_VALUE);
        }

        if (this.rewardEvents.stream().anyMatch(event -> event.getGuid() == guid)) {
            return Either.right(RetcodeOuterClass.Retcode.RET_HOME_FURNITURE_GUID_ERROR_VALUE);
        }

        this.summonEvents.removeIf(event -> event.getGuid() == guid || event.getAvatarId() == avatarId);

        if (targetSuite == null) {
            return Either.right(RetcodeOuterClass.Retcode.RET_HOME_CLIENT_PARAM_INVALID_VALUE);
        }

        var eventData = SuiteEventType.HOME_AVATAR_SUMMON_EVENT.getEventDataFrom(avatarId, suiteId);
        if (eventData == null) {
            return Either.right(RetcodeOuterClass.Retcode.RET_HOME_CLIENT_PARAM_INVALID_VALUE);
        }

        var event =
                new HomeAvatarSummonEvent(
                        owner, eventData.getId(), eventData.getRewardID(), avatarId, suiteId, guid);
        this.summonEvents.add(event);
        owner.sendPacket(new PacketHomeAvatarSummonAllEventNotify(owner));
        return Either.left(event);
    }

    public void onFinishSummonEvent(int eventId) {
        this.summonEvents.removeIf(event -> event.getEventId() == eventId);
    }

    public HomeAvatarRewardEventNotifyOuterClass.HomeAvatarRewardEventNotify toRewardEventProto() {
        var notify = HomeAvatarRewardEventNotifyOuterClass.HomeAvatarRewardEventNotify.newBuilder();
        if (!this.rewardEvents.isEmpty()) {
            notify.setRewardEvent(this.rewardEvents.get(0).toProto()).setIsEventTrigger(true);

            notify.addAllPendingList(
                    this.rewardEvents.subList(1, this.rewardEvents.size()).stream()
                            .map(HomeAvatarRewardEvent::toProto)
                            .toList());
        }

        return notify.build();
    }

    public HomeAvatarSummonAllEventNotifyOuterClass.HomeAvatarSummonAllEventNotify
            toSummonEventProto() {
        return HomeAvatarSummonAllEventNotifyOuterClass.HomeAvatarSummonAllEventNotify.newBuilder()
                .addAllSummonEventList(
                        this.summonEvents.stream().map(HomeAvatarSummonEvent::toProto).toList())
                .build();
    }

    public boolean isInRewardEvent(int avatarId) {
        return this.rewardEvents.stream().anyMatch(e -> e.getAvatarId() == avatarId);
    }

    public HomeSceneItem getOutdoorSceneItem() {
        return this.outdoor.getSceneItem();
    }

    public HomeSceneItem getIndoorSceneItem() {
        return this.indoor.getSceneItem();
    }

    public void onSetModule() {
        if (this.moduleId == 0) {
            return;
        }

        this.outdoor.addEntities(this.getOutdoorSceneItem().getAnimals(this.outdoor));
        this.indoor.addEntities(this.getIndoorSceneItem().getAnimals(this.indoor));
        this.fireAllAvatarRewardEvents();
    }

    public void onRemovedModule() {
        if (this.moduleId == 0) {
            return;
        }

        this.outdoor.getEntities().clear();
        this.indoor.getEntities().clear();
    }
}
