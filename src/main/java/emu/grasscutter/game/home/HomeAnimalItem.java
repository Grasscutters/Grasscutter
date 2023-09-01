package emu.grasscutter.game.home;

import dev.morphia.annotations.Entity;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.net.proto.HomeAnimalDataOuterClass;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(builderMethodName = "of")
public class HomeAnimalItem {
    int furnitureId;
    Position spawnPos;
    Position spawnRot;

    public static HomeAnimalItem parseFrom(HomeAnimalDataOuterClass.HomeAnimalData homeAnimalData) {
        return HomeAnimalItem.of()
                .furnitureId(homeAnimalData.getFurnitureId())
                .spawnPos(new Position(homeAnimalData.getSpawnPos()))
                .spawnRot(new Position(homeAnimalData.getSpawnRot()))
                .build();
    }

    public HomeAnimalDataOuterClass.HomeAnimalData toProto() {
        return HomeAnimalDataOuterClass.HomeAnimalData.newBuilder()
                .setFurnitureId(furnitureId)
                .setSpawnPos(spawnPos.toProto())
                .setSpawnRot(spawnRot.toProto())
                .build();
    }
}
