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

import java.util.HashSet;
import java.util.List;
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
            }
            return null;
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
}
