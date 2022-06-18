package emu.grasscutter.data.def;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "GatherExcelConfigData.json")
public class GatherData extends GameResource {
	private int PointType;
	private int Id;
	private int GadgetId;
	private int ItemId;
	private int Cd; // Probably hours
	private boolean IsForbidGuest;
	private boolean InitDisableInteract;
	    
	@Override
	public int getId() {
		return this.PointType;
	}

	public int getGatherId() {
		return Id;
	}

	public int getGadgetId() {
		return GadgetId;
	}

	public int getItemId() {
		return ItemId;
	}

	public int getCd() {
		return Cd;
	}

	public boolean isForbidGuest() {
		return IsForbidGuest;
	}

	public boolean initDisableInteract() {
		return InitDisableInteract;
	}

	@Override
	public void onLoad() {

	}
}
