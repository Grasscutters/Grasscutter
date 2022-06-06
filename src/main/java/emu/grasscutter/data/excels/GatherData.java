package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.Getter;

@ResourceType(name = "GatherExcelConfigData.json")
@Getter
public class GatherData extends GameResource {
	private int pointType;
	private int id;
	private int gadgetId;
	private int itemId;
	private int cd; // Probably hours
	private boolean isForbidGuest;
	private boolean initDisableInteract;
	    
	@Override
	public int getId() {
		return this.pointType;
	}

	@Override
	public void onLoad() {

	}
}
