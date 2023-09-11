package emu.grasscutter.game.home.suite;

import dev.morphia.annotations.Entity;
import emu.grasscutter.game.home.HomeMarkPointProtoFactory;
import emu.grasscutter.game.home.SpecialFurnitureType;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.net.proto.HomeFurnitureSuiteDataOuterClass;
import emu.grasscutter.net.proto.HomeMarkPointFurnitureDataOuterClass;
import emu.grasscutter.net.proto.HomeMarkPointSuiteDataOuterClass;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.Nullable;

@Entity
@Builder(builderMethodName = "of")
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HomeSuiteItem implements HomeMarkPointProtoFactory {
    public static final int SUITE_FURNITURE_ID = 377101;
    int guid;
    int suiteId;
    Position pos;
    List<Integer> includedFurnitureIndexList;
    boolean isAllowSummon;

    public static HomeSuiteItem parseFrom(
            HomeFurnitureSuiteDataOuterClass.HomeFurnitureSuiteData data) {
        return HomeSuiteItem.of()
                .guid(data.getGuid())
                .suiteId(data.getSuiteId())
                .pos(new Position(data.getSpawnPos()))
                .includedFurnitureIndexList(data.getIncludedFurnitureIndexListList())
                .isAllowSummon(data.getIsAllowSummon())
                .build();
    }

    public HomeFurnitureSuiteDataOuterClass.HomeFurnitureSuiteData toProto() {
        return HomeFurnitureSuiteDataOuterClass.HomeFurnitureSuiteData.newBuilder()
                .setSuiteId(this.suiteId)
                .setGuid(this.guid)
                .setIsAllowSummon(this.isAllowSummon)
                .addAllIncludedFurnitureIndexList(this.includedFurnitureIndexList)
                .setSpawnPos(this.pos.toProto())
                .build();
    }

    @Nullable @Override
    public HomeMarkPointFurnitureDataOuterClass.HomeMarkPointFurnitureData toMarkPointProto() {
        return HomeMarkPointFurnitureDataOuterClass.HomeMarkPointFurnitureData.newBuilder()
                .setFurnitureId(SUITE_FURNITURE_ID)
                .setPos(this.pos.toProto())
                .setFurnitureType(this.getType().getValue())
                .setGuid(this.guid)
                .setSuiteData(
                        HomeMarkPointSuiteDataOuterClass.HomeMarkPointSuiteData.newBuilder()
                                .setSuiteId(this.suiteId)
                                .build())
                .build();
    }

    @Override
    public SpecialFurnitureType getType() {
        return SpecialFurnitureType.FurnitureSuite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HomeSuiteItem that = (HomeSuiteItem) o;
        return suiteId == that.suiteId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suiteId);
    }
}
