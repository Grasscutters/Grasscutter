package emu.grasscutter.game.entity;

import emu.grasscutter.game.World;

public abstract class EntityGadget extends GenshinEntity {

	public EntityGadget(World world) {
		super(world);
	}
	
	public abstract int getGadgetId();
	
	@Override
	public void onDeath(int killerId) {
		
	}
}
