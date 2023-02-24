package emu.grasscutter.game.home;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.IndexOptions;
import dev.morphia.annotations.Indexed;
import dev.morphia.annotations.Transient;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.HomeWorldLevelData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.*;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Entity(value = "homes", useDiscriminator = false)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(builderMethodName = "of")
public class GameHome {
    @Id
    String id;

    @Indexed(options = @IndexOptions(unique = true))
    long ownerUid;
    @Transient Player player;

    int level;
    int exp;
    int lastUpdatedTime;
    int nextUpdateTime;
    int storedCoin;
    int storedFetterExp;
    List<FurnitureMakeSlotItem> furnitureMakeSlotItemList;
    ConcurrentHashMap<Integer, HomeSceneItem> sceneMap;
    Set<Integer> unlockedHomeBgmList;
    int enterHomeOption;

    public void save() {
        DatabaseHelper.saveHome(this);
    }

    public static GameHome getByUid(Integer uid) {
        var home = DatabaseHelper.getHomeByUid(uid);
        if (home == null) {
            home = GameHome.create(uid);
        }
        return home;
    }

    public static GameHome create(Integer uid) {
        return GameHome.of()
                .ownerUid(uid)
                .level(1)
                .sceneMap(new ConcurrentHashMap<>())
                .unlockedHomeBgmList(new HashSet<>())
                .build();
    }

    public HomeSceneItem getHomeSceneItem(int sceneId) {
        return sceneMap.computeIfAbsent(sceneId, e -> {
            var defaultItem = GameData.getHomeworldDefaultSaveData().get(sceneId);
            if (defaultItem != null) {
                Grasscutter.getLogger().info("Set player {} home {} to initial setting", ownerUid, sceneId);
                return HomeSceneItem.parseFrom(defaultItem, sceneId);
            } else {
                // Realm res missing bricks account, use default realm data to allow main house
                defaultItem = GameData.getHomeworldDefaultSaveData().get(2001);
                return HomeSceneItem.parseFrom(defaultItem, sceneId);
            }
        });
    }

    public void onOwnerLogin(Player player) {
        if (this.player == null)
            this.player = player;
        player.getSession().send(new PacketHomeBasicInfoNotify(player, false));
        player.getSession().send(new PacketPlayerHomeCompInfoNotify(player));
        player.getSession().send(new PacketHomeComfortInfoNotify(player));
        player.getSession().send(new PacketFurnitureCurModuleArrangeCountNotify());
        player.getSession().send(new PacketHomeMarkPointNotify(player));
        player.getSession().send(new PacketHomeAllUnlockedBgmIdListNotify(player));
        checkAccumulatedResources(player);
        player.getSession().send(new PacketHomeResourceNotify(player));
    }

    // Tell the client the reward is claimed or realm unlocked
    public void onClaimReward(Player player) {
        player.getSession().send(new PacketPlayerHomeCompInfoNotify(player));
    }

    public Player getPlayer() {
        if (this.player == null)
            this.player = Grasscutter.getGameServer().getPlayerByUid((int) this.ownerUid, true);
        return this.player;
    }

    public HomeWorldLevelData getLevelData() {
        return GameData.getHomeWorldLevelDataMap().get(level);
    }

    public boolean addUnlockedHomeBgm(int homeBgmId) {
        if (!getUnlockedHomeBgmList().add(homeBgmId)) return false;

        var player = this.getPlayer();
        player.sendPacket(new PacketHomeNewUnlockedBgmIdListNotify(homeBgmId));
        player.sendPacket(new PacketHomeAllUnlockedBgmIdListNotify(player));
        save();
        return true;
    }

    public Set<Integer> getUnlockedHomeBgmList() {
        if (this.unlockedHomeBgmList == null) {
            this.unlockedHomeBgmList = new HashSet<>();
        }

        if (this.unlockedHomeBgmList.addAll(getDefaultUnlockedHomeBgmIds())) {
            save();
        }

        return this.unlockedHomeBgmList;
    }

    private Set<Integer> getDefaultUnlockedHomeBgmIds() {
        return GameData.getHomeWorldBgmDataMap().int2ObjectEntrySet().stream()
            .filter(e -> e.getValue().isDefaultUnlock())
            .map(Int2ObjectMap.Entry::getIntKey)
            .collect(Collectors.toUnmodifiableSet());
    }

    // Same as Player.java addExpDirectly
    public void addExp(Player player, int count) {
        exp += count;
        int reqExp = getExpRequired(level);

        while (exp >= reqExp && reqExp > 0) {
            exp -= reqExp;
            level += 1;
            reqExp = getExpRequired(level);

            // Update client level and exp
            player.getSession().send(new PacketHomeBasicInfoNotify(player, false));
        }

        // Update client home
        onOwnerLogin(player);
    }

    private void checkAccumulatedResources(Player player) {
        int clientTime = (int) ZonedDateTime.now().toEpochSecond();
        int owedRewards = 0;

        // Don't owe if previous update hasn't passed
        if (nextUpdateTime > clientTime) {
            return;
        }

        if (lastUpdatedTime == 0) {
            lastUpdatedTime = clientTime;
        }

        // Calculate number of owed rewards
        owedRewards = 1 + ((clientTime - nextUpdateTime) / 3600);

        // Ensure next update is at top of the hour
        nextUpdateTime = (int) ZonedDateTime.now().plusHours(1).truncatedTo(ChronoUnit.HOURS).toEpochSecond();

        // Get resources
        var hourlyResources = getComfortResources(player);
        var owedCoin = hourlyResources.get(0) * owedRewards;
        var owedFetter = hourlyResources.get(1) * owedRewards;

        // Update stored amounts
        storeResources(player, owedCoin, owedFetter);
    }

