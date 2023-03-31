package emu.grasscutter.utils;

import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.net.proto.PropValueOuterClass.PropValue;

public final class ProtoHelper {
    public static PropValue newPropValue(PlayerProperty key, int value) {
        return PropValue.newBuilder().setType(key.getId()).setIval(value).setVal(value).build();
    }
}
