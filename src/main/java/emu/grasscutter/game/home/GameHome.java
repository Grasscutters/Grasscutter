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

import java.util.ArrayList;
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
    List<Integer> unlockedHomeBgmList;

    public void save(){
        DatabaseHelper.saveHome(this);
    }

    public static GameHome getByUid(Integer uid){
        var home = DatabaseHelper.getHomeByUid(uid);
        if (home == null) {
            home = GameHome.create(uid);
        }
        return home;
    }

    public static GameHome create(Integer uid){
        return GameHome.of()
                .ownerUid(uid)
                .level(1)
                .sceneMap(new ConcurrentHashMap<>())
                .build();
    }

    public HomeSceneItem getHomeSceneItem(int sceneId) {
        return sceneMap.computeIfAbsent(sceneId, e -> {
            var defaultItem = GameData.getHomeworldDefaultSaveData().get(sceneId);
            if (defaultItem != null){
                Grasscutter.getLogger().info("Set player {} home {} to initial setting", ownerUid, sceneId);
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
        player.getSession().send(new PacketUnlockedHomeBgmNotify(player));
    }

    public HomeWorldLevelData getLevelData(){
        return GameData.getHomeWorldLevelDataMap().get(level);
    }

    public void addUnlockedHomeBgm(int homeBgmId) {
        getUnlockedHomeBgmList().add(homeBgmId);
        save();
    }

    public List<Integer> getUnlockedHomeBgmListInfo() {
        var list = getUnlockedHomeBgmList();
        if (list == null) {
            list = new ArrayList<>();
            addAllDefaultUnlockedBgms(list);
            setUnlockedHomeBgmList(list);
            save();
        }

        return list;
    }

    private void addAllDefaultUnlockedBgms(List<Integer> list) {
        list.add(105);//Pure Sky
        list.add(110);//The City Favored by Wind
        list.add(121);//Hence, Begins the Journey
        list.add(201);//Moon in One's Cup
        list.add(202);//Maiden's Longing
        list.add(225);//Liyue
    }
}
