package emu.grasscutter.game.home;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.HomeworldDefaultSaveData;
import emu.grasscutter.net.proto.HomeSceneArrangementInfoOuterClass.HomeSceneArrangementInfo;
import emu.grasscutter.utils.Position;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Data
@Builder(builderMethodName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HomeSceneItem {
    @Id
    int sceneId;
    Map<Integer, HomeBlockItem> blockItems;
    Position bornPos;
    Position bornRot;
    Position djinnPos;
    HomeFurnitureItem mainHouse;
    int tmpVersion;

    public static HomeSceneItem parseFrom(HomeworldDefaultSaveData defaultItem, int sceneId) {
        return HomeSceneItem.of()
            .sceneId(sceneId)
            .blockItems(defaultItem.getHomeBlockLists().stream()
                .map(HomeBlockItem::parseFrom)
                .collect(Collectors.toMap(HomeBlockItem::getBlockId, y -> y)))
            .bornPos(defaultItem.getBornPos())
            .bornRot(defaultItem.getBornRot() == null ? new Position() : defaultItem.getBornRot())
            .djinnPos(defaultItem.getDjinPos() == null ? new Position() : defaultItem.getDjinPos())
            .mainHouse(defaultItem.getMainhouse() == null ? null :
                HomeFurnitureItem.parseFrom(defaultItem.getMainhouse()))
            .build();
    }

    public void update(HomeSceneArrangementInfo arrangementInfo) {
        for (var blockItem : arrangementInfo.getBlockArrangementInfoListList()) {
            var block = this.blockItems.get(blockItem.getBlockId());
            if (block == null) {
                Grasscutter.getLogger().warn("Could not found the Home Block {}", blockItem.getBlockId());
                continue;
            }
            block.update(blockItem);
            this.blockItems.put(blockItem.getBlockId(), block);
        }

        this.bornPos = new Position(arrangementInfo.getBornPos());
        this.bornRot = new Position(arrangementInfo.getBornRot());
        this.djinnPos = new Position(arrangementInfo.getDjinnPos());
        this.mainHouse = HomeFurnitureItem.parseFrom(arrangementInfo.getMainHouse());
        this.tmpVersion = arrangementInfo.getTmpVersion();
    }

    public int getRoomSceneId() {
        if (this.mainHouse == null || this.mainHouse.getAsItem() == null) {
            return 0;
        }
        return this.mainHouse.getAsItem().getRoomSceneId();
    }

    public int calComfort() {
        return this.blockItems.values().stream()
            .mapToInt(HomeBlockItem::calComfort)
            .sum();
    }

    public HomeSceneArrangementInfo toProto() {
        var proto = HomeSceneArrangementInfo.newBuilder();
        this.blockItems.values().forEach(b -> proto.addBlockArrangementInfoList(b.toProto()));

        proto.setComfortValue(this.calComfort())
            .setBornPos(this.bornPos.toProto())
            .setBornRot(this.bornRot.toProto())
            .setDjinnPos(this.djinnPos.toProto())
            .setIsSetBornPos(true)
            .setSceneId(this.sceneId)
            .setTmpVersion(this.tmpVersion);

        if (this.mainHouse != null) {
            proto.setMainHouse(this.mainHouse.toProto());
        }
        return proto.build();
    }

}
