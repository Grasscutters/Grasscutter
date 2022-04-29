package emu.grasscutter.game.entity;

import emu.grasscutter.game.world.Scene;
import emu.grasscutter.game.world.World;

public abstract class EntityBaseGadget extends GameEntity {

	public EntityBaseGadget(Scene scene) {
		super(scene);
	}
	
	public abstract int getGadgetId();
	
	@Override
	public void onDeath(int killerId) {
		
	}
}
