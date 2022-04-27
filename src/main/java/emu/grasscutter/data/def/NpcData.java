package emu.grasscutter.data.def;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "NpcExcelConfigData.json")
public class NpcData extends GameResource {
	private int Id;
	
	private String JsonName;
    private String Alias;
    private String ScriptDataPath;
    private String LuaDataPath;

    private boolean IsInteractive;
    private boolean HasMove;
    private String DyePart;
    private String BillboardIcon;

    private long NameTextMapHash;
    private int CampID;
	    
	@Override
	public int getId() {
		return this.Id;
	}

	public String getJsonName() {
		return JsonName;
	}

	public String getAlias() {
		return Alias;
	}

	public String getScriptDataPath() {
		return ScriptDataPath;
	}

	public String getLuaDataPath() {
		return LuaDataPath;
	}

	public boolean isIsInteractive() {
		return IsInteractive;
	}

	public boolean isHasMove() {
		return HasMove;
	}

	public String getDyePart() {
		return DyePart;
	}

	public String getBillboardIcon() {
		return BillboardIcon;
	}

	public long getNameTextMapHash() {
		return NameTextMapHash;
	}

	public int getCampID() {
		return CampID;
	}

	@Override
	public void onLoad() {

	}
}
