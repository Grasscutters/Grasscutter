package emu.grasscutter.data.common;

import emu.grasscutter.game.props.FightProperty;
import lombok.Getter;

@Getter
public class FightPropData {
    private String propType;
    private FightProperty prop;
    private float value;

    public void onLoad() {
        this.prop = FightProperty.getPropByName(propType);
    }
}
