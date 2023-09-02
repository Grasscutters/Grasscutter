package emu.grasscutter.game.managers;

import ch.qos.logback.classic.Logger;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.*;
import emu.grasscutter.game.city.CityInfoData;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.*;
import emu.grasscutter.game.props.*;
import emu.grasscutter.game.quest.enums.QuestContent;
import emu.grasscutter.net.proto.ChangeHpReasonOuterClass.ChangeHpReason;
import emu.grasscutter.net.proto.PropChangeReasonOuterClass.PropChangeReason;
import emu.grasscutter.server.event.player.PlayerLevelStatueEvent;
import emu.grasscutter.server.packet.send.*;
import java.util.*;

// Statue of the Seven Manager
public class SotSManager extends BasePlayerManager {

    // NOTE: Spring volume balance *1  = fight prop HP *100

    public static final int GlobalMaximumSpringVolume =
            PlayerProperty.PROP_MAX_SPRING_VOLUME.getMax();
    private final Logger logger = Grasscutter.getLogger();
    private final boolean enablePriorityHealing = false;
    private Timer autoRecoverTimer;

    public SotSManager(Player player) {
        super(player);
    }

    public boolean getIsAutoRecoveryEnabled() {
        return player.getProperty(PlayerProperty.PROP_IS_SPRING_AUTO_USE) == 1;
    }

    public void setIsAutoRecoveryEnabled(boolean enabled) {
        player.setProperty(PlayerProperty.PROP_IS_SPRING_AUTO_USE, enabled ? 1 : 0);
        player.save();
    }

    public int getAutoRecoveryPercentage() {
        return player.getProperty(PlayerProperty.PROP_SPRING_AUTO_USE_PERCENT);
    }

    public void setAutoRecoveryPercentage(int percentage) {
        player.setProperty(PlayerProperty.PROP_SPRING_AUTO_USE_PERCENT, percentage);
        player.save();
    }

    public long getLastUsed() {
        return player.getSpringLastUsed();
    }

    public void setLastUsed() {
        player.setSpringLastUsed(System.currentTimeMillis() / 1000);
        player.save();
    }

    public int getMaxVolume() {
        return player.getProperty(PlayerProperty.PROP_MAX_SPRING_VOLUME);
    }

    public void setMaxVolume(int volume) {
        player.setProperty(PlayerProperty.PROP_MAX_SPRING_VOLUME, volume);
        player.save();
    }

    public int getCurrentVolume() {
        return player.getProperty(PlayerProperty.PROP_CUR_SPRING_VOLUME);
    }

    public void setCurrentVolume(int volume) {
        player.setProperty(PlayerProperty.PROP_CUR_SPRING_VOLUME, volume);
        setLastUsed();
        player.save();
    }

    public void handleEnterTransPointRegionNotify() {
        logger.trace("Player entered statue region");
        autoRevive();
        if (autoRecoverTimer == null) {
            autoRecoverTimer = new Timer();
            autoRecoverTimer.schedule(new AutoRecoverTimerTick(), 2500, 15000);
        }
    }

    public void handleExitTransPointRegionNotify() {
        logger.trace("Player left statue region");
        if (autoRecoverTimer != null) {
            autoRecoverTimer.cancel();
            autoRecoverTimer = null;
        }
    }

    // autoRevive automatically revives all team members.
    public void autoRevive() {
        player
                .getTeamManager()
                .getActiveTeam()
                .forEach(
                        entity -> {
                            boolean isAlive = entity.isAlive();
                            if (isAlive) {
                                return;
                            }
                            logger.trace("Reviving avatar " + entity.getAvatar().getAvatarData().getName());
                            player.getTeamManager().reviveAvatar(entity.getAvatar());
                            player.getTeamManager().healAvatar(entity.getAvatar(), 30, 0);
                        });
    }

