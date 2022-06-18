package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.props.EntityType;

@ResourceType(name = "GadgetExcelConfigData.json")
public class GadgetData extends GameResource {
    private int id;

    private EntityType type;
    private String jsonName;
    private boolean isInteractive;
    private String[] tags;
    private String itemJsonName;
    private String inteeIconName;
    private long nameTextMapHash;
    private int campID;
    private String LODPatternName;

    @Override
    public int getId() {
        return this.id;
    }

    public EntityType getType() {
        return this.type;
    }

    public String getJsonName() {
        return this.jsonName;
    }

    public boolean isInteractive() {
        return this.isInteractive;
    }

    public String[] getTags() {
        return this.tags;
    }

    public String getItemJsonName() {
        return this.itemJsonName;
    }

    public String getInteeIconName() {
        return this.inteeIconName;
    }

    public long getNameTextMapHash() {
        return this.nameTextMapHash;
    }

    public int getCampID() {
        return this.campID;
    }

    public String getLODPatternName() {
        return this.LODPatternName;
    }

    @Override
    public void onLoad() {

    }
}
