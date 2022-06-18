package emu.grasscutter.data.common;

import emu.grasscutter.game.props.FightProperty;

public class FightPropData {
    private String propType;
    private FightProperty prop;
    private float value;

    public String getPropType() {
        return this.propType;
    }

    public float getValue() {
        return this.value;
    }

    public FightProperty getProp() {
        return this.prop;
    }

    public void onLoad() {
        this.prop = FightProperty.getPropByName(this.propType);
    }
}