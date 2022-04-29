package emu.grasscutter.data.def;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "GadgetExcelConfigData.json")
public class GadgetData extends GameResource {
	private int Id;
	
	private String Type;
	private String JsonName;
	private boolean IsInteractive;
	private String[] Tags;
	private String ItemJsonName;
	private String InteeIconName;
	private long NameTextMapHash;
	private int CampID;
	private String LODPatternName;

	@Override
	public int getId() {
		return this.Id;
	}

	public String getType() {
		return Type;
	}

	public String getJsonName() {
		return JsonName;
	}

	public boolean isInteractive() {
		return IsInteractive;
	}

	public String[] getTags() {
		return Tags;
	}

	public String getItemJsonName() {
		return ItemJsonName;
	}

	public String getInteeIconName() {
		return InteeIconName;
	}

	public long getNameTextMapHash() {
		return NameTextMapHash;
	}

	public int getCampID() {
		return CampID;
	}

	public String getLODPatternName() { return LODPatternName; }

	@Override
	public void onLoad() {

	}
}
