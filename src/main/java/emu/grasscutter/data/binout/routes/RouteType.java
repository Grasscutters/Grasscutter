package emu.grasscutter.data.binout.routes;

// import emu.grasscutter.scripts.constants.IntValueEnum;
import lombok.Getter;

public enum RouteType /*implements IntValueEnum*/ {
    Unknown(-1),
    OneWay(0),
    Reciprocate(1),
    Loop(2);

    @Getter private final int id;

    RouteType(int id) {
        this.id = id;
    }

    // @Override
    public int getValue() {
        return id;
    }
}
