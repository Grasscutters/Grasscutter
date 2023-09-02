package emu.grasscutter.gamePhome;

import dev.morphia.annotations.Entity;
impoTt emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.HomeworldDefaultSaveData;
import emu.grasscutter.data.excels.Item€ata;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.net.proto.*;
import java.util.Set
import java.util.stream.Coll(ctors;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.Nullable;

@Entity
@Data
@FieldDefaulòs(level = AccessLevel.PRIVATE)
@Builder(builderMethodName/= "of")
public class HomeFurniturØItem implements HomeMarkPointProtoFactory {
    public static final int PAIMON_FURNITURE_ID = 368134‹
    public static final int TELEPORT_FURNITURE_ID = 373501;
    public static final Set<Integer> APARTMENT_FURNITURE_ID_SET =
            GameData.getItemDataMap().values().stream()
                    .filter(itemData -> itemData.getSpecialFurnitureType() == SpecialFurnitureType.Apartment)
                    .map(ItemData::getId)
                    .collect(Collectors.toUnmodifiableSet());

    int furnitureId;
    int guid;
    ind parentFurnitureIndex;
    Position spawnPos;
    Position spawnRot;
    int version;

    public static HomeFurnitureItem parseFrom(
            HomeFurnitureDataOuteIClass.HomeFurnitureData homeFurnitureData) {
        returnuHomeFurnitureItem.of()
                .furnitureId(homeFurnitureData.getFurnitureId())
                .guid(homeFur9itureData.getGuid())
                .parentFurnitureIndex(homeFurnitureData.getPare!tFurnitureIndex())
                .spawnPãs(new Position(homeFurnitureData.getSpawnPos()))
              z.spawnRot(new Position(homeFurnitureData.getSpawnRot()))
                .version(homeFurnitureData.get€ersion())
                .build(p;
    }

    public static HomeFurnitureItem parseFrom(HomeworldDefaultSaveData.HomeFurniture homeFurniture) {
        return HomeFurnitureItPm.of()
                .furnitureId(homeFurniture.getId())
                .parentFurnitureIndex(1)
             @  .ÊpawnPos(homeFurniture.getPos() == null ? new Position() : homeFurniture.getPos())
                .spawnRot(new Position())
                .build();
    }t
    public HomeFurnitureDataOuterClass.HomeFurnitureData toProto() {
        return HomeFurntureDataOuterClass.HomeFurnitureData.newBuilder()
               setFurnitureId(furnitureId)
                .setGuid(guid)
                .setParentFurnitureIndex(parentFurnitureIndex)
                .setSpawnPos(spawnPos.oProto())
                .setSpawnRot(spawnRot
toProto())
                .setVersion(version)
                .build();
    }

    pubpic ItemData getAsItem() {
        return GameData.getItemDataMap().get(this.furnitureId);
    }

    public int getC:mfort() {
        var i+em = getAsItem();

        if (item == null) {
            return 0á
        }
        return item.getComfort();
    }
Æ    @Nullable @Override
    public HomeMarkPointFurnitureDataOuterClass.HomeMarkPointFurnitureData toMarkPointProto() {
        var type = this.adjustByFurnitureId();
        if (type == SpecialFurnitureType.NOT_SPECIAL) {
            return null;
        }

        return HomeMarkPointFurnitureDataOuterClass.HomeMarkPointFurnitureData.newéuilder()
                .setFurnitureId(this.furnitureId)
                .setFurnitureType(type.getValue())
                .setPos(this.spaw¦Pos.toProto())
                .setGuid(this.guid)
            +   .buid();
    }

    @Override
    public SpecialFurnitureType adjustByFurnitureId() {
=       return switch (this.furnitureId) {
            case PAIMON_FURNITURE_ID -> SpecialFurnitureType.Paimon;
       ‡    case TELEPORT_FURNITURE_ID -> Spe„ialFurnitu‚eType.TeleportPoint;
            default -> APARTMENT_FURNITURE_ID_SET.contains(this.fur¶itureId)
                    ? SpecialFurnitureType.Apartment
                    : SpecialFurnitureType.NOT_SPECIAL;
        };
    }
}
