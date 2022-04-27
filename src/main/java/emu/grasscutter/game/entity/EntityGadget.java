package emu.grasscutter.game.entity;

import emu.grasscutter.game.world.Scene;
import emu.grasscutter.game.world.World;

public abstract class EntityGadget extends GameEntity {

	public EntityGadget(Scene scene) {
		super(scene);
	}
	
	public abstract int getGadgetId();
	
	@Override
	public void onDeath(int killerId) {
		
	}
}
