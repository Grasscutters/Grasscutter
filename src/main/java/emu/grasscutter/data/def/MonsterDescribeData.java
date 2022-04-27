package emu.grasscutter.data.def;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;

@ResourceType(name = "MonsterDescribeExcelConfigData.json", loadPriority = LoadPriority.HIGH)
public class MonsterDescribeData extends GameResource {
	private int Id;
    private long NameTextMapHash;
    private int TitleID;
    private int SpecialNameLabID;
    private String Icon;
	
	@Override
	public int getId() {
		return Id;
	}

	public long getNameTextMapHash() {
		return NameTextMapHash;
	}

	public int getTitleID() {
		return TitleID;
	}

	public int getSpecialNameLabID() {
		return SpecialNameLabID;
	}

	public String getIcon() {
		return Icon;
	}

	@Override
	public void onLoad() {

	}
}