    public void takeHomeCoin(Player player) {
        player.getInventory().addItem(204, storedCoin);
        storedCoin = 0;
        save();
        player.getSession().send(new PacketHomeResourceNotify(player));
    }

    public void takeHomeFetter(Player player) {
        List<Integer> invitedAvatars = new ArrayList<>();

        // Outdoors avatars
        sceneMap.get(player.getCurrentRealmId() + 2000).getBlockItems().forEach((i, e) -> {
            e.getDeployNPCList().forEach(id -> {
                invitedAvatars.add(id.getAvatarId());
            });
        });

        // Check as realm 5 inside is not in defaults and will be null
        if (Objects.nonNull(sceneMap.get(player.getCurrentRealmId() + 2200))) {
            // Indoors avatars
            sceneMap.get(player.getCurrentRealmId() + 2200).getBlockItems().forEach((i, e) -> {
                e.getDeployNPCList().forEach(id -> {
                    invitedAvatars.add(id.getAvatarId());
                });
            });
        }

        // Add exp to all avatars
        invitedAvatars.forEach(id -> {
            var avatar = player.getAvatars().getAvatarById(id);
            player.getServer().getInventorySystem().upgradeAvatarFetterLevel(player, avatar, storedFetterExp);
        });

        storedFetterExp = 0;
        save();
        player.getSession().send(new PacketHomeResourceNotify(player));
    }

    public void updateHourlyResources(Player player) {
        int clientTime = (int) ZonedDateTime.now().toEpochSecond();

        // Check if resources can update
        if (nextUpdateTime > clientTime) {
            return;
        }

        // If no update has occurred before
        if (lastUpdatedTime == 0) {
            lastUpdatedTime = clientTime;
        }

        // Update stored resources
        storeResources(player, 0, 0);
        lastUpdatedTime = clientTime;
        nextUpdateTime = (int) ZonedDateTime.now().plusHours(1).truncatedTo(ChronoUnit.HOURS).toEpochSecond();
        save();

        // Send packet
        player.getSession().send(new PacketHomeResourceNotify(player));
    }

    public void storeResources(Player player, int owedCoin, int owedFetter) {
        // Get max values
        var maxCoin = getMaxCoin(level);
        var maxFetter = getMaxFetter(level);
        int newCoin = 0;
        int newFetter = 0;

        // Check if resources are already max
        if (storedCoin >= maxCoin && storedFetterExp >= maxFetter) {
            return;
        }

        // Get resources
        var hourlyResources = getComfortResources(player);

        // Update home coin
        if (storedCoin < maxCoin) {
            // Check if owed or hourly
            if (owedCoin == 0) {
                newCoin = storedCoin + hourlyResources.get(0);
            } else {
                newCoin = storedCoin + owedCoin;
            }
            // Ensure max is not exceeded
            storedCoin = (maxCoin >= newCoin) ? newCoin : maxCoin;
        }

        // Update fetter exp
        if (storedFetterExp < maxFetter) {
            // Check if owed or hourly
            if (owedFetter == 0) {
                newFetter = storedFetterExp + hourlyResources.get(1);
            } else {
                newFetter = storedFetterExp + owedFetter;
            }
            // Ensure max is not exceeded
            storedFetterExp = (maxFetter >= newFetter) ? newFetter : maxFetter;
        }

        save();
    }

    public List<Integer> getComfortResources(Player player) {
        List<Integer> allHomesComfort = new ArrayList<>();
        int highestComfort = 0;
        // Use HomeComfortInfoNotify data since comfort value isn't stored
        if (player.getRealmList() == null) {
            return List.of(0, 0);
        }

        // Calculate comfort value for each home
        for (int moduleId : player.getRealmList()) {
            var homeScene = player.getHome().getHomeSceneItem(moduleId + 2000);
            allHomesComfort.add(homeScene.calComfort());
        }

        // Get highest comfort value
        highestComfort = Collections.max(allHomesComfort);

        // Determine hourly resources
        if (highestComfort >= 20000) {
            return List.of(30, 5);
        } else if (highestComfort >= 15000) {
            return List.of(28, 5);
        } else if (highestComfort >= 12000) {
            return List.of(26, 5);
        } else if (highestComfort >= 10000) {
            return List.of(24, 4);
        } else if (highestComfort >= 8000) {
            return List.of(22, 4);
        } else if (highestComfort >= 6000) {
            return List.of(20, 4);
        } else if (highestComfort >= 4500) {
            return List.of(16, 3);
        } else if (highestComfort >= 3000) {
            return List.of(12, 3);
        } else if (highestComfort >= 2000) {
            return List.of(8, 2);
        } else
            return List.of(4, 2);
    }

    private int getExpRequired(int level) {
        HomeWorldLevelData levelData = GameData.getHomeWorldLevelDataMap().get(level);
        return levelData != null ? levelData.getExp() : 0;
    }

    public int getMaxCoin(int level) {
        var levelData = GameData.getHomeWorldLevelDataMap().get(level);
        return levelData != null ? levelData.getHomeCoinStoreLimit() : 0;
    }

    public int getMaxFetter(int level) {
        var levelData = GameData.getHomeWorldLevelDataMap().get(level);
        return levelData != null ? levelData.getHomeFetterExpStoreLimit() : 0;
    }
}
