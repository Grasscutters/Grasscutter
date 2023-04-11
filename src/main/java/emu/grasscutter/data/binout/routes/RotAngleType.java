package emu.grasscutter.data.binout.routes;

// import emu.grasscutter.scripts.constants.IntValueEnum;
import lombok.Getter;

public enum RotAngleType /*implements IntValueEnum */ {
    ROT_NONE(-1),
    ROT_ANGLE_X(0),
    ROT_ANGLE_Y(1),
    ROT_ANGLE_Z(2);

    @Getter private final int id;

    RotAngleType(int id) {
        this.id = id;
    }

    // @Override
    public int getValue() {
        return id;
    }
}
