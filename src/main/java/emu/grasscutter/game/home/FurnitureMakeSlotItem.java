package emu.grasscutter.game.home;

import dev.morphia.annotations.*;
import emu.grasscutter.net.proto.FurnitureMakeDataOuterClass;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(builderMethodName = "of")
public class FurnitureMakeSlotItem {
    @Id int index;
    int makeId;
    int avatarId;
    int beginTime;
    int durTime;

    public FurnitureMakeDataOuterClass.FurnitureMakeData toProto() {
        return FurnitureMakeDataOuterClass.FurnitureMakeData.newBuilder()
                .setIndex(index)
                .setAvatarId(avatarId)
                .setMakeId(makeId)
                .setBeginTime(beginTime)
                .setDurTime(durTime)
                .build();
    }
}
