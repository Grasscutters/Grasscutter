package emu.grasscutter.data.common;

import emu.grasscutter.game.props.FightProperty;

public class FightPropData {
	private String PropType;
	private FightProperty prop;
    private float Value;
    
	public String getPropType() {
		return PropType;
	}
	
	public float getValue() {
		return Value;
	}

	public FightProperty getProp() {
		return prop;
	}

	public void onLoad() {
		this.prop = FightProperty.getPropByName(PropType);
	}
}