    public void checkAndHealAvatar(EntityAvatar entity) {
        int maxHP = (int) (entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP) * 100);
        int currentHP = (int) (entity.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) * 100);
        if (currentHP == maxHP) {
            return;
        }
        int targetHP = maxHP * getAutoRecoveryPercentage() / 100;

        if (targetHP > currentHP) {
            int needHP = targetHP - currentHP;
            int currentVolume = getCurrentVolume();
            if (currentVolume >= needHP) {
                // sufficient
                setCurrentVolume(currentVolume - needHP);
            } else {
                // insufficient balance
                needHP = currentVolume;
                setCurrentVolume(0);
            }
            if (needHP > 0) {
                logger.trace(
                        "Healing avatar " + entity.getAvatar().getAvatarData().getName() + " +" + needHP);
                player.getTeamManager().healAvatar(entity.getAvatar(), 0, needHP);
                player
                        .getSession()
                        .send(
                                new PacketEntityFightPropChangeReasonNotify(
                                        entity,
                                        FightProperty.FIGHT_PROP_CUR_HP,
                                        ((float) needHP / 100),
                                        List.of(3),
                                        PropChangeReason.PROP_CHANGE_REASON_STATUE_RECOVER,
                                        ChangeHpReason.CHANGE_HP_REASON_ADD_STATUE));
                player
                        .getSession()
                        .send(new PacketEntityFightPropUpdateNotify(entity, FightProperty.FIGHT_PROP_CUR_HP));
            }
        }
    }

    public void refillSpringVolume() {
        // Temporary: Max spring volume depends on level of the statues in Mondstadt and Liyue. Override
        // until we have statue level.
        // TODO: remove
        // https://genshin-impact.fandom.com/wiki/Statue_of_The_Seven#:~:text=region%20of%20Inazuma.-,Statue%20Levels,-Upon%20first%20unlocking
        setMaxVolume(8500000);
        // Temporary: Auto enable 100% statue recovery until we can adjust statue settings in game
        // TODO: remove
        setAutoRecoveryPercentage(100);
        setIsAutoRecoveryEnabled(true);

        int maxVolume = getMaxVolume();
        int currentVolume = getCurrentVolume();
        if (currentVolume < maxVolume) {
            long now = System.currentTimeMillis() / 1000;
            int secondsSinceLastUsed = (int) (now - getLastUsed());
            // 15s = 1% max volume
            int volumeRefilled = secondsSinceLastUsed * maxVolume / 15 / 100;
            logger.trace("Statue has refilled HP volume: " + volumeRefilled);
            currentVolume = Math.min(currentVolume + volumeRefilled, maxVolume);
            logger.trace("Statue remaining HP volume: " + currentVolume);
            setCurrentVolume(currentVolume);
        }
    }

    private class AutoRecoverTimerTick extends TimerTask {
        // autoRecover checks player setting to see if auto recover is enabled, and refill HP to the
        // predefined level.
        public void run() {
            refillSpringVolume();

            logger.trace(
                    "isAutoRecoveryEnabled: "
                            + getIsAutoRecoveryEnabled()
                            + "\tautoRecoverPercentage: "
                            + getAutoRecoveryPercentage());

            if (getIsAutoRecoveryEnabled()) {
                List<EntityAvatar> activeTeam = player.getTeamManager().getActiveTeam();
                // When the statue does not have enough remaining volume:
                //      Enhanced experience: Enable priority healing
                //                              The current active character will get healed first, then
                // sequential.
                //      Vanilla experience: Disable priority healing
                //                              Sequential healing based on character index.
                int priorityIndex =
                        enablePriorityHealing ? player.getTeamManager().getCurrentCharacterIndex() : -1;
                if (priorityIndex >= 0) {
                    checkAndHealAvatar(activeTeam.get(priorityIndex));
                }
                for (int i = 0; i < activeTeam.size(); i++) {
                    if (i != priorityIndex) {
                        checkAndHealAvatar(activeTeam.get(i));
                    }
                }
            }
        }
    }

    public CityData getCityByAreaId(int areaId) {
        return GameData.getCityDataMap().values().stream()
                .filter(city -> city.getAreaIdVec().contains(areaId))
                .findFirst()
                .orElse(null);
    }

    public CityInfoData getCityInfo(int cityId) {
        if (player.getCityInfoData() == null) player.setCityInfoData(new HashMap<>());
        var cityInfo = player.getCityInfoData().get(cityId);
        if (cityInfo == null) {
            cityInfo = new CityInfoData(cityId);
            player.getCityInfoData().put(cityId, cityInfo);
        }
        return cityInfo;
    }

    public void addCityInfo(CityInfoData cityInfoData) {
        if (player.getCityInfoData() == null) player.setCityInfoData(new HashMap<>());

        player.getCityInfoData().put(cityInfoData.getCityId(), cityInfoData);
    }

    public void levelUpSotS(int areaId, int sceneId, int itemNum) {
        if (itemNum <= 0) return;

        // search city by areaId
        var city = this.getCityByAreaId(areaId);
        if (city == null) return;
        var cityId = city.getCityId();

        // check data level up
        var cityInfo = this.getCityInfo(cityId);
        var nextStatuePromoteData = GameData.getStatuePromoteData(cityId, cityInfo.getLevel() + 1);
        if (nextStatuePromoteData == null) return;
        var nextLevelCrystal = nextStatuePromoteData.getCostItems()[0].getCount();

        // delete item from inventory
        var itemNumrequired = Math.min(itemNum, nextLevelCrystal - cityInfo.getNumCrystal());
        player
                .getInventory()
                .removeItemById(nextStatuePromoteData.getCostItems()[0].getId(), itemNumrequired);

        // update number oculi
        cityInfo.setNumCrystal(cityInfo.getNumCrystal() + itemNumrequired);

        // hanble quest
        if (itemNumrequired >= 1)
            player.getQuestManager().queueEvent(QuestContent.QUEST_CONTENT_CITY_LEVEL_UP, cityId, areaId);

        // handle oculi overflow
        if (cityInfo.getNumCrystal() >= nextLevelCrystal) {
            cityInfo.setNumCrystal(cityInfo.getNumCrystal() - nextLevelCrystal);
            cityInfo.setLevel(cityInfo.getLevel() + 1);

            // update max stamina and notify client
            player.setProperty(
                    PlayerProperty.PROP_MAX_STAMINA,
                    player.getProperty(PlayerProperty.PROP_MAX_STAMINA)
                            + nextStatuePromoteData.getStamina() * 100,
                    true);

            // Add items to inventory
            if (nextStatuePromoteData.getRewardIdList() != null) {
                for (var rewardId : nextStatuePromoteData.getRewardIdList()) {
                    RewardData rewardData = GameData.getRewardDataMap().get(rewardId);
                    if (rewardData == null) continue;

                    player
                            .getInventory()
                            .addItemParamDatas(rewardData.getRewardItemList(), ActionReason.CityLevelupReward);
                }
            }

            // unlock forcescene
            player.sendPacket(new PacketSceneForceUnlockNotify(1, true));
        }

        // update data
        this.addCityInfo(cityInfo);

        // Packets
        player.sendPacket(
                new PacketLevelupCityRsp(
                        sceneId, cityInfo.getLevel(), cityId, cityInfo.getNumCrystal(), areaId, 0));

        // Call PlayerLevelStatueEvent.
        new PlayerLevelStatueEvent(this.getPlayer(), cityInfo, sceneId, areaId);
    }
}
