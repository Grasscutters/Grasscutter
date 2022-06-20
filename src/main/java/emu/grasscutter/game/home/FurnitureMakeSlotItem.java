package emu.grasscutter.game.home;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import emu.grasscutter.net.proto.FurnitureMakeDataOuterClass;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(builderMethodName = "of")
public class FurnitureMakeSlotItem {
    @Id
    int index;
    int makeId;
    int avatarId;
    int beginTime;
    int durTime;

    public FurnitureMakeDataOuterClass.FurnitureMakeData toProto() {
        return FurnitureMakeDataOuterClass.FurnitureMakeData.newBuilder()
            .setIndex(this.index)
            .setAvatarId(this.avatarId)
            .setMakeId(this.makeId)
            .setBeginTime(this.beginTime)
            .setDurTime(this.durTime)
            .build();
    }
}
