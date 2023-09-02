package emu.grasscutter.game.home;

import emu.grasscutter.net.proto.HomeMarkPointFurnitureDataOuterClass;
import org.jetbrains.annotations.Nullable;

public interface HomeMarkPointProtoFactory {
    @Nullable HomeMarkPointFurnitureDataOuterClass.HomeMarkPointFurnitureData toMarkPointProto();

    default SpecialFurnitureType adjustByFurnitureId() {
        return this.getType();
    }

    default SpecialFurnitureType getType() {
        return SpecialFurnitureType.NOT_SPECIAL;
    }

    default boolean isProtoConvertible() {
        return this.adjustByFurnitureId() != SpecialFurnitureType.NOT_SPECIAL;
    }
}
