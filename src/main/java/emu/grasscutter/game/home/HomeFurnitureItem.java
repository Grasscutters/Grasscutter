package emu.grasscutter.game.home;

import dev.morphia.annotations.Entity;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.HomeworldDefaultSaveData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.net.proto.HomeFurnitureDataOuterClass;
import emu.grasscutter.net.proto.HomeMarkPointFurnitureDataOuterClass;
import emu.grasscutter.utils.Position;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(builderMethodName = "of")
public class HomeFurnitureItem {
    int furnitureId;
    int guid;
    int parentFurnitureIndex;
    Position spawnPos;
    Position spawnRot;
    int version;

    public HomeFurnitureDataOuterClass.HomeFurnitureData toProto() {
        return HomeFurnitureDataOuterClass.HomeFurnitureData.newBuilder()
            .setFurnitureId(this.furnitureId)
            .setGuid(this.guid)
            .setParentFurnitureIndex(this.parentFurnitureIndex)
            .setSpawnPos(this.spawnPos.toProto())
            .setSpawnRot(this.spawnRot.toProto())
            .setVersion(this.version)
            .build();
    }

    public HomeMarkPointFurnitureDataOuterClass.HomeMarkPointFurnitureData toMarkPointProto(int type) {
        return HomeMarkPointFurnitureDataOuterClass.HomeMarkPointFurnitureData.newBuilder()
            .setFurnitureId(this.furnitureId)
            .setGuid(this.guid)
            .setFurnitureType(type)
            .setPos(this.spawnPos.toProto())
            // TODO NPC and farm
            .build();
    }

    public static HomeFurnitureItem parseFrom(HomeFurnitureDataOuterClass.HomeFurnitureData homeFurnitureData) {
        return HomeFurnitureItem.of()
            .furnitureId(homeFurnitureData.getFurnitureId())
            .guid(homeFurnitureData.getGuid())
            .parentFurnitureIndex(homeFurnitureData.getParentFurnitureIndex())
            .spawnPos(new Position(homeFurnitureData.getSpawnPos()))
            .spawnRot(new Position(homeFurnitureData.getSpawnRot()))
            .version(homeFurnitureData.getVersion())
            .build();
    }

    public static HomeFurnitureItem parseFrom(HomeworldDefaultSaveData.HomeFurniture homeFurniture) {
        return HomeFurnitureItem.of()
            .furnitureId(homeFurniture.getId())
            .parentFurnitureIndex(1)
            .spawnPos(homeFurniture.getPos() == null ? new Position() : homeFurniture.getPos())
            .spawnRot(homeFurniture.getRot() == null ? new Position() : homeFurniture.getRot())
            .build();
    }

    public ItemData getAsItem() {
        return GameData.getItemDataMap().get(this.furnitureId);
    }

    public int getComfort() {
        var item = this.getAsItem();

        if (item == null) {
            return 0;
        }
        return item.getComfort();
    }
}
