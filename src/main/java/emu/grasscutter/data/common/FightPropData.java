package emu.grasscutter.data.common;

import emu.grasscutter.game.props.FightProperty;

public class FightPropData {
    private String propType;
    private FightProperty prop;
    private float value;

    public String getPropType() {
        return propType;
    }

    public float getValue() {
        return value;
    }

    public FightProperty getProp() {
        return prop;
    }

    public void onLoad() {
        this.prop = FightProperty.getPropByName(propType);
    }
}
