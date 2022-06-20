package emu.grasscutter.game.home;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import emu.grasscutter.data.binout.HomeworldDefaultSaveData;
import emu.grasscutter.net.proto.HomeBlockArrangementInfoOuterClass.HomeBlockArrangementInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@Builder(builderMethodName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HomeBlockItem {
    @Id
    int blockId;
    boolean unlocked;
    List<HomeFurnitureItem> deployFurnitureList;
    List<HomeFurnitureItem> persistentFurnitureList;
    List<HomeAnimalItem> deployAnimalList;
    List<HomeNPCItem> deployNPCList;

    public void update(HomeBlockArrangementInfo homeBlockArrangementInfo) {
        this.blockId = homeBlockArrangementInfo.getBlockId();

        this.deployFurnitureList = homeBlockArrangementInfo.getDeployFurniureListList().stream()
            .map(HomeFurnitureItem::parseFrom)
            .toList();

        this.persistentFurnitureList = homeBlockArrangementInfo.getPersistentFurnitureListList().stream()
            .map(HomeFurnitureItem::parseFrom)
            .toList();

        this.deployAnimalList = homeBlockArrangementInfo.getDeployAnimalListList().stream()
            .map(HomeAnimalItem::parseFrom)
            .toList();

        this.deployNPCList = homeBlockArrangementInfo.getDeployNpcListList().stream()
            .map(HomeNPCItem::parseFrom)
            .toList();
    }

    public int calComfort() {
        return this.deployFurnitureList.stream()
            .mapToInt(HomeFurnitureItem::getComfort)
            .sum();
    }

    public HomeBlockArrangementInfo toProto() {
        var proto = HomeBlockArrangementInfo.newBuilder()
            .setBlockId(this.blockId)
            .setIsUnlocked(this.unlocked)
            .setComfortValue(this.calComfort());

        this.deployFurnitureList.forEach(f -> proto.addDeployFurniureList(f.toProto()));
        this.persistentFurnitureList.forEach(f -> proto.addPersistentFurnitureList(f.toProto()));
        this.deployAnimalList.forEach(f -> proto.addDeployAnimalList(f.toProto()));
        this.deployNPCList.forEach(f -> proto.addDeployNpcList(f.toProto()));

        return proto.build();
    }

    public static HomeBlockItem parseFrom(HomeworldDefaultSaveData.HomeBlock homeBlock) {
        // create from default setting
        return HomeBlockItem.of()
            .blockId(homeBlock.getBlockId())
            .unlocked(homeBlock.getFurnitures() != null)
            .deployFurnitureList(
                homeBlock.getFurnitures() == null ? List.of() :
                    homeBlock.getFurnitures().stream()
                        .map(HomeFurnitureItem::parseFrom)
                        .toList())
            .deployAnimalList(List.of())
            .deployNPCList(List.of())
            .persistentFurnitureList(List.of())
            .build();
    }
}
