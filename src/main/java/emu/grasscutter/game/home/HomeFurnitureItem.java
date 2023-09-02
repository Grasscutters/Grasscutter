package emu.grasscutter.game.home;

import dev.morphia.annotations.Entity;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.HomeworldDefaultSaveData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.net.proto.*;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.Nullable;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(builderMethodName = "of")
public class HomeFurnitureItem implements HomeMarkPointProtoFactory {
    public static final int PAIMON_FURNITURE_ID = 368134;
    public static final int TELEPORT_FURNITURE_ID = 373501;
    public static final Set<Integer> APARTMENT_FURNITURE_ID_SET =
            GameData.getItemDataMap().values().stream()
                    .filter(itemData -> itemData.getSpecialFurnitureType() == SpecialFurnitureType.Apartment)
                    .map(ItemData::getId)
                    .collect(Collectors.toUnmodifiableSet());

    int furnitureId;
    int guid;
    int parentFurnitureIndex;
    Position spawnPos;
    Position spawnRot;
    int version;

    public static HomeFurnitureItem parseFrom(
            HomeFurnitureDataOuterClass.HomeFurnitureData homeFurnitureData) {
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
                .spawnRot(new Position())
                .build();
    }

    public HomeFurnitureDataOuterClass.HomeFurnitureData toProto() {
        return HomeFurnitureDataOuterClass.HomeFurnitureData.newBuilder()
                .setFurnitureId(furnitureId)
                .setGuid(guid)
                .setParentFurnitureIndex(parentFurnitureIndex)
                .setSpawnPos(spawnPos.toProto())
                .setSpawnRot(spawnRot.toProto())
                .setVersion(version)
                .build();
    }

    public ItemData getAsItem() {
        return this.furnitureId == 0 ? null : GameData.getItemDataMap().get(this.furnitureId);
    }

    public int getComfort() {
        var item = getAsItem();

        if (item == null) {
            return 0;
        }
        return item.getComfort();
    }

    @Nullable @Override
    public HomeMarkPointFurnitureDataOuterClass.HomeMarkPointFurnitureData toMarkPointProto() {
        var type = this.adjustByFurnitureId();
        if (type == SpecialFurnitureType.NOT_SPECIAL) {
            return null;
        }

        return HomeMarkPointFurnitureDataOuterClass.HomeMarkPointFurnitureData.newBuilder()
                .setFurnitureId(this.furnitureId)
                .setFurnitureType(type.getValue())
                .setPos(this.spawnPos.toProto())
                .setGuid(this.guid)
                .build();
    }

    @Override
    public SpecialFurnitureType adjustByFurnitureId() {
        return switch (this.furnitureId) {
            case PAIMON_FURNITURE_ID -> SpecialFurnitureType.Paimon;
            case TELEPORT_FURNITURE_ID -> SpecialFurnitureType.TeleportPoint;
            default -> APARTMENT_FURNITURE_ID_SET.contains(this.furnitureId)
                    ? SpecialFurnitureType.Apartment
                    : SpecialFurnitureType.NOT_SPECIAL;
        };
    }
}
