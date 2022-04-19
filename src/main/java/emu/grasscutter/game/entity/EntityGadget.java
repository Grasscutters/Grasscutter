package emu.grasscutter.game.entity;

import emu.grasscutter.game.GenshinScene;
import emu.grasscutter.game.World;

public abstract class EntityGadget extends GenshinEntity {

	public EntityGadget(GenshinScene scene) {
		super(scene);
	}
	
	public abstract int getGadgetId();
	
	@Override
	public void onDeath(int killerId) {
		
	}
}
