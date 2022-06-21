package emu.grasscutter.game.home;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.IndexOptions;
import dev.morphia.annotations.Indexed;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.HomeWorldLevelData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.packet.send.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Entity(value = "homes", useDiscriminator = false)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(builderMethodName = "of")
public class GameHome {

    @Id
    String id;

    @Indexed(options = @IndexOptions(unique = true))
    long ownerUid;

    int level;
    int exp;
    List<FurnitureMakeSlotItem> furnitureMakeSlotItemList;
    ConcurrentHashMap<Integer, HomeSceneItem> sceneMap;

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
            .build();
    }

    public HomeSceneItem getHomeSceneItem(int sceneId) {
        return this.sceneMap.computeIfAbsent(sceneId, e -> {
            var defaultItem = GameData.getHomeworldDefaultSaveData().get(sceneId);
            if (defaultItem != null) {
                Grasscutter.getLogger().info("Set player {} home {} to initial setting", this.ownerUid, sceneId);
                return HomeSceneItem.parseFrom(defaultItem, sceneId);
            }
            return null;
        });
    }

    public void onOwnerLogin(Player player) {
        player.getSession().send(new PacketHomeBasicInfoNotify(player, false));
        player.getSession().send(new PacketPlayerHomeCompInfoNotify(player));
        player.getSession().send(new PacketHomeComfortInfoNotify(player));
        player.getSession().send(new PacketFurnitureCurModuleArrangeCountNotify());
        player.getSession().send(new PacketHomeMarkPointNotify(player));
    }

    public HomeWorldLevelData getLevelData() {
        return GameData.getHomeWorldLevelDataMap().get(this.level);
    }
}
