package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "NpcExcelConfigData.json")
public class NpcData extends GameResource {
	private int id;
	
	private String jsonName;
    private String alias;
    private String scriptDataPath;
    private String luaDataPath;

    private boolean isInteractive;
    private boolean hasMove;
    private String dyePart;
    private String billboardIcon;

    private long nameTextMapHash;
    private int campID;
	    
	@Override
	public int getId() {
		return this.id;
	}

	public String getJsonName() {
		return jsonName;
	}

	public String getAlias() {
		return alias;
	}

	public String getScriptDataPath() {
		return scriptDataPath;
	}

	public String getLuaDataPath() {
		return luaDataPath;
	}

	public boolean isIsInteractive() {
		return isInteractive;
	}

	public boolean isHasMove() {
		return hasMove;
	}

	public String getDyePart() {
		return dyePart;
	}

	public String getBillboardIcon() {
		return billboardIcon;
	}

	public long getNameTextMapHash() {
		return nameTextMapHash;
	}

	public int getCampID() {
		return campID;
	}

	@Override
	public void onLoad() {

	}
}
