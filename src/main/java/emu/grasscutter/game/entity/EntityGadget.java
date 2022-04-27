package emu.grasscutter.game.entity;

import emu.grasscutter.game.Scene;
import emu.grasscutter.game.World;

public abstract class EntityGadget extends GameEntity {

	public EntityGadget(Scene scene) {
		super(scene);
	}
	
	public abstract int getGadgetId();
	
	@Override
	public void onDeath(int killerId) {
		
	}
}
