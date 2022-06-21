package emu.grasscutter.game.home;

import dev.morphia.annotations.Entity;
import emu.grasscutter.net.proto.HomeNpcDataOuterClass;
import emu.grasscutter.utils.Position;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(builderMethodName = "of")
public class HomeNPCItem {
    int avatarId;
    Position spawnPos;
    Position spawnRot;
    int costumeId;

    public HomeNpcDataOuterClass.HomeNpcData toProto() {
        return HomeNpcDataOuterClass.HomeNpcData.newBuilder()
            .setAvatarId(this.avatarId)
            .setSpawnPos(this.spawnPos.toProto())
            .setSpawnRot(this.spawnRot.toProto())
            .setCostumeId(this.costumeId)
            .build();
    }

    public static HomeNPCItem parseFrom(HomeNpcDataOuterClass.HomeNpcData homeNpcData) {
        return HomeNPCItem.of()
            .avatarId(homeNpcData.getAvatarId())
            .spawnPos(new Position(homeNpcData.getSpawnPos()))
            .spawnRot(new Position(homeNpcData.getSpawnRot()))
            .costumeId(homeNpcData.getCostumeId())
            .build();
    }
}
