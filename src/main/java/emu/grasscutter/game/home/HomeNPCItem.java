package emu.grasscutter.game.home;

import dev.morphia.annotations.Entity;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.net.proto.HomeMarkPointFurnitureDataOuterClass;
import emu.grasscutter.net.proto.HomeMarkPointNPCDataOuterClass;
import emu.grasscutter.net.proto.HomeNpcDataOuterClass;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.Nullable;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(builderMethodName = "of")
public class HomeNPCItem implements HomeMarkPointProtoFactory {
    transient int furnitureId;
    int avatarId;
    Position spawnPos;
    Position spawnRot;
    int costumeId;

    public static HomeNPCItem parseFrom(HomeNpcDataOuterClass.HomeNpcData homeNpcData, Player owner) {
        return HomeNPCItem.of()
                .avatarId(homeNpcData.getAvatarId())
                .spawnPos(new Position(homeNpcData.getSpawnPos()))
                .spawnRot(new Position(homeNpcData.getSpawnRot()))
                .costumeId(owner.getCostumeFrom(homeNpcData.getAvatarId()))
                .build();
    }

    public HomeNpcDataOuterClass.HomeNpcData toProto() {
        return HomeNpcDataOuterClass.HomeNpcData.newBuilder()
                .setAvatarId(avatarId)
                .setSpawnPos(spawnPos.toProto())
                .setSpawnRot(spawnRot.toProto())
                .setCostumeId(costumeId)
                .build();
    }

    public int getFurnitureId() {
        if (this.furnitureId == 0) {
            var data = GameData.getHomeWorldNPCDataMap().get(this.avatarId);
            this.furnitureId = data == null ? -1 : data.getFurnitureID();
        }

        return this.furnitureId;
    }

    @Nullable @Override
    public HomeMarkPointFurnitureDataOuterClass.HomeMarkPointFurnitureData toMarkPointProto() {
        return HomeMarkPointFurnitureDataOuterClass.HomeMarkPointFurnitureData.newBuilder()
                .setFurnitureId(this.getFurnitureId())
                .setFurnitureType(this.getType().getValue())
                .setPos(this.spawnPos.toProto())
                .setNpcData(
                        HomeMarkPointNPCDataOuterClass.HomeMarkPointNPCData.newBuilder()
                                .setAvatarId(this.avatarId)
                                .setCostumeId(this.costumeId)
                                .build())
                .build();
    }

    @Override
    public SpecialFurnitureType getType() {
        return SpecialFurnitureType.NPC;
    }

    @Override
    public boolean isProtoConvertible() {
        return this.getFurnitureId() > 0;
    }
}
