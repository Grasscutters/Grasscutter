package emu.grasscutter.game.home;

import dev.morphia.annotations.Entity;
import emu.grasscutter.net.proto.HomeAnimalDataOuterClass;
import emu.grasscutter.utils.Position;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(builderMethodName = "of")
public class HomeAnimalItem {
    int furnitureId;
    Position spawnPos;
    Position spawnRot;

    public HomeAnimalDataOuterClass.HomeAnimalData toProto() {
        return HomeAnimalDataOuterClass.HomeAnimalData.newBuilder()
            .setFurnitureId(this.furnitureId)
            .setSpawnPos(this.spawnPos.toProto())
            .setSpawnRot(this.spawnRot.toProto())
            .build();
    }

    public static HomeAnimalItem parseFrom(HomeAnimalDataOuterClass.HomeAnimalData homeAnimalData) {
        return HomeAnimalItem.of()
            .furnitureId(homeAnimalData.getFurnitureId())
            .spawnPos(new Position(homeAnimalData.getSpawnPos()))
            .spawnRot(new Position(homeAnimalData.getSpawnRot()))
            .build();
    }

}
