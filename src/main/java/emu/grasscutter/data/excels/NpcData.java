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
        return this.jsonName;
    }

    public String getAlias() {
        return this.alias;
    }

    public String getScriptDataPath() {
        return this.scriptDataPath;
    }

    public String getLuaDataPath() {
        return this.luaDataPath;
    }

    public boolean isIsInteractive() {
        return this.isInteractive;
    }

    public boolean isHasMove() {
        return this.hasMove;
    }

    public String getDyePart() {
        return this.dyePart;
    }

    public String getBillboardIcon() {
        return this.billboardIcon;
    }

    public long getNameTextMapHash() {
        return this.nameTextMapHash;
    }

    public int getCampID() {
        return this.campID;
    }

    @Override
    public void onLoad() {

    }
}